package boardgame;

import java.util.HashMap;
import java.util.Map;

public class Node {

    private NodeId id;
    private Piece piece;

    private Map<Direction, Node> neighbors = new HashMap<>();

    public Node(int row, int column) {
        this.id = new NodeId(row, column);
    }

    public NodeId getId() {
        return id;
    }

    public int getRow() {
        return id.getRow();
    }

    public int getColumn() {
        return id.getColumn();
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Node getNeighbor(Direction direction) {
        return neighbors.get(direction);
    }

    public void setNeighbor(Direction direction, Node node) {
        neighbors.put(direction, node);
    }

    public boolean isEmpty() {
        return piece == null;
    }
}
