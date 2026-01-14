package application;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import boardgame.Piece;
import chess.ChessMatch;
import chess.ChessPosition;
import chess.ChessTeam;

public class UI {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";

    public static void clearScreen() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

    public static ChessPosition readChessPosition(Scanner sc) {
        while (true) {
            try {
                String s = sc.nextLine().toLowerCase().trim();
                if (s.length() != 2) {
                    throw new InputMismatchException();
                }
                char column = s.charAt(0);
                int row = Integer.parseInt(s.substring(1));
                return new ChessPosition(column, row);
            } catch (RuntimeException e) {
                System.out.print("Ошибка ввода. Введите значения от a1 до h8: ");
            }
        }
    }

    public static void printMatch(ChessMatch chessMatch, List<Piece> captured) {
        printBoard(chessMatch.getPieces());
        System.out.println();
        printCapturedPieces(captured);
        System.out.println();
        System.out.println("Ход: " + chessMatch.getTurn());

        if (!chessMatch.isCheckMate()) {
            System.out.println("Ожидание хода: " + chessMatch.getCurrentPlayer());
            if (chessMatch.isInCheck()) {
                System.out.println("!!! ШАХ !!!");
            }
        } else {
            System.out.println("!!! МАТ !!!");
            System.out.println("Победитель: " + chessMatch.getCurrentPlayer());
        }
    }

    public static void printBoard(Piece[][] pieces) {
        printBoard(pieces, null);
    }

    public static void printBoard(Piece[][] pieces, boolean[][] possibleMoves) {
        System.out.println(ANSI_CYAN + "   _________________" + ANSI_RESET);
        for (int i = 0; i < pieces.length; i++) {
            System.out.print((8 - i) + ANSI_CYAN + " | " + ANSI_RESET);
            for (int j = 0; j < pieces[i].length; j++) {
                printPiece(pieces[i][j], (possibleMoves != null && possibleMoves[i][j]));
            }
            System.out.println(ANSI_CYAN + "|" + ANSI_RESET);
        }
        System.out.println(ANSI_CYAN + "   _________________" + ANSI_RESET);
        System.out.println("    a b c d e f g h");
    }

    private static void printPiece(Piece piece, boolean background) {
        if (background) {
            System.out.print(ANSI_BLUE_BACKGROUND);
        }
        if (piece == null) {
            System.out.print("-" + ANSI_RESET);
        } else {
            String color = (piece.getTeam() == ChessTeam.WHITE) ? ANSI_WHITE : ANSI_YELLOW;
            System.out.print(color + piece + ANSI_RESET);
        }
        System.out.print(" ");
    }

    private static void printCapturedPieces(List<Piece> captured) {
        System.out.println("Съеденные фигуры:");
        System.out.print("Белые съели: " + ANSI_YELLOW);
        for (Piece p : captured) {
            if (p.getTeam() == ChessTeam.BLACK) {
                System.out.print(p + " ");
            }
        }
        System.out.println(ANSI_RESET);

        System.out.print("Черные съели: " + ANSI_WHITE);
        for (Piece p : captured) {
            if (p.getTeam() == ChessTeam.WHITE) {
                System.out.print(p + " ");
            }
        }
        System.out.println(ANSI_RESET);
    }
}