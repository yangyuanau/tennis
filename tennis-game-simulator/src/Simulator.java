import java.util.Scanner;

public class Simulator {

    public static void main(String[] args){

        Scanner scanner = new Scanner(System.in);
        Match match = setupGame(scanner);
        int indicator = 0;
        while (indicator == 0) {
            System.out.print("Point won by: ");
            String winner = scanner.next();
            try {
                indicator = match.pointWonBy(winner);
            } catch (InvalidInputException ex) {
                System.out.println("Invalid player name! Current match up: " + match.getPlayerA() + " vs " + match.getPlayerB());
                continue;
            }

            System.out.println("Current score: " + match.score());
        }

        String winner = indicator == 1 ? match.getPlayerA() : match.getPlayerB();
        System.out.println("Congratulations!!! The winner of the match is : " + winner);
    }

    private static Match setupGame(final Scanner scanner) {
        System.out.println("Welcome to Dius Tennis Game");

        System.out.print("Please enter the name of first player: ");
        String playerA = scanner.next();

        System.out.print("Please enter the name of second player: ");
        String playerB = scanner.next();

        System.out.println("Game start...");

        return new Match(playerA, playerB);
    }
}
