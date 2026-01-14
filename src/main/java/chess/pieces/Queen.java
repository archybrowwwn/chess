package chess.pieces;

import boardgame.Board;
import boardgame.Node;
import boardgame.Team;
import boardgame.Direction;
import chess.ChessPiece;
import java.util.ArrayList;
import java.util.List;

public class Queen extends ChessPiece {

    public Queen(Board board, Team team) {
        super(board, team);
    }

    @Override
    public String toString() {
        return "Q";
    }

    @Override
    public List<Node> possibleMoves() {
        List<Node> moves = new ArrayList<>();

        addMovesInDirection(moves, Direction.NORTH);
        addMovesInDirection(moves, Direction.SOUTH);
        addMovesInDirection(moves, Direction.WEST);
        addMovesInDirection(moves, Direction.EAST);

        addMovesInDirection(moves, Direction.NORTH_WEST);
        addMovesInDirection(moves, Direction.NORTH_EAST);
        addMovesInDirection(moves, Direction.SOUTH_WEST);
        addMovesInDirection(moves, Direction.SOUTH_EAST);

        return moves;
    }
}