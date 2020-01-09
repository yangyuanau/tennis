import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.stream.IntStream;

import static org.junit.Assert.*;

public class MatchTest {

    private static final String PLAYER_A = "a";
    private static final String PLAYER_B = "b";

    private Match match;

    @Before
    public void setUp() throws Exception {
        match = new Match(PLAYER_A, PLAYER_B);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void singleNormalGameTest() throws Exception {

        //first point won by a, indicator = 0 since game is not end, game score should be "0-0, 15-0"
        int indicator = match.pointWonBy(PLAYER_A);
        assertEquals(0, indicator);
        assertEquals("0-0, 15-0", match.score());

        indicator = match.pointWonBy(PLAYER_B);
        assertEquals(0, indicator);
        assertEquals("0-0, 15-15", match.score());

        indicator = match.pointWonBy(PLAYER_B);
        assertEquals(0, indicator);
        assertEquals("0-0, 15-30", match.score());

        indicator = match.pointWonBy(PLAYER_B);
        assertEquals(0, indicator);
        assertEquals("0-0, 15-40", match.score());

        indicator = match.pointWonBy(PLAYER_B);
        assertEquals(0, indicator);
        assertEquals("0-1", match.score());
    }

    @Test
    public void singleGameWithDeuceAndAdvantage() throws Exception {
        int indicator = match.pointWonBy(PLAYER_A);
        assertEquals(0, indicator);
        assertEquals("0-0, 15-0", match.score());

        indicator = match.pointWonBy(PLAYER_B);
        assertEquals(0, indicator);
        assertEquals("0-0, 15-15", match.score());

        indicator = match.pointWonBy(PLAYER_A);
        assertEquals(0, indicator);
        assertEquals("0-0, 30-15", match.score());

        indicator = match.pointWonBy(PLAYER_B);
        assertEquals(0, indicator);
        assertEquals("0-0, 30-30", match.score());

        indicator = match.pointWonBy(PLAYER_A);
        assertEquals(0, indicator);
        assertEquals("0-0, 40-30", match.score());

        indicator = match.pointWonBy(PLAYER_B);
        assertEquals(0, indicator);
        assertEquals("0-0, Deuce", match.score());

        indicator = match.pointWonBy(PLAYER_A);
        assertEquals(0, indicator);
        assertEquals("0-0, Advantage a", match.score());

        indicator = match.pointWonBy(PLAYER_B);
        assertEquals(0, indicator);
        assertEquals("0-0, Deuce", match.score());

        indicator = match.pointWonBy(PLAYER_A);
        assertEquals(0, indicator);
        assertEquals("0-0, Advantage a", match.score());

        indicator = match.pointWonBy(PLAYER_A);
        assertEquals(0, indicator);
        assertEquals("1-0", match.score());
    }

    @Test
    public void setTest() throws Exception {
        // Set score A:5, B:4. If A win another game, game ends.
        letPlayerWinGame(5, PLAYER_A);
        letPlayerWinGame(4, PLAYER_B);

        match.pointWonBy(PLAYER_A);
        match.pointWonBy(PLAYER_A);
        match.pointWonBy(PLAYER_A);
        int indicator = match.pointWonBy(PLAYER_A);
        //indicator should be one since a is the winner
        assertEquals(1, indicator);
        assertEquals("6-4", match.score());
    }

    @Test
    public void testGame7() throws Exception {
        // setup game 7
        letPlayerWinGame(6, PLAYER_A);
        letPlayerWinGame(6, PLAYER_B);

        match.pointWonBy(PLAYER_A);
        assertEquals("6-6, 1-0", match.score());

        match.pointWonBy(PLAYER_A);
        assertEquals("6-6, 2-0", match.score());

        match.pointWonBy(PLAYER_A);
        assertEquals("6-6, 3-0", match.score());

        match.pointWonBy(PLAYER_A);
        assertEquals("6-6, 4-0", match.score());

        match.pointWonBy(PLAYER_A);
        assertEquals("6-6, 5-0", match.score());

        match.pointWonBy(PLAYER_A);
        assertEquals("6-6, 6-0", match.score());

        int indicator = match.pointWonBy(PLAYER_A);
        assertEquals(1, indicator);
        assertEquals("7-6", match.score());
    }

    @Test
    public void testClutchGame7() throws Exception {
        // setup game 7
        letPlayerWinGame(6, PLAYER_A);
        letPlayerWinGame(6, PLAYER_B);

        IntStream.rangeClosed(1, 6).forEach(i -> {
            try {
                match.pointWonBy(PLAYER_A);
            } catch (InvalidInputException e) {
                //never reach here
            }
        });
        assertEquals("6-6, 6-0", match.score());

        IntStream.rangeClosed(1, 6).forEach(i -> {
            try {
                match.pointWonBy(PLAYER_B);
            } catch (InvalidInputException e) {
                //never reach here
            }
        });
        assertEquals("6-6, 6-6", match.score());

        match.pointWonBy(PLAYER_A);
        assertEquals("6-6, 7-6", match.score());

        match.pointWonBy(PLAYER_B);
        assertEquals("6-6, 7-7", match.score());

        match.pointWonBy(PLAYER_B);
        assertEquals("6-6, 7-8", match.score());

        int indicator = match.pointWonBy(PLAYER_B);
        assertEquals(2, indicator);
        assertEquals("6-7", match.score());
    }

    @Test(expected = InvalidInputException.class)
    public void invalidPlayerNameTest() throws Exception {
        match.pointWonBy("Yang");
    }

    private void letPlayerWinGame(int numberOfGame, String player) throws Exception {
        for (int i = 0; i < 4 * numberOfGame; i++) {
            match.pointWonBy(player);
        }
    }
}