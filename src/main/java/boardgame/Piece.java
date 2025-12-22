package boardgame;

import java.util.List;

public abstract class Piece {

    protected Node node;
    protected Board board;
    protected Team team;

    public Piece(Board board, Team team) {
        this.board = board;
        this.team = team;
        this.node = null;
    }

    public Board getBoard() {
        return board;
    }

    public Team getTeam() {
        return team;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public abstract List<Node> possibleMoves();

    public boolean possibleMove(Node targetNode) {
        if (targetNode == null) return false;
        return possibleMoves().contains(targetNode);
    }

    public boolean isThereAnyPossibleMove() {
        return !possibleMoves().isEmpty();
    }

    protected boolean isThereOpponentPiece(Node target) {
        return !target.isEmpty() && getTeam().isOpponent(target.getPiece().getTeam());
    }
}
