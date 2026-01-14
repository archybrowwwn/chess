package chess;

import boardgame.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChessBot {

    public static ChessPiece makeRandomMove(ChessMatch match) {
        ChessPiece[][] board = match.getPieces();
        List<ChessPiece> myPieces = new ArrayList<>();
        ChessTeam botTeam = (ChessTeam) match.getCurrentPlayer();

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                ChessPiece p = board[i][j];
                if (p != null && p.getTeam() == botTeam && !p.possibleMoves().isEmpty()) {
                    myPieces.add(p);
                }
            }
        }

        if (myPieces.isEmpty()) {
            throw new ChessException("Нет доступных ходов");
        }

        Random rand = new Random();
        ChessPiece selectedPiece = myPieces.get(rand.nextInt(myPieces.size()));

        List<Node> moves = selectedPiece.possibleMoves();
        Node targetNode = moves.get(rand.nextInt(moves.size()));

        ChessPosition source = selectedPiece.getChessPosition();

        String[] parts = targetNode.getId().split(",");
        int r = Integer.parseInt(parts[0]);
        int c = Integer.parseInt(parts[1]);
        ChessPosition target = new ChessPosition((char)('a' + c), 8 - r);

        System.out.println("Бот сходил: " + source + " -> " + target);

        return match.performChessMove(source, target);
    }
}
