package boardgame;

import java.util.List;

public abstract class Piece {

    protected Position position;
    private Team team;
    protected Board board;
    protected Node node;

    private int moveCount;

    public Piece(Board board, Team team) {
        this.board = board;
        this.team = team;
    }

    public abstract List<Node> possibleMoves();

    public Team getTeam() {
        return team;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public Node getNode() {
        return node;
    }

    public boolean possibleMove(Node target) {
        return possibleMoves().contains(target);
    }

    public boolean hasAnyMove() {
        return !possibleMoves().isEmpty();
    }

    public int getMoveCount() {
        return moveCount;
    }

    public void increaseMoveCount() {
        moveCount++;
    }

    public void decreaseMoveCount() {
        moveCount--;
    }

    protected boolean isThereOpponentPiece(Node target) {
        return !target.isEmpty() && getTeam().isOpponent(target.getPiece().getTeam());
    }

    protected void addMovesInDirection(List<Node> moves, Direction direction) {
        if (node == null) return;

        Node target = node.getNeighbor(direction);

        while (target != null) {
            if (target.isEmpty()) {
                moves.add(target);
            } else {
                if (isThereOpponentPiece(target)) {
                    moves.add(target);
                }
                break;
            }
            target = target.getNeighbor(direction);
        }
    }
}