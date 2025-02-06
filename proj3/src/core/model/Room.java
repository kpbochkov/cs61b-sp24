package core.model;

import tileengine.TETile;

public class Room {
    private final int width;
    private final int height;
    private final TETile[][] roomTiles;
    private int x, y;

    public Room(int width, int height, TETile[][] roomTiles) {
        this.width = width;
        this.height = height;
        this.roomTiles = roomTiles;
        this.x = -1;
        this.y = -1;
    }

    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    public TETile[][] getRoomTiles() {
        return roomTiles;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
}




