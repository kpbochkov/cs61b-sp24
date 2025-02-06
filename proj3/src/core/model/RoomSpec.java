package core.model;

import java.util.Random;

public record RoomSpec(int width, int height) {
    /**
     * Generates a random RoomSpec using a random RoomSize.
     */
    public static RoomSpec generateRandom() {
        RoomSize size = RoomSize.getRandomRoomSize();
        int width = size.getRandomSize();
        int height = size.getRandomSize();
        return new RoomSpec(width, height);
    }
}
