package chess.pieces;

import boardgame.Board;
import boardgame.Node;
import boardgame.Team;
import boardgame.Direction;
import chess.ChessPiece;
import java.util.ArrayList;
import java.util.List;

public class Knight extends ChessPiece {

    public Knight(Board board, Team team) {
        super(board, team);
    }

    @Override
    public String toString() {
        return "N";
    }

    @Override
    public List<Node> possibleMoves() {
        List<Node> moves = new ArrayList<>();

        checkJump(moves, Direction.NORTH, Direction.NORTH, Direction.WEST);
        checkJump(moves, Direction.NORTH, Direction.NORTH, Direction.EAST);

        checkJump(moves, Direction.SOUTH, Direction.SOUTH, Direction.WEST);
        checkJump(moves, Direction.SOUTH, Direction.SOUTH, Direction.EAST);

        checkJump(moves, Direction.WEST, Direction.WEST, Direction.NORTH);
        checkJump(moves, Direction.WEST, Direction.WEST, Direction.SOUTH);

        checkJump(moves, Direction.EAST, Direction.EAST, Direction.NORTH);
        checkJump(moves, Direction.EAST, Direction.EAST, Direction.SOUTH);

        return moves;
    }

    private void checkJump(List<Node> moves, Direction d1, Direction d2, Direction d3) {

        Node target = node.getNeighbor(d1);
        if (target == null) return;

        target = target.getNeighbor(d2);
        if (target == null) return;

        target = target.getNeighbor(d3);

        if (target != null && (target.isEmpty() || isThereOpponentPiece(target))) {
            moves.add(target);
        }
    }
}