import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {

    private WeightedQuickUnionUF quickUnionPercolationOperator;
    private WeightedQuickUnionUF quickUnionFullnessOperator; // handles the issue with wrongly showing path from top to bottom
    private boolean[][] openSites;
    private int gridSize;
    private int elements;
    private int numberOfOpenSites;
    private int VIRTUAL_TOP_SET;
    private int VIRTUAL_BOTTOM_SET;


    public Percolation(int N) {
        gridSize = N;
        elements = gridSize * gridSize + 2; // + elements for virtual top and virtual bottom
        quickUnionPercolationOperator = new WeightedQuickUnionUF(elements);
        quickUnionFullnessOperator = new WeightedQuickUnionUF(elements);
        openSites = new boolean[N][N];
        VIRTUAL_TOP_SET = elements - 2;
        VIRTUAL_BOTTOM_SET = elements - 1;
        numberOfOpenSites = 0;
    }

    public int getVirtualTopSet() {
        return VIRTUAL_TOP_SET;
    }

    public int getVirtualBottomSet() {
        return VIRTUAL_BOTTOM_SET;
    }

    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    public void open(int col, int row) {
        if (openSites[col][row]) {
            return;
        }
        openSites[col][row] = true;
        numberOfOpenSites++;
        handleUnion(col, row);
    }

    public boolean isOpen(int row, int col) {
        return openSites[row][col];
    }

    public boolean isFull(int row, int col) {
        return quickUnionFullnessOperator.connected(xyTo1D(row, col), VIRTUAL_TOP_SET);
    }

    public boolean percolates() {
        return quickUnionPercolationOperator.connected(VIRTUAL_BOTTOM_SET, VIRTUAL_TOP_SET);
    }

    private void handleUnion(int row, int col) {
        // if top row -> union with virtual top
        // if bottom row -> union with virtual bottom
        if (row == 0) {
            quickUnionPercolationOperator.union(xyTo1D(row, col), VIRTUAL_TOP_SET);
            quickUnionFullnessOperator.union(xyTo1D(row, col), VIRTUAL_TOP_SET);
        }
        if (row == gridSize - 1) {
            quickUnionPercolationOperator.union(xyTo1D(row, col), VIRTUAL_BOTTOM_SET);
        }
        // Check adjacent cells in all directions
        hasOpenNeighbour(row, col, -1, 0);  // Left
        hasOpenNeighbour(row, col, 1, 0);   // Right
        hasOpenNeighbour(row, col, 0, -1);   // Down
        hasOpenNeighbour(row, col, 0, 1); // Up
    }

    private void hasOpenNeighbour(int row, int col, int rowOffset, int colOffset) {
        int rowNeighbour = row + rowOffset;
        int colNeighbour = col + colOffset;

        // Check if the neighbour cells are within bounds
        if (isValidCell(rowNeighbour, colNeighbour) && isOpen(rowNeighbour, colNeighbour)) {
            makeUnion(row, col, rowNeighbour, colNeighbour);
        }
    }

    private void makeUnion(final int row, final int col, final int rowNeighbour, final int colNeighbour) {
        quickUnionPercolationOperator.union(xyTo1D(row, col), xyTo1D(rowNeighbour, colNeighbour));
        quickUnionFullnessOperator.union(xyTo1D(row, col), xyTo1D(rowNeighbour, colNeighbour));
    }

    private boolean isValidCell(int row, int col) {
        return row >= 0 && row < gridSize && col >= 0 && col < gridSize;
    }

    private int xyTo1D(int x, int y) {
        return x * gridSize + y;
    }
}
