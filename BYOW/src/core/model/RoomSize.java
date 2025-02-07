package core.model;

import utils.RandomUtils;

import java.util.Random;

public enum RoomSize {
    SMALL(4, 6),
    MEDIUM(7, 10),
    LARGE(11, 15);

    private final int minSize;
    private final int maxSize;
    private static final Random RANDOM = new Random();

    RoomSize(int minSize, int maxSize) {
        this.minSize = minSize;
        this.maxSize = maxSize;
    }

    public int getRandomSize() {
        return minSize + RandomUtils.uniform(RANDOM, maxSize - minSize + 1);
    }

    public static RoomSize getRandomRoomSize() {
        return values()[RandomUtils.uniform(RANDOM, values().length)];
    }

    public int getMinSize() {
        return minSize;
    }

    public int getMaxSize() {
        return maxSize;
    }
}