package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Rook extends ChessPiece{

    public Rook(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "R";
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

        addMovesInDirection(mat, -1, 0); // Up
        addMovesInDirection(mat, 1, 0);  // Down
        addMovesInDirection(mat, 0, -1); // Left
        addMovesInDirection(mat, 0, 1);  // Right

        return mat;
    }
}