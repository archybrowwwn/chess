package chess.pieces;

import boardgame.Board;
import boardgame.Node;
import boardgame.Team;
import boardgame.Direction;
import boardgame.Piece;
import chess.ChessTeam;
import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {

    public Pawn(Board board, Team team) {
        super(board, team);
    }

    @Override
    public List<Node> possibleMoves() {
        List<Node> moves = new ArrayList<>();

        Direction forward = (getTeam() == ChessTeam.WHITE) ? Direction.NORTH : Direction.SOUTH;

        Direction leftDiag = (getTeam() == ChessTeam.WHITE) ? Direction.NORTH_WEST : Direction.SOUTH_WEST;
        Direction rightDiag = (getTeam() == ChessTeam.WHITE) ? Direction.NORTH_EAST : Direction.SOUTH_EAST;

        Node p1 = node.getNeighbor(forward);
        if (p1 != null && p1.isEmpty()) {
            moves.add(p1);

            if (getMoveCount() == 0) {
                Node p2 = p1.getNeighbor(forward);
                if (p2 != null && p2.isEmpty()) {
                    moves.add(p2);
                }
            }
        }

        checkCapture(moves, leftDiag);
        checkCapture(moves, rightDiag);

        return moves;
    }

    private void checkCapture(List<Node> moves, Direction dir) {
        Node target = node.getNeighbor(dir);
        if (target != null && isThereOpponentPiece(target)) {
            moves.add(target);
        }
    }

    @Override
    public String toString() {
        return "P";
    }
}