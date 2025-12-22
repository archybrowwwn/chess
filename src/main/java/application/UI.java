package application;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;

public class UI {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";

    public static void clearScreen() {
        System.out.println("\033[H\033[2J");
        System.out.flush();
    }

    public static ChessPosition readChessPosition(Scanner sc) {
        try {
            String s = sc.nextLine();
            char column = s.charAt(0);
            int row = Integer.parseInt(s.substring(1));
            return new ChessPosition(column, row);
        }
        catch (RuntimeException e) {
            throw new InputMismatchException(
                    "Ошибка ввода позиции. Допустимые значения — от a1 до h8."
            );
        }
    }

    public static void printMatch(ChessMatch chessMatch, List<ChessPiece> captured) {
        printBoard(chessMatch.getPieces());
        System.out.println();
        printCapturedPieces(captured);
        System.out.println();
        System.out.println("Ход: " + chessMatch.getTurn());

        if (!chessMatch.getCheckMate()) {
            System.out.println("Ходит игрок: " + chessMatch.getCurrentPlayer());
            if (chessMatch.getCheck()) {
                System.out.println("ШАХ!");
            }
        } else {
            System.out.println("МАТ!");
            System.out.println("Победитель: " + chessMatch.getCurrentPlayer());
        }
    }

    public static void printBoard(ChessPiece[][] pieces) {
        printBoard(pieces, null);
    }

    public static void printBoard(ChessPiece[][] pieces, boolean[][] possibleMoves) {
        System.out.println(ANSI_CYAN + "   _________________" + ANSI_RESET);

        for (int i = 0; i < pieces.length; i++) {
            System.out.print((8 - i) + ANSI_CYAN + " | " + ANSI_RESET);
            for (int j = 0; j < pieces[i].length; j++) {
                boolean background = (possibleMoves != null && possibleMoves[i][j]);
                printPiece(pieces[i][j], background);
            }
            System.out.println(ANSI_CYAN + "|" + ANSI_RESET);
        }

        System.out.println(ANSI_CYAN + "   _________________" + ANSI_RESET);
        System.out.println("    a b c d e f g h");
    }

    private static void printPiece(ChessPiece piece, boolean background) {
        if (background) {
            System.out.print(ANSI_BLUE_BACKGROUND);
        }
        if (piece == null) {
            System.out.print("-" + ANSI_RESET);
        } else {
            String colorCode = (piece.getColor() == Color.WHITE)
                    ? ANSI_WHITE
                    : ANSI_YELLOW;
            System.out.print(colorCode + piece + ANSI_RESET);
        }
        System.out.print(" ");
    }

    private static void printCapturedPieces(List<ChessPiece> captured) {
        List<ChessPiece> white = captured.stream()
                .filter(x -> x.getColor() == Color.WHITE)
                .collect(Collectors.toList());

        List<ChessPiece> black = captured.stream()
                .filter(x -> x.getColor() == Color.BLACK)
                .collect(Collectors.toList());

        System.out.println("Сбитые фигуры:");
        printCapturedList("Белые", white, ANSI_WHITE);
        printCapturedList("Чёрные", black, ANSI_YELLOW);
    }

    private static void printCapturedList(String title, List<ChessPiece> pieces, String colorCode) {
        System.out.print(title + ": ");
        System.out.print(colorCode);
        System.out.println(Arrays.toString(pieces.toArray()));
        System.out.print(ANSI_RESET);
    }
}
