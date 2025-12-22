package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {

    private ChessMatch chessMatch;

    public Pawn(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        this.chessMatch = chessMatch;
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
        Position p = new Position(0, 0);

        int direction = (getColor() == Color.WHITE) ? -1 : 1;

        p.setValues(position.getRow() + direction, position.getColumn());
        if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;

            Position p2 = new Position(position.getRow() + (direction * 2), position.getColumn());
            if (getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0) {
                mat[p2.getRow()][p2.getColumn()] = true;
            }
        }

        p.setValues(position.getRow() + direction, position.getColumn() - 1);
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        p.setValues(position.getRow() + direction, position.getColumn() + 1);
        if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
            mat[p.getRow()][p.getColumn()] = true;
        }

        if ((getColor() == Color.WHITE && position.getRow() == 3) ||
                (getColor() == Color.BLACK && position.getRow() == 4)) {

            checkEnPassant(mat, direction, position.getColumn() - 1);
            checkEnPassant(mat, direction, position.getColumn() + 1);
        }

        return mat;
    }

    private void checkEnPassant(boolean[][] mat, int direction, int targetColumn) {
        Position target = new Position(position.getRow(), targetColumn);
        if (getBoard().positionExists(target) && isThereOpponentPiece(target) &&
                getBoard().piece(target) == chessMatch.getEnPassantVulnerable()) {
            mat[target.getRow() + direction][target.getColumn()] = true;
        }
    }

    @Override
    public String toString() {
        return "P";
    }

}