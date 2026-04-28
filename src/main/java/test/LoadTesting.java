package test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import stay_and_shop_system.occupancy.Room;
import stay_and_shop_system.occupancy.database.ReservationRepository;
import stay_and_shop_system.occupancy.database.RoomRepository;
import stay_and_shop_system.user.UserRepository;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class LoadTesting {
    @BeforeAll
    static void init() {
        RoomRepository.dropTable();
        RoomRepository.createTable();
    }
    // For UI loading time testing.
    // Could not find a way to prevent loading rooms on the screen affecting the scroll pane.
    @Test
    void load300Rooms() {
        for (int i = 100; i < 400; ++i) {
            List< Room.BedType> bedTypes = new ArrayList<>();
            for (int j = 0; j < (Math.random() * 100) % 4; ++j)  {
                bedTypes.add(Room.BedType.values()[(int)(Math.random() * 100) % 4]);
            }
            Room.QualityLevel qualityLevel = Room.QualityLevel.values()[(int)(Math.random() * 100) % 4];
            Room.RoomSize roomSize = Room.RoomSize.values()[(int)(Math.random() * 100) % 6];
            Room r = new Room(i, (int)(Math.random() * 100), (int)(Math.random() * 100), Math.random() * 100,
                    !(Math.random() < 0.50), bedTypes,
                    qualityLevel, roomSize
                    );
            RoomRepository.addRoom(r);
        }
    }
    @AfterAll
    static void dropTables() {
        // RoomRepository.dropTable();
//        ReservationRepository.dropTable();
//        UserRepository.dropTable();
    }
}
