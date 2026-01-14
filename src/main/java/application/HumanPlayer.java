package application;

import boardgame.Piece;
import chess.ChessMatch;
import chess.ChessPosition;
import java.util.Scanner;

public class HumanPlayer implements ChessPlayer {

    @Override
    public Piece playTurn(ChessMatch match, Scanner sc) {
        System.out.print("Откуда: ");
        ChessPosition source = UI.readChessPosition(sc);

        boolean[][] possibleMoves = match.possibleMoves(source);
        UI.clearScreen();
        UI.printBoard(match.getPieces(), possibleMoves);
        System.out.println();

        System.out.print("Куда: ");
        ChessPosition target = UI.readChessPosition(sc);

        return match.performChessMove(source, target);
    }
}