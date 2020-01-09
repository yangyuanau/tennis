import java.util.HashMap;
import java.util.Map;

public class Match implements IMatch {

    private String playerA;
    private String playerB;

    /**
     * 2-D array as the scoreboard
     *
     *     number of game that playerA won <-[(0,0)] [(0,1)] -> number of points that playerA won in current game
     *     number of game that playerB won <-[(1,0)] [(1,1)] -> number of points that playerA won in current game
     */
    private int[][] scoreBoard;

    private boolean isGame7;

    private Map<Integer, String> scoreTranslator = new HashMap<>();

    public String getPlayerA() {
        return playerA;
    }

    public String getPlayerB() {
        return playerB;
    }

    public Match(String playerA, String playerB) {
        this.playerA = playerA;
        this.playerB = playerB;
        initialize();
    }

    private void initialize() {
        this.scoreBoard =  new int[2][2];
        scoreTranslator.put(0, "0");
        scoreTranslator.put(1, "15");
        scoreTranslator.put(2, "30");
        scoreTranslator.put(3, "40");
        isGame7 = false;
    }

    /**
     * @param winner, player name of the winner regarding to this point
     *
     * @return int  1 means player A wins the set
     *              2 means player B wins the set
     *              0 means set not end yet, match should continue
     *
     * @throws InvalidInputException in case if user has typo
     */
    @Override
    public int pointWonBy(String winner) throws InvalidInputException {

        if (playerA.equals(winner)) {
            return addPointForA();
        } else if (playerB.equals(winner)) {
            return addPointForB();
        } else {
            throw new InvalidInputException("Invalid player name");
        }
    }

    @Override
    public String score() {
        String scoreStr = String.format("%s-%s", scoreBoard[0][0], scoreBoard[1][0]);

        if (!isGame7) {
            if (scoreBoard[0][1] != 0 || scoreBoard[1][1] != 0) {
                scoreStr = scoreStr.concat(", ").concat(getGameScoreStr());
            }
        } else {
            if (scoreBoard[0][1] != 0 || scoreBoard[1][1] != 0) {
                scoreStr = scoreStr.concat(", ").concat(String.format("%s-%s", scoreBoard[0][1], scoreBoard[1][1]));
            }
        }

        return scoreStr;
    }


    private String getGameScoreStr() {
        if (scoreBoard[0][1] == 3 && scoreBoard[1][1] == 3) {
            return "Deuce";
        } else if (scoreBoard[0][1] > 3 && scoreBoard[0][1] == scoreBoard[1][1]) {
            return "Deuce";
        } else if (scoreBoard[0][1] > 3 && scoreBoard[0][1] > scoreBoard[1][1]) {
            return "Advantage " + playerA;
        } else if (scoreBoard[1][1] > 3 && scoreBoard[1][1] > scoreBoard[0][1]) {
            return "Advantage " + playerB;
        } else {
            return String.format("%s-%s", scoreTranslator.get(scoreBoard[0][1]), scoreTranslator.get(scoreBoard[1][1]));
        }
    }

    /**
     *  Helper method to add one point for A
     *  if A wins the game, reset the game
     *  if A wins the set return 1
     *
     * @return int which indicates if A wins the game or not
     */
    private int addPointForA() {
        scoreBoard[0][1]++;

        if ((isGame7 && isGame7Won()) || (!isGame7 && isGameWon())) {
            scoreBoard[0][0]++;
            resetGame();
        }

        if (isSetWon()) {
            return 1;
        } else {
            return 0;
        }
    }

    private int addPointForB() {
        scoreBoard[1][1]++;

        if ((isGame7 && isGame7Won()) || (!isGame7 && isGameWon())) {
            scoreBoard[1][0]++;
            resetGame();
        }

        if (isSetWon()) {
            return 2;
        } else {
            return 0;
        }
    }

    private void resetGame() {
        scoreBoard[0][1] = 0;
        scoreBoard[1][1] = 0;
    }

    /**
     * Helper function to determine if one game within the set is won by a player, except game 7
     *
     * Rules:
     * A game is won by the first player to have won at least 4 points in total and at least 2 points more than the opponent.
     *
     * The running score of each game is described in a manner peculiar to tennis: scores from zero to three points are described as 0, 15, 30, 40, respectively
     * If at least 3 points have been scored by each player, and the scores are equal, the score is "deuce".
     *
     * If at least 3 points have been scored by each side and a player has one more point than his opponent, the score of the game is "advantage" for the player in the lead.
     *
     *  @return
     */
    private boolean isGameWon() {
        int a = Math.max(scoreBoard[0][1], scoreBoard[1][1]);
        int b = Math.min(scoreBoard[0][1], scoreBoard[1][1]);

        if (a == 4 && b < 3) {
            return true;
        } else if (a > 4 && (a - b) > 1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Helper function to determine if one game within the set is won by a player, except game 7
     *
     * Rule:
     * A tie-break, played under a separate set of rules, allows one player to win one more game and thus the set, to give a final set score of 7–6.
     * A tie-break is scored one point at a time. The tie-break game continues until one player wins seven points by a margin of two or more points.
     * Instead of being scored from 0, 15, 30, 40 like regular games, the score for a tie breaker goes up incrementally from 0 by 1.
     * a player's score will go from 0 to 1 to 2 to 3 …etc.
     */
    private boolean isGame7Won() {
        int a = Math.max(scoreBoard[0][1], scoreBoard[1][1]);
        int b = Math.min(scoreBoard[0][1], scoreBoard[1][1]);

        if (a >= 7 && (a-b) > 1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Helper function to determine if there is a winner for the game
     * Also if set score reaches 6:6, set the isGame7 flag to true
     *
     * Rules:
     * A player wins a set by winning at least 6 games and at least 2 games more than the opponent.
     *
     * If one player has won six games and the opponent five, an additional game is played. If the leading player wins that game,
     * the player wins the set 7–5. If the trailing player wins the game, a tie-break is played.
     *
     *
     *  @return
     */
    private boolean isSetWon() {
        int a = Math.max(scoreBoard[0][0], scoreBoard[1][0]);
        int b = Math.min(scoreBoard[0][0], scoreBoard[1][0]);

        if (a == 6 && a == b) {
            isGame7 = true;
        }

        if (a == 7) {
            return true;
        } else if (a == 6 && b < 5) {
            return true;
        } else {
            return false;
        }
    }

}
