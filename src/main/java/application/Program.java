package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import chess.ChessBot;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.ChessTeam;

public class Program {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ChessMatch match = new ChessMatch();
        List<ChessPiece> captured = new ArrayList<>();

        while (!match.isCheckMate()) {
            try {
                UI.clearScreen();
                UI.printMatch(match, captured);
                System.out.println();

                ChessPiece capturedPiece;

                if (match.getCurrentPlayer() == ChessTeam.BLACK) {
                    capturedPiece = playBotTurn(match, sc);
                } else {
                    capturedPiece = playHumanTurn(match, sc);
                }

                if (capturedPiece != null) {
                    captured.add(capturedPiece);
                }

            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
                sc.nextLine();
            }
        }

        UI.clearScreen();
        UI.printMatch(match, captured);
    }

    private static ChessPiece playHumanTurn(ChessMatch match, Scanner sc) {
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

    private static ChessPiece playBotTurn(ChessMatch match, Scanner sc) {
        System.out.println("Бот думает...");

        try { Thread.sleep(600); } catch (InterruptedException e) {}

        ChessPiece captured = ChessBot.makeRandomMove(match);

        System.out.println("Бот сделал ход. Нажмите Enter, чтобы продолжить...");
        sc.nextLine();

        return captured;
    }
}