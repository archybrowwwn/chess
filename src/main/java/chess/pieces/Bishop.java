package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Bishop extends ChessPiece{

    public Bishop(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "B";
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

        addMovesInDirection(mat, -1, -1); // NW
        addMovesInDirection(mat, -1, 1);  // NE
        addMovesInDirection(mat, 1, -1);  // SW
        addMovesInDirection(mat, 1, 1);   // SE

        return mat;
    }
}
