package chess;

import boardgame.Board;
import boardgame.Node;
import boardgame.Piece;
import boardgame.Team;
import boardgame.Direction;
import java.util.List;

public abstract class ChessPiece extends Piece {

    private int moveCount;

    public ChessPiece(Board board, Team team) {
        super(board, team);
    }

    public int getMoveCount() {
        return moveCount;
    }

    public void increaseMoveCount() {
        moveCount++;
    }

    public void decreaseMoveCount() {
        moveCount--;
    }

    public ChessPosition getChessPosition() {
        try {
            String[] parts = node.getId().split(",");
            int r = Integer.parseInt(parts[0]);
            int c = Integer.parseInt(parts[1]);

            return new ChessPosition((char)('a' + c), 8 - r);
        } catch (Exception e) {
            return null;
        }
    }

    protected boolean isThereOpponentPiece(Node target) {
        return !target.isEmpty() && getTeam().isOpponent(target.getPiece().getTeam());
    }

    protected void addMovesInDirection(List<Node> moves, Direction direction) {
        if (node == null) return;

        Node current = node.getNeighbor(direction);

        while (current != null) {
            if (current.isEmpty()) {
                moves.add(current);
            } else {
                if (isThereOpponentPiece(current)) {
                    moves.add(current);
                }
                break;
            }
            current = current.getNeighbor(direction);
        }
    }
}
