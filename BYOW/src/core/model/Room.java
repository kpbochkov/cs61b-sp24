package core.model;

public class Room {
    private final int width;
    private final int height;
    private int x, y;

    // Updated constructor to include position
    public Room(int width, int height, int x, int y) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    // Optional method to set position later
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
}





