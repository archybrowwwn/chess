package application;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.ChessTeam;

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
            String s = sc.nextLine().toLowerCase().trim();
            char column = s.charAt(0);
            int row = Integer.parseInt(s.substring(1));
            return new ChessPosition(column, row);
        } catch (RuntimeException e) {
            throw new InputMismatchException("Ошибка ввода (пример: a1).");
        }
    }

    public static void printMatch(ChessMatch chessMatch, List<ChessPiece> captured) {
        printBoard(chessMatch.getPieces());
        System.out.println();
        printCapturedPieces(captured);
        System.out.println();
        System.out.println("Ход: " + chessMatch.getTurn());

        if (!chessMatch.isCheckMate()) {
            System.out.println("Ходит: " + chessMatch.getCurrentPlayer());
            if (chessMatch.isInCheck()) {
                System.out.println("!!! ШАХ !!!");
            }
        } else {
            System.out.println("!!! МАТ !!!");
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
                printPiece(pieces[i][j], (possibleMoves != null && possibleMoves[i][j]));
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
            String color = (piece.getTeam() == ChessTeam.WHITE) ? ANSI_WHITE : ANSI_YELLOW;
            System.out.print(color + piece + ANSI_RESET);
        }
        System.out.print(" ");
    }

    private static void printCapturedPieces(List<ChessPiece> captured) {
        System.out.println("Захваченные фигуры:");
        System.out.print("Белые: " + ANSI_WHITE);
        for (ChessPiece p : captured) {
            if (p.getTeam() == ChessTeam.WHITE) {
                System.out.print(p + " ");
            }
        }
        System.out.println(ANSI_RESET);

        System.out.print("Черные: " + ANSI_YELLOW);
        for (ChessPiece p : captured) {
            if (p.getTeam() == ChessTeam.BLACK) {
                System.out.print(p + " ");
            }
        }
        System.out.println(ANSI_RESET);
    }
}