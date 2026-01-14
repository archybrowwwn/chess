package boardgame;

import java.util.HashMap;
import java.util.Map;

public class Board {

    private int rows;
    private int columns;
    private Map<NodeId, Node> nodes = new HashMap<>();

    public Board(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        initializeGraph();
    }

    private void initializeGraph() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                Node node = new Node(r, c);
                nodes.put(node.getId(), node);
            }
        }

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                Node node = getNode(r, c);
                for (Direction dir : Direction.values()) {
                    int neighborRow = r + dir.getRowOffset();
                    int neighborCol = c + dir.getColumnOffset();

                    if (positionExists(neighborRow, neighborCol)) {
                        Node neighbor = getNode(neighborRow, neighborCol);
                        node.setNeighbor(dir, neighbor);
                    }
                }
            }
        }
    }

    public Node getNode(int row, int column) {
        return nodes.get(new NodeId(row, column));
    }

    public void placePiece(Piece piece, NodeId nodeId) {
        Node node = nodes.get(nodeId);
        if (node != null) {
            node.setPiece(piece);
            piece.setNode(node);
        }
    }

    public void placePiece(Piece piece, int row, int col) {
        placePiece(piece, new NodeId(row, col));
    }

    public Piece removePiece(NodeId nodeId) {
        Node node = nodes.get(nodeId);
        if (node == null || node.getPiece() == null) {
            return null;
        }
        Piece piece = node.getPiece();
        node.setPiece(null);
        return piece;
    }

    public boolean positionExists(int row, int column) {
        return row >= 0 && row < rows && column >= 0 && column < columns;
    }

    public int getRows() { return rows; }
    public int getColumns() { return columns; }
}