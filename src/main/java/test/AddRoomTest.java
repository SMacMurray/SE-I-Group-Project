package test;

import org.junit.jupiter.api.Test;
import stay_and_shop_system.occupancy.Room;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AddRoomTest {

    @Test
    void addRoomCreatesRoomObject() {
        Room room = new Room(101, 2, 4, 150.0, false,
                new ArrayList<>(List.of(Room.BedType.Queen)),
                Room.QualityLevel.Comfort,
                Room.RoomSize.Double);

        assertNotNull(room);
    }

    @Test
    void addRoomStoresRoomNumber() {
        Room room = new Room(102, 1, 2, 99.0, true,
                new ArrayList<>(List.of(Room.BedType.King)),
                Room.QualityLevel.Economy,
                Room.RoomSize.Single);

        assertEquals(102, room.getNumber());
    }

    @Test
    void addRoomStoresBeds() {
        Room room = new Room(103, 3, 5, 200.0, false,
                new ArrayList<>(List.of(Room.BedType.Full)),
                Room.QualityLevel.Business,
                Room.RoomSize.Family);

        assertEquals(3, room.getBeds());
    }

    @Test
    void addRoomStoresRate() {
        Room room = new Room(104, 2, 4, 175.5, false,
                new ArrayList<>(List.of(Room.BedType.Queen)),
                Room.QualityLevel.Executive,
                Room.RoomSize.Suite);

        assertEquals(175.5, room.getBaseDailyRate());
    }

    @Test
    void addRoomStoresSmokingStatus() {
        Room room = new Room(105, 2, 4, 120.0, true,
                new ArrayList<>(List.of(Room.BedType.Queen)),
                Room.QualityLevel.Comfort,
                Room.RoomSize.Double);

        assertTrue(room.getSmokingStatus());
    }
}