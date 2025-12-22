package chess.pieces;

import boardgame.Board;
import boardgame.Node;
import boardgame.Team;
import boardgame.Direction;
import chess.ChessPiece;
import java.util.ArrayList;
import java.util.List;

public class Rook extends ChessPiece {

    public Rook(Board board, Team team) {
        super(board, team);
    }

    @Override
    public String toString() {
        return "R";
    }

    @Override
    public List<Node> possibleMoves() {
        List<Node> moves = new ArrayList<>();

        addMovesInDirection(moves, Direction.NORTH);
        addMovesInDirection(moves, Direction.SOUTH);
        addMovesInDirection(moves, Direction.EAST);
        addMovesInDirection(moves, Direction.WEST);

        return moves;
    }
}