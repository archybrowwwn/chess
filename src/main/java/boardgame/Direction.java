package boardgame;

public enum Direction {

    NORTH(-1, 0),
    NORTH_EAST(-1, 1),
    EAST(0, 1),
    SOUTH_EAST(1, 1),
    SOUTH(1, 0),
    SOUTH_WEST(1, -1),
    WEST(0, -1),
    NORTH_WEST(-1, -1);

    private int rowOffset;
    private int columnOffset;

    Direction(int rowOffset, int columnOffset) {
        this.rowOffset = rowOffset;
        this.columnOffset = columnOffset;
    }

    public int getRowOffset() {
        return rowOffset;
    }

    public int getColumnOffset() {
        return columnOffset;
    }
}
