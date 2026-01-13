package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Node;
import boardgame.Piece;
import boardgame.Position;
import boardgame.Team;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;

public class ChessMatch {

    private int turn;
    private Team currentPlayer;
    private Board board;
    private boolean check;
    private boolean checkMate;

    private List<Piece> piecesOnTheBoard = new ArrayList<>();
    private List<Piece> capturedPieces = new ArrayList<>();

    public ChessMatch() {
        board = new Board(8, 8);
        turn = 1;
        currentPlayer = ChessTeam.WHITE;
        setupBoard();
    }

    public int getTurn() { return turn; }
    public Team getCurrentPlayer() { return currentPlayer; }
    public boolean isInCheck() { return check; }
    public boolean isCheckMate() { return checkMate; }

    public ChessPiece[][] getPieces() {
        ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getColumns(); j++) {
                Node node = board.getNode(i, j);
                if (!node.isEmpty()) {
                    mat[i][j] = (ChessPiece) node.getPiece();
                }
            }
        }
        return mat;
    }

    public boolean[][] possibleMoves(ChessPosition sourcePosition) {
        Position position = sourcePosition.toPosition();
        validateSource(position);

        Node node = board.getNode(position.getRow(), position.getColumn());
        List<Node> possibleNodes = node.getPiece().possibleMoves();

        boolean[][] mat = new boolean[board.getRows()][board.getColumns()];
        for (Node n : possibleNodes) {
            String[] parts = n.getId().split(",");
            int r = Integer.parseInt(parts[0]);
            int c = Integer.parseInt(parts[1]);
            mat[r][c] = true;
        }
        return mat;
    }

    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
        Position sourcePos = sourcePosition.toPosition();
        Position targetPos = targetPosition.toPosition();

        Node sourceNode = board.getNode(sourcePos.getRow(), sourcePos.getColumn());
        Node targetNode = board.getNode(targetPos.getRow(), targetPos.getColumn());

        validateSource(sourcePos);
        validateTarget(sourceNode, targetNode);

        // Выполняем ход
        Piece capturedPiece = executeMove(sourceNode, targetNode);

        // Проверяем, не подставили ли мы сами себя под шах
        if (testCheck(currentPlayer)) {
            undoMove(sourceNode, targetNode, capturedPiece);
            throw new ChessException("Нельзя ставить короля под шах");
        }

        // Проверяем, поставили ли мы шах сопернику
        check = testCheck(opponent(currentPlayer));

        if (testCheckMate(opponent(currentPlayer))) {
            checkMate = true;
        } else {
            nextTurn();
        }

        return (ChessPiece) capturedPiece;
    }

    private Piece executeMove(Node source, Node target) {
        Piece p = board.removePiece(source.getId());
        ((ChessPiece) p).increaseMoveCount();
        Piece capturedPiece = board.removePiece(target.getId());
        board.placePiece(p, target.getId());

        if (capturedPiece != null) {
            piecesOnTheBoard.remove(capturedPiece);
            capturedPieces.add(capturedPiece);
        }
        return capturedPiece;
    }

    private void undoMove(Node source, Node target, Piece capturedPiece) {
        Piece p = board.removePiece(target.getId());
        ((ChessPiece) p).decreaseMoveCount();
        board.placePiece(p, source.getId());

        if (capturedPiece != null) {
            board.placePiece(capturedPiece, target.getId());
            capturedPieces.remove(capturedPiece);
            piecesOnTheBoard.add(capturedPiece);
        }
    }

    private void validateSource(Position position) {
        if (!board.positionExists(position.getRow(), position.getColumn())) throw new ChessException("Позиция не существует");
        Node node = board.getNode(position.getRow(), position.getColumn());
        if (node.isEmpty()) throw new ChessException("Тут пусто");
        if (currentPlayer != node.getPiece().getTeam()) throw new ChessException("Это чужая фигура");
        if (!node.getPiece().hasAnyMove()) throw new ChessException("Нет доступных ходов");
    }

    private void validateTarget(Node source, Node target) {
        if (!source.getPiece().possibleMove(target)) throw new ChessException("Сюда ходить нельзя");
    }

    private void nextTurn() {
        turn++;
        currentPlayer = (currentPlayer == ChessTeam.WHITE) ? ChessTeam.BLACK : ChessTeam.WHITE;
    }

    private Team opponent(Team team) {
        return (team == ChessTeam.WHITE) ? ChessTeam.BLACK : ChessTeam.WHITE;
    }

    private ChessPiece king(Team team) {
        for (Piece p : piecesOnTheBoard) {
            if (p instanceof King && p.getTeam() == team) return (ChessPiece) p;
        }
        throw new IllegalStateException("Нет короля " + team);
    }

    private boolean testCheck(Team team) {
        Node kingNode = king(team).getNode();
        for (Piece p : piecesOnTheBoard) {
            if (p.getTeam() != team) {
                if (p.possibleMoves().contains(kingNode)) return true;
            }
        }
        return false;
    }

    private boolean testCheckMate(Team team) {
        if (!testCheck(team)) return false;
        List<Piece> myList = piecesOnTheBoard.stream().filter(p -> p.getTeam() == team).collect(Collectors.toList());

        for (Piece p : myList) {
            List<Node> moves = p.possibleMoves();
            for (Node targetNode : moves) {
                Node sourceNode = p.getNode();
                Piece captured = executeMove(sourceNode, targetNode);
                boolean stillInCheck = testCheck(team);
                undoMove(sourceNode, targetNode, captured);
                if (!stillInCheck) return false;
            }
        }
        return true;
    }

    private void setupBoard() {
        placeNewPiece('a', 1, new Rook(board, ChessTeam.WHITE));
        placeNewPiece('b', 1, new Knight(board, ChessTeam.WHITE));
        placeNewPiece('c', 1, new Bishop(board, ChessTeam.WHITE));
        placeNewPiece('d', 1, new Queen(board, ChessTeam.WHITE));

        placeNewPiece('e', 1, new King(board, ChessTeam.WHITE));
        placeNewPiece('f', 1, new Bishop(board, ChessTeam.WHITE));
        placeNewPiece('g', 1, new Knight(board, ChessTeam.WHITE));
        placeNewPiece('h', 1, new Rook(board, ChessTeam.WHITE));

        placeNewPiece('a', 8, new Rook(board, ChessTeam.BLACK));
        placeNewPiece('b', 8, new Knight(board, ChessTeam.BLACK));
        placeNewPiece('c', 8, new Bishop(board, ChessTeam.BLACK));
        placeNewPiece('d', 8, new Queen(board, ChessTeam.BLACK));

        placeNewPiece('e', 8, new King(board, ChessTeam.BLACK));
        placeNewPiece('f', 8, new Bishop(board, ChessTeam.BLACK));
        placeNewPiece('g', 8, new Knight(board, ChessTeam.BLACK));
        placeNewPiece('h', 8, new Rook(board, ChessTeam.BLACK));

        for (char c = 'a'; c <= 'h'; c++) {
            placeNewPiece(c, 2, new Pawn(board, ChessTeam.WHITE));
            placeNewPiece(c, 7, new Pawn(board, ChessTeam.BLACK));
        }
    }

    private void placeNewPiece(char column, int row, ChessPiece piece) {
        String nodeId = board.getNode(8 - row, column - 'a').getId();
        board.placePiece(piece, nodeId);
        piecesOnTheBoard.add(piece);
    }
}