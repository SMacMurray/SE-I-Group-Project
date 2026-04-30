package test;

import org.junit.jupiter.api.Test;
import stay_and_shop_system.occupancy.Room;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ViewAllRoomClerkTest {

    @Test
    void clerkCanViewRoomListThatExists() {
        List<Room> rooms = new ArrayList<>();
        assertNotNull(rooms);
    }

    @Test
    void clerkCanViewOneRoomInList() {
        List<Room> rooms = new ArrayList<>();

        rooms.add(new Room(101, 1, 2, 90.0, false,
                new ArrayList<>(List.of(Room.BedType.Twin)),
                Room.QualityLevel.Economy,
                Room.RoomSize.Single));

        assertEquals(1, rooms.size());
    }

    @Test
    void clerkCanViewMultipleRoomsInList() {
        List<Room> rooms = new ArrayList<>();

        rooms.add(new Room(101, 1, 2, 90.0, false,
                new ArrayList<>(List.of(Room.BedType.Twin)),
                Room.QualityLevel.Economy,
                Room.RoomSize.Single));

        rooms.add(new Room(102, 2, 4, 160.0, true,
                new ArrayList<>(List.of(Room.BedType.Queen)),
                Room.QualityLevel.Comfort,
                Room.RoomSize.Double));

        assertEquals(2, rooms.size());
    }

    @Test
    void clerkCanViewCorrectRoomNumber() {
        Room room = new Room(103, 2, 4, 160.0, false,
                new ArrayList<>(List.of(Room.BedType.Queen)),
                Room.QualityLevel.Comfort,
                Room.RoomSize.Double);

        assertEquals(103, room.getNumber());
    }

    @Test
    void clerkCanViewCorrectRoomRate() {
        Room room = new Room(104, 2, 4, 175.0, false,
                new ArrayList<>(List.of(Room.BedType.Queen)),
                Room.QualityLevel.Business,
                Room.RoomSize.Double);

        assertEquals(175.0, room.getBaseDailyRate());
    }
}