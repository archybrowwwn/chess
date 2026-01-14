package application;

import boardgame.Piece;
import chess.ChessBot;
import chess.ChessMatch;
import java.util.Scanner;

public class BotPlayer implements ChessPlayer {

    @Override
    public Piece playTurn(ChessMatch match, Scanner sc) {
        System.out.println("Бот думает...");
        try { Thread.sleep(500); } catch (InterruptedException e) {}

        Piece result = ChessBot.makeRandomMove(match);

        System.out.println("Ход сделан.");

        return result;
    }
}