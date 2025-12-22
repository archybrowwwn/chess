package boardgame;

import java.util.HashMap;
import java.util.Map;

public class Node {

    private String id;
    private Piece piece;

    private Map<Direction, Node> neighbors = new HashMap<>();

    public Node(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setNeighbor(Direction direction, Node node) {
        neighbors.put(direction, node);
    }

    public Node getNeighbor(Direction direction) {
        return neighbors.get(direction);
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Piece getPiece() {
        return piece;
    }

    public boolean isEmpty() {
        return piece == null;
    }

    @Override
    public String toString() {
        return id;
    }
}
