package chess;

import boardgame.Node;
import boardgame.Piece;
import boardgame.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChessBot {

    public static Piece makeRandomMove(ChessMatch match) {

        Piece[][] board = match.getPieces();
        List<Piece> myPieces = new ArrayList<>();
        Team botTeam = match.getCurrentPlayer();

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                Piece p = board[i][j];

                if (p != null && p.getTeam() == botTeam && !p.possibleMoves().isEmpty()) {
                    myPieces.add(p);
                }
            }
        }

        if (myPieces.isEmpty()) {
            throw new ChessException("Нет доступных ходов");
        }

        Random rand = new Random();
        Piece selectedPiece = myPieces.get(rand.nextInt(myPieces.size()));

        List<Node> moves = selectedPiece.possibleMoves();
        Node targetNode = moves.get(rand.nextInt(moves.size()));

        int sourceRow = selectedPiece.getNode().getRow();
        int sourceCol = selectedPiece.getNode().getColumn();
        ChessPosition source = new ChessPosition((char)('a' + sourceCol), 8 - sourceRow);

        int targetRow = targetNode.getRow();
        int targetCol = targetNode.getColumn();
        ChessPosition target = new ChessPosition((char)('a' + targetCol), 8 - targetRow);

        System.out.println("Бот сходил: " + source + " -> " + target);

        return match.performChessMove(source, target);
    }
}