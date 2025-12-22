package chess.pieces;

import boardgame.Board;
import boardgame.Node;
import boardgame.Team;
import boardgame.Direction;
import chess.ChessMatch;
import chess.ChessPiece;
import java.util.ArrayList;
import java.util.List;

public class King extends ChessPiece {

    private ChessMatch chessMatch;

    public King(Board board, Team team, ChessMatch chessMatch) {
        super(board, team);
        this.chessMatch = chessMatch;
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