public interface IMatch {
    int pointWonBy(String player) throws InvalidInputException;
    String score();
}
