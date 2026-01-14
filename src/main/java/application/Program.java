package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import boardgame.Piece;
import chess.ChessException;
import chess.ChessMatch;
import chess.ChessTeam;

public class Program {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ChessMatch match = new ChessMatch();
        List<Piece> captured = new ArrayList<>();

        System.out.println("1 - Человек vs Человек");
        System.out.println("2 - Человек vs Бот");
        System.out.println("3 - Бот vs Бот");
        System.out.print("Режим: ");

        int mode = sc.hasNextInt() ? sc.nextInt() : 1;
        sc.nextLine();

        ChessPlayer player1;
        ChessPlayer player2;

        switch (mode) {
            case 2:
                player1 = new HumanPlayer();
                player2 = new BotPlayer();
                break;
            case 3:
                player1 = new BotPlayer();
                player2 = new BotPlayer();
                break;
            default:
                player1 = new HumanPlayer();
                player2 = new HumanPlayer();
        }

        while (!match.isCheckMate()) {
            try {
                UI.clearScreen();
                UI.printMatch(match, captured);
                System.out.println();

                Piece capturedPiece;

                if (match.getCurrentPlayer() == ChessTeam.WHITE) {
                    capturedPiece = player1.playTurn(match, sc);
                } else {
                    capturedPiece = player2.playTurn(match, sc);
                }

                if (capturedPiece != null) {
                    captured.add(capturedPiece);
                }

                if (mode == 3) {
                    try { Thread.sleep(1000); } catch (InterruptedException e) {}
                }

            } catch (ChessException e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Ошибка ввода");
                sc.nextLine();
            }
        }
        UI.clearScreen();
        UI.printMatch(match, captured);
    }
}