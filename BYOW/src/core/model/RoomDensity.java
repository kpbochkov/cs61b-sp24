package core.model;

import utils.RandomUtils;
import java.util.Random;

public enum RoomDensity {
    OVER_POPULATED(0.65, 0.8),
    LEAST_POPULATED(0.5, 0.6);

    private final double minCoverage;
    private final double maxCoverage;
    private static final Random RANDOM = new Random();

    RoomDensity(double minCoverage, double maxCoverage) {
        this.minCoverage = minCoverage;
        this.maxCoverage = maxCoverage;
    }

    public double getRandomCoverage() {
        return RandomUtils.uniform(RANDOM, minCoverage, maxCoverage);
    }

    public static RoomDensity getRandomDensity() {
        return values()[RandomUtils.uniform(RANDOM, values().length)];
    }
}

