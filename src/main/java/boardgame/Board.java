package boardgame;

import java.util.HashMap;
import java.util.Map;

public class Board {

    private int rows;
    private int columns;

    private Map<String, Node> nodes = new HashMap<>();

    public Board(int rows, int columns) {
        if (rows < 1 || columns < 1) {
            throw new BoardException("Ошибка создания доски: должно быть как минимум 1 строка и 1 столбец");
        }
        this.rows = rows;
        this.columns = columns;
        initializeGraph();
    }

    private void initializeGraph() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                String id = makeId(r, c);
                nodes.put(id, new Node(id));
            }
        }

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                Node node = getNode(r, c);

                linkIfExists(node, Direction.NORTH, r - 1, c);
                linkIfExists(node, Direction.SOUTH, r + 1, c);
                linkIfExists(node, Direction.WEST, r, c - 1);
                linkIfExists(node, Direction.EAST, r, c + 1);

                linkIfExists(node, Direction.NORTH_WEST, r - 1, c - 1);
                linkIfExists(node, Direction.NORTH_EAST, r - 1, c + 1);
                linkIfExists(node, Direction.SOUTH_WEST, r + 1, c - 1);
                linkIfExists(node, Direction.SOUTH_EAST, r + 1, c + 1);
            }
        }
    }

    private void linkIfExists(Node source, Direction dir, int r, int c) {
        if (positionExists(r, c)) {
            Node target = getNode(r, c);
            source.setNeighbor(dir, target);
        }
    }

    private String makeId(int row, int column) {
        return row + "," + column;
    }

    // Получение узла по координатам
    public Node getNode(int row, int column) {
        return nodes.get(makeId(row, column));
    }

    // Получение узла по ID
    public Node getNode(String id) {
        return nodes.get(id);
    }

    public void placePiece(Piece piece, String nodeId) {
        Node node = nodes.get(nodeId);
        if (node == null) {
            throw new BoardException("Узел не найден: " + nodeId);
        }
        if (!node.isEmpty()) {
            throw new BoardException("На позиции " + nodeId + " уже есть фигура");
        }
        node.setPiece(piece);
        piece.setNode(node);
    }

    public Piece removePiece(String nodeId) {
        Node node = nodes.get(nodeId);
        if (node == null || node.isEmpty()) {
            return null;
        }
        Piece p = node.getPiece();
        node.setPiece(null);
        p.setNode(null);
        return p;
    }

    public boolean positionExists(int row, int column) {
        return row >= 0 && row < rows && column >= 0 && column < columns;
    }

    public boolean thereIsAPiece(String nodeId) {
        Node node = nodes.get(nodeId);
        if (node == null) {
            throw new BoardException("Позиция не существует");
        }
        return !node.isEmpty();
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }
}