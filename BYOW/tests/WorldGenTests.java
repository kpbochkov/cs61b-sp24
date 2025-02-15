import core.model.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class WorldGenTests {

    private static final int BOARD_WIDTH = 60;
    private static final int BOARD_HEIGHT = 30;
    // Pre-generate a board once for tests that only inspect properties
    private static Board preGeneratedBoard;
//    @Test
//    public void basicTest() {
//        // put different seeds here to test different worlds
//        TETile[][] tiles = AutograderBuddy.getWorldFromInput("n1234567890123456789s");
//
//        TERenderer ter = new TERenderer();
//        ter.initialize(tiles.length, tiles[0].length);
//        ter.renderFrame(tiles);
//        StdDraw.pause(5000); // pause for 5 seconds so you can see the output
//    }
//
//    @Test
//    public void basicInteractivityTest() {
//        // TODO: write a test that uses an input like "n123swasdwasd"
//    }
//
//    @Test
//    public void basicSaveTest() {
//        // TODO: write a test that calls getWorldFromInput twice, with "n123swasd:q" and with "lwasd"
//    }

    @BeforeAll
    public static void setUp() {
        preGeneratedBoard = new Board(BOARD_WIDTH, BOARD_HEIGHT);
        preGeneratedBoard.generateWorld();
        System.out.println("Pre-generated Rooms:");
        for (Room room : preGeneratedBoard.getRooms()) {
            System.out.println("Room at (" + room.getX() + ", " + room.getY() + ") " +
                    "Size: " + room.getWidth() + "x" + room.getHeight());
        }
    }

    // ---------------------------
    // Automated Tests
    // ---------------------------

    @Test
    public void testRoomSizeGeneration() {
        for (int i = 0; i < 100; i++) {
            RoomSize size = RoomSize.getRandomRoomSize();
            int generatedWidth = size.getRandomSize();
            int generatedHeight = size.getRandomSize();

            assertTrue(generatedWidth >= size.getMinSize() && generatedWidth <= size.getMaxSize(),
                    "Width out of range for " + size);
            assertTrue(generatedHeight >= size.getMinSize() && generatedHeight <= size.getMaxSize(),
                    "Height out of range for " + size);
        }
    }

    @Test
    public void testRoomGeneration() {
        RoomDimensionSpec spec = RoomDimensionSpec.generateRandom();
        Board board = new Board(BOARD_WIDTH, BOARD_HEIGHT);
        boolean placed = board.placeRoomOnBoard(spec.width(), spec.height());
        assertTrue(placed, "Room should be placed successfully");

        Room room = board.getRooms().get(board.getRooms().size() - 1);
        TETile[][] grid = board.getGrid();
        int startX = room.getX();
        int startY = room.getY();

        for (int x = 0; x < room.getWidth(); x++) {
            for (int y = 0; y < room.getHeight(); y++) {
                int boardX = startX + x;
                int boardY = startY + y;

                if (x == 0 || x == room.getWidth() - 1 || y == 0 || y == room.getHeight() - 1) {
                    assertEquals(Tileset.WALL, grid[boardX][boardY], "Expected WALL");
                } else {
                    assertEquals(Tileset.FLOOR, grid[boardX][boardY], "Expected FLOOR");
                }
            }
        }
    }

    @Test
    public void testBoardInitialization() {
        Board board = new Board(BOARD_WIDTH, BOARD_HEIGHT);
        assertNotNull(board.getGrid(), "Grid should not be null");
        assertEquals(BOARD_WIDTH, board.getGrid().length, "Grid width mismatch");
        assertEquals(BOARD_HEIGHT, board.getGrid()[0].length, "Grid height mismatch");
    }

    @Test
    public void testRoomPlacementOnBoard() {
        assertFalse(preGeneratedBoard.getRooms().isEmpty(), "Rooms should be added to the board");
    }

    @Test
    public void testBoardPopulationAbove50Percent() {
        TETile[][] grid = preGeneratedBoard.getGrid();
        int filledTiles = 0;
        int totalTiles = BOARD_WIDTH * BOARD_HEIGHT;
        for (int x = 0; x < BOARD_WIDTH; x++) {
            for (int y = 0; y < BOARD_HEIGHT; y++) {
                if (grid[x][y] != Tileset.NOTHING) {
                    filledTiles++;
                }
            }
        }
        double filledPercentage = filledTiles / (double) totalTiles;
        System.out.println("Board coverage: " + filledPercentage);
        assertTrue(filledPercentage >= 0.5, "Board coverage is less than expected: " + filledPercentage);
    }

    @Test
    @Timeout(5)
    public void testRoomsDoNotOverlap() {
        Set<String> occupiedTiles = new HashSet<>();
        System.out.println("Verifying room overlap for pre-generated board...");
        for (Room room : preGeneratedBoard.getRooms()) {
            int xStart = room.getX();
            int yStart = room.getY();
            int roomWidth = room.getWidth();
            int roomHeight = room.getHeight();

            for (int x = xStart; x < xStart + roomWidth; x++) {
                for (int y = yStart; y < yStart + roomHeight; y++) {
                    String key = x + "," + y;
                    assertFalse(occupiedTiles.contains(key),
                            "Overlap detected at: (" + x + ", " + y + ") for room at (" +
                                    xStart + ", " + yStart + ")");
                    occupiedTiles.add(key);
                }
            }
        }
    }

    @Test
    public void testRoomDensity() {
        // Verify that RoomDensity produces coverage values within the expected range.
        for (int i = 0; i < 50; i++) {
            RoomDensity density = RoomDensity.getRandomDensity();
            double coverage = density.getRandomCoverage();
            assertTrue(coverage >= 0.5 && coverage <= 0.8, "Coverage out of range: " + coverage);
        }
    }

    // ---------------------------
    // Visualization Tests
    // ---------------------------

    @Test
    public void testVisualizeWorldWithRandomRooms() {
        int width = BOARD_WIDTH;
        int height = BOARD_HEIGHT;
        Board board = new Board(width, height);
        board.generateWorld();

        System.out.println("Generated Rooms (from visualize test):");
        for (Room room : board.getRooms()) {
            System.out.println("Room at (" + room.getX() + ", " + room.getY() + ") " +
                    "Size: " + room.getWidth() + "x" + room.getHeight());
        }

        TERenderer renderer = new TERenderer();
        renderer.initialize(width, height);
        renderer.renderFrameTest(board);

        System.out.println("Press Enter to exit visualization...");
        new Scanner(System.in).nextLine();
    }

    @Test
    public void testVisualizePreGeneratedBoard() {
        TERenderer renderer = new TERenderer();
        renderer.initialize(BOARD_WIDTH, BOARD_HEIGHT);
        renderer.renderFrame(preGeneratedBoard.getGrid());

        System.out.println("Press Enter to exit visualization of pre-generated board...");
        new Scanner(System.in).nextLine();
    }

    @Test
    public void testVisualizeTwoRooms() {
        generateRoomsTestOne(2);
    }

    @Test
    public void testVisualizeTwentyRooms() {
        generateRoomsTestOne(20);
    }

    private void generateRoomsTestOne(int roomCount) {
        Board board = new Board(BOARD_WIDTH, BOARD_HEIGHT);
        TERenderer renderer = new TERenderer();
        renderer.initialize(BOARD_WIDTH, BOARD_HEIGHT);

        for (int i = 0; i < roomCount; i++) {
            RoomDimensionSpec spec = RoomDimensionSpec.generateRandom();
            boolean placed = board.placeRoomOnBoard(spec.width(), spec.height());
            if (!placed) {
                System.out.println("Room " + i + " could not be placed.");
            }
        }

        renderer.renderFrame(board.getGrid());
        System.out.println("Press Enter to exit visualization...");
        new Scanner(System.in).nextLine();
    }

}
