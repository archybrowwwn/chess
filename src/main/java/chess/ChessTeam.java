package chess;

import boardgame.Team;

public class ChessTeam extends Team {

    public static final Team WHITE = new ChessTeam("WHITE");
    public static final Team BLACK = new ChessTeam("BLACK");


    protected ChessTeam(String name) {
        super(name);
    }
}
