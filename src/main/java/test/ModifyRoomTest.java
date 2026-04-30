package test;

import org.junit.jupiter.api.*;
import stay_and_shop_system.occupancy.Room;

import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class ModifyRoomTest {
    Room room;

    @BeforeEach
    void setup() {
        room = new Room(201, 1, 2, 100.0, false,
                new ArrayList<>(List.of(Room.BedType.Twin)),
                Room.QualityLevel.Economy,
                Room.RoomSize.Single);
    }

    @Test
    void modifyRoomNumber() {
        room.setNumber(202);
        assertEquals(202, room.getNumber());
    }

    @Test
    void modifyBeds() {
        room.setBeds(3);
        assertEquals(3, room.getBeds());
    }

    @Test
    void modifyMaxOccupancy() {
        room.setMaxOccupancy(6);
        assertEquals(6, room.getMaxOccupancy());
    }

    @Test
    void modifyBaseRate() {
        room.setBaseDailyRate(245.99);
        assertEquals(245.99, room.getBaseDailyRate());
    }

    @Test
    void modifySmokingStatus() {
        room.setSmokingStatus(true);
        assertTrue(room.getSmokingStatus());
    }
}