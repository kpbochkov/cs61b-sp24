package core.model;

public record RoomDimensionSpec(int width, int height) {

    public static RoomDimensionSpec generateRandom() {
        RoomSize size = RoomSize.getRandomRoomSize();
        int width = size.getRandomSize();
        int height = size.getRandomSize();
        return new RoomDimensionSpec(width, height);
    }
}
