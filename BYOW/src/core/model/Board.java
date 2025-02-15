package core.model;

import edu.princeton.cs.algs4.StdDraw;
import tileengine.TETile;
import tileengine.Tileset;
import utils.RandomUtils;

import java.awt.*;
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

    public void generateWorld() {
        generateRooms();
        generateHallways();
    }

    private void generateRooms() {
        RoomDensity density = RoomDensity.getRandomDensity();
        double requiredCoverage = density.getRandomCoverage();
        int totalTiles = width * height;
        int targetTiles = (int) (totalTiles * requiredCoverage);

        int filledTiles = 0;
        while (filledTiles < targetTiles) {
            RoomDimensionSpec spec = RoomDimensionSpec.generateRandom();
            if (placeRoomOnBoard(spec.width(), spec.height())) {
                filledTiles += spec.width() * spec.height();
            }
        }
    }

    private void generateHallways() {

    }


    public boolean placeRoomOnBoard(int roomWidth, int roomHeight) {
        for (int attempt = 0; attempt < 100; attempt++) {
            int x = RandomUtils.uniform(random, 0, width - roomWidth + 1);
            int y = RandomUtils.uniform(random, 0, height - roomHeight + 1);

            if (canPlaceRoom(x, y, roomWidth, roomHeight)) {
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
                Room placedRoom = new Room(roomWidth, roomHeight, x, y);
                rooms.add(placedRoom);
                return true;
            }
        }
        return false;
    }

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
        return rooms;
    }

    public TETile[][] getGrid() {
        return grid;
    }
}



