package tetris;

import edu.princeton.cs.algs4.StdDraw;
import tileengine.TETile;
import tileengine.TERenderer;
import tileengine.Tileset;

import java.awt.*;
import java.util.*;

/**
 * Provides the logic for Tetris.
 *
 * @author Erik Nelson, Omar Yu, Noah Adhikari, Jasmine Lin
 */

public class Tetris {

    private static int WIDTH = 10;
    private static int HEIGHT = 20;

    // Tetrominoes spawn above the area we display, so we'll have our Tetris board have a
    // greater height than what is displayed.
    private static int GAME_HEIGHT = 25;

    // Contains the tiles for the board.
    private TETile[][] board;

    // Helps handle movement of pieces.
    private Movement movement;

    // Checks for if the game is over.
    private boolean isGameOver;

    // The current Tetromino that can be controlled by the player.
    private Tetromino currentTetromino;

    // The current game's score.
    private int score;

    /**
     * Checks for if the game is over based on the isGameOver parameter.
     *
     * @return boolean representing whether the game is over or not
     */
    private boolean isGameOver() {
        return isGameOver;
    }

    /**
     * Renders the game board and score to the screen.
     */
    private void renderBoard() {
        ter.drawTiles(board);
        renderScore();
        StdDraw.show();

        if (auxFilled) {
            auxToBoard();
        } else {
            fillBoard(Tileset.NOTHING);
        }
    }

    /**
     * Creates a new Tetromino and updates the instance variable
     * accordingly. Flags the game to end if the top of the board
     * is filled and the new piece cannot be spawned.
     */
    private void spawnPiece() {
        // The game ends if this tile is filled
        if (board[4][19] != Tileset.NOTHING) {
            isGameOver = true;
        }

        // Otherwise, spawn a new piece and set its position to the spawn point
        currentTetromino = Tetromino.values()[bagRandom.getValue()];
        currentTetromino.reset();
    }

    /**
     * Updates the board based on the user input. Makes the appropriate moves
     * depending on the user's input.
     */
    private void updateBoard() {
        // Grabs the current piece.
        Tetromino t = currentTetromino;
        if (actionDeltaTime() > 1000) {
            movement.dropDown();
            resetActionTimer();
            Tetromino.draw(t, board, t.pos.x, t.pos.y);
            return;
        }

        // TODO: Implement interactivity, so the user is able to input the keystrokes to move
        //  the tile and rotate the tile. You'll want to use some provided helper methods here.
        if (StdDraw.hasNextKeyTyped()) {
            final char c = StdDraw.nextKeyTyped();
            switch (c) {
                case 'a': movement.tryMove(-1, 0); break;
                case 'd': movement.tryMove(1, 0); break;
                case 's': movement.dropDown(); break;
                case 'q': movement.rotateLeft(); break;
                case 'w': movement.rotateRight(); break;
                default: break;
            }
        }

        Tetromino.draw(t, board, t.pos.x, t.pos.y);
    }

    /**
     * Increments the score based on the number of lines that are cleared.
     *
     * @param linesCleared
     */
    private void incrementScore(int linesCleared) {
        // TODO: Increment the score based on the number of lines cleared.
        if (linesCleared == 1) {
            score = score + 100;
        } else if (linesCleared == 2) {
            score = score + 300;
        } else if (linesCleared == 3) {
            score = score + 500;
        } else if (linesCleared == 4) {
            score = score + 800;
        }
    }

    /**
     * Clears lines/rows on the provided tiles/board that are horizontally filled.
     * Repeats this process for cascading effects and updates score accordingly.
     *
     * @param tiles
     */
    public void clearLines(TETile[][] tiles) {
        int cols = tiles.length; // Board width
        int rows = tiles[0].length; // Board height
        int linesCleared = 0;

        // Iterate through rows from the bottom up
        for (int y = 0; y < rows; y++) {
            boolean isRowFull = true;

            // Check if the current row is full
            for (int x = 0; x < cols; x++) {
                if (tiles[x][y] == Tileset.NOTHING) {
                    isRowFull = false;
                    break;
                }
            }

            // If the row is full, clear it and shift everything above downward
            if (isRowFull) {
                linesCleared++;
                shiftRowsDown(tiles, y);
                y--; // Recheck the current row as rows above have shifted
            }
        }

        // Increment score based on cleared lines
        if (linesCleared > 0) {
            incrementScore(linesCleared);
        }
    }

    /**
     * Shifts all rows above the given rowIndex downward by one row.
     * Clears the topmost row after shifting.
     *
     * @param tiles The board tiles
     * @param rowIndex The row to start shifting from
     */
    private void shiftRowsDown(TETile[][] tiles, int rowIndex) {
        int cols = tiles.length; // Board width

        // Shift rows above the current rowIndex down by one
        for (int y = rowIndex; y < tiles[0].length - 1; y++) {
            for (int x = 0; x < cols; x++) {
                tiles[x][y] = tiles[x][y + 1];
            }
        }

        // Clear the topmost row
        for (int x = 0; x < cols; x++) {
            tiles[x][tiles[0].length - 1] = Tileset.NOTHING;
        }
    }

    /**
     * Where the game logic takes place. The game should continue as long as the game isn't
     * over.
     */
    public void runGame() {
        if (currentTetromino == null) {
            spawnPiece();
        }
        renderBoard();

        // Game loop
        while (!isGameOver()) {
            // Clear any completed lines and update the score
            clearLines(board);

            // Update the board based on user input
            updateBoard();


            // Render the board and score
            renderBoard();
            renderScore();

            // If the current piece can't move down, spawn a new one
            if (currentTetromino == null) {
                spawnPiece();
            }

//            // Pause to control game speed
//            StdDraw.pause(200); // Adjust pause duration as needed
        }

        // Game over logic
        System.out.println("Game Over! Your final score is: " + score);
        // TODO: Set up your game loop. The game should keep running until the game is over.
        // Use helper methods inside your game loop, according to the spec description.


    }


    /**
     * Renders the score using the StdDraw library.
     */
    private void renderScore() {
        // TODO: Use the StdDraw library to draw out the score.
        // Clear any previous text in the score area
        StdDraw.setPenColor(StdDraw.BLACK); // Set to black to clear the background
        StdDraw.filledRectangle(7, 19, 2, 0.5); // Clear a small area for the score display

        // Set the color of the text to white
        StdDraw.setPenColor(new Color(255, 255, 255)); // RGB for white

        // Set the font for the score display
        Font font = new Font("Arial", Font.BOLD, 18); // Customize font as needed
        StdDraw.setFont(font);

        // Draw the score at position x = 7, y = 19
        StdDraw.text(7, 19, "Score: " + score);

        // Render the updated score
        StdDraw.show();
    }

    /**
     * Use this method to run Tetris.
     *
     * @param args
     */
    public static void main(String[] args) {
        long seed = args.length > 0 ? Long.parseLong(args[0]) : (new Random()).nextLong();
        Tetris tetris = new Tetris(seed);
        tetris.runGame();
    }

    /**
     * Everything below here you don't need to touch.
     */

    // This is our tile rendering engine.
    private final TERenderer ter = new TERenderer();

    // Used for randomizing which pieces are spawned.
    private Random random;
    private BagRandomizer bagRandom;

    private long prevActionTimestamp;
    private long prevFrameTimestamp;

    // The auxiliary board. At each time step, as the piece moves down, the board
    // is cleared and redrawn, so we keep an auxiliary board to track what has been
    // placed so far to help render the current game board as it updates.
    private TETile[][] auxiliary;
    private boolean auxFilled;

    public Tetris() {
        board = new TETile[WIDTH][GAME_HEIGHT];
        auxiliary = new TETile[WIDTH][GAME_HEIGHT];
        random = new Random(new Random().nextLong());
        bagRandom = new BagRandomizer(random, Tetromino.values().length);
        auxFilled = false;
        movement = new Movement(WIDTH, GAME_HEIGHT, this);
        fillBoard(Tileset.NOTHING);
        fillAux();
    }

    public Tetris(long seed) {
        board = new TETile[WIDTH][GAME_HEIGHT];
        auxiliary = new TETile[WIDTH][GAME_HEIGHT];
        random = new Random(seed);
        bagRandom = new BagRandomizer(random, Tetromino.values().length);
        auxFilled = false;
        movement = new Movement(WIDTH, GAME_HEIGHT, this);

        ter.initialize(WIDTH, HEIGHT);
        fillBoard(Tileset.NOTHING);
        fillAux();
    }

    // Setter and getter methods.

    /**
     * Returns the current game board.
     *
     * @return
     */
    public TETile[][] getBoard() {
        return board;
    }

    /**
     * Returns the score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Returns the current auxiliary board.
     *
     * @return
     */
    public TETile[][] getAuxiliary() {
        return auxiliary;
    }


    /**
     * Returns the current Tetromino/piece.
     *
     * @return
     */
    public Tetromino getCurrentTetromino() {
        return currentTetromino;
    }

    /**
     * Sets the current Tetromino to null.
     *
     * @return
     */
    public void setCurrentTetromino() {
        currentTetromino = null;
    }

    /**
     * Sets the boolean auxFilled to true;
     */
    public void setAuxTrue() {
        auxFilled = true;
    }

    /**
     * Fills the entire board with the specific tile that is passed in.
     *
     * @param tile
     */
    private void fillBoard(TETile tile) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = tile;
            }
        }
    }

    /**
     * Copies the contents of the src array into the dest array using
     * System.arraycopy.
     *
     * @param src
     * @param dest
     */
    private static void copyArray(TETile[][] src, TETile[][] dest) {
        for (int i = 0; i < src.length; i++) {
            System.arraycopy(src[i], 0, dest[i], 0, src[0].length);
        }
    }

    /**
     * Copies over the tiles from the game board to the auxiliary board.
     */
    public void fillAux() {
        copyArray(board, auxiliary);
    }

    /**
     * Copies over the tiles from the auxiliary board to the game board.
     */
    private void auxToBoard() {
        copyArray(auxiliary, board);
    }

    /**
     * Calculates the delta time with the previous action.
     *
     * @return the amount of time between the previous Tetromino movement with the present
     */
    private long actionDeltaTime() {
        return System.currentTimeMillis() - prevActionTimestamp;
    }

    /**
     * Resets the action timestamp to the current time in milliseconds.
     */
    private void resetActionTimer() {
        prevActionTimestamp = System.currentTimeMillis();
    }

}
