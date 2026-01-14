package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import chess.ChessBot;
import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.ChessTeam;

public class Program {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);



        ChessMatch match = new ChessMatch();
        List<ChessPiece> captured = new ArrayList<>();

        System.out.println("1 - Игрок против Игрока");
        System.out.println("2 - Игрок против Бота");
        System.out.print("Выберите режим: ");

        int mode = 1;
        try {
            mode = sc.nextInt();
        } catch (Exception e) {
            mode = 1;
        }
        sc.nextLine();

        while (!match.isCheckMate()) {
            try {
                UI.clearScreen();
                UI.printMatch(match, captured);
                System.out.println();

                ChessPiece capturedPiece;

                if (match.getCurrentPlayer() == ChessTeam.BLACK && mode == 2) {
                    capturedPiece = playBotTurn(match, sc);
                } else {
                    capturedPiece = playHumanTurn(match, sc);
                }

                if (capturedPiece != null) {
                    captured.add(capturedPiece);
                }

            } catch (ChessException e) {
                System.out.println("Ошибка игры: " + e.getMessage());
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Ошибка ввода. Нажмите Enter.");
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

        try { Thread.sleep(1000); } catch (InterruptedException e) {}

        ChessPiece result = ChessBot.makeRandomMove(match);

        System.out.println("Нажмите Enter...");
        sc.nextLine();

        return result;
    }
}