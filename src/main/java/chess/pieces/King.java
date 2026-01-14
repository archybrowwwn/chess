package chess.pieces;

import boardgame.Board;
import boardgame.Node;
import boardgame.Team;
import boardgame.Direction;
import boardgame.Piece;
import java.util.ArrayList;
import java.util.List;

public class King extends Piece {

    public King(Board board, Team team) {
        super(board, team);
    }

    @Override
    public String toString() {
        return "K";
    }

    private boolean canMove(Node target) {
        return target != null && (target.isEmpty() || isThereOpponentPiece(target));
    }

    @Override
    public List<Node> possibleMoves() {
        List<Node> moves = new ArrayList<>();

        for (Direction dir : Direction.values()) {
            Node neighbor = node.getNeighbor(dir);
            if (canMove(neighbor)) {
                moves.add(neighbor);
            }
        }

        return moves;
    }
}