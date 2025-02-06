package core.model;

import tileengine.TETile;
import tileengine.Tileset;
import utils.RandomUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {
    private final int width;
    private final int height;
    private final TETile[][] grid;
    private final boolean[][] occupied; // Fast occupancy tracking
    private final List<Room> rooms;
    private final Random random;

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = new TETile[width][height];
        this.occupied = new boolean[width][height];
        this.rooms = new ArrayList<>();
        this.random = new Random();

        // Initialize the board
        initializeGrid();
    }

    private void initializeGrid() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                grid[x][y] = Tileset.NOTHING;
                occupied[x][y] = false;
            }
        }
    }

    /**
     * Generates rooms until the board is filled to a target area (based on a random density).
     */
    public void generateRooms() {
        RoomDensity density = RoomDensity.getRandomDensity();
        double requiredCoverage = density.getRandomCoverage();
        int totalTiles = width * height;
        int targetTiles = (int) (totalTiles * requiredCoverage);

        int filledTiles = 0;
        while (filledTiles < targetTiles) {
            // Directly generate a random RoomSpec using its static factory method.
            RoomSpec spec = RoomSpec.generateRandom();
            if (placeRoomDirectly(spec.width(), spec.height())) {
                filledTiles += spec.width() * spec.height();
            }
        }
    }

    /**
     * Attempts to place a room directly on the board.
     * Directly "paints" the room onto the grid and updates the occupancy grid.
     * Returns true if placement is successful, false otherwise.
     */
    public boolean placeRoomDirectly(int roomWidth, int roomHeight) {
        // Try up to 100 random candidate positions.
        for (int attempt = 0; attempt < 100; attempt++) {
            int x = RandomUtils.uniform(random, 0, width - roomWidth + 1);
            int y = RandomUtils.uniform(random, 0, height - roomHeight + 1);

            if (canPlaceRoom(x, y, roomWidth, roomHeight)) {
                // Generate the room pattern directly:
                TETile[][] roomTiles = new TETile[roomWidth][roomHeight];
                for (int i = 0; i < roomWidth; i++) {
                    for (int j = 0; j < roomHeight; j++) {
                        int boardX = x + i;
                        int boardY = y + j;

                        if (i == 0 || i == roomWidth - 1 || j == 0 || j == roomHeight - 1) {
                            grid[boardX][boardY] = Tileset.WALL;
                        } else {
                            grid[boardX][boardY] = Tileset.FLOOR;
                        }
                        occupied[boardX][boardY] = true;
                    }
                }
                // Create and record the room metadata.
                Room placedRoom = new Room(roomWidth, roomHeight, roomTiles);
                placedRoom.setPosition(x, y);
                rooms.add(placedRoom);
                return true;
            }
        }
        return false;
    }

    /**
     * Uses the occupancy grid to quickly check if a room of given dimensions can be placed at (x, y).
     */
    private boolean canPlaceRoom(int x, int y, int roomWidth, int roomHeight) {
        for (int i = x; i < x + roomWidth; i++) {
            for (int j = y; j < y + roomHeight; j++) {
                if (occupied[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public List<Room> getRooms() {
        return List.copyOf(rooms);
    }

    public TETile[][] getGrid() {
        return grid;
    }
}



