package application;

import boardgame.Piece;
import chess.ChessMatch;
import java.util.Scanner;

public interface ChessPlayer {
    Piece playTurn(ChessMatch match, Scanner sc);
}
