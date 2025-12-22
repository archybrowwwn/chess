package chess;

import boardgame.Team;

public class ChessTeam implements Team {

    private String name;

    public static final ChessTeam WHITE = new ChessTeam("WHITE");
    public static final ChessTeam BLACK = new ChessTeam("BLACK");

    private ChessTeam(String name) {
        this.name = name;
    }

    @Override
    public boolean isOpponent(Team other) {
        if (other == null) return false;
        return !this.name.equals(other.getName());
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
