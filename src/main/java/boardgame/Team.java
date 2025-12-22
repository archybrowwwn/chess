package boardgame;

public interface Team {
    boolean isOpponent(Team other);
    String getName();
}
