package test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import stay_and_shop_system.occupancy.Room;
import stay_and_shop_system.occupancy.database.RoomRepository;
import stay_and_shop_system.user.AccountController;
import stay_and_shop_system.user.User;
import stay_and_shop_system.user.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TestData {
    @BeforeAll
    static void init() {
        UserRepository.dropTable();
        UserRepository.initAccountTable();
        RoomRepository.dropTable();
        RoomRepository.createTable();
    }
    @Test
    void addUsers() {
        AccountController.createClerk("testClerk@email.com", "Joey Jr", "password", "+1 909-999-9999");
        UserRepository.createEmployeeAccount("testAdmin@email.com", "Joey Jr", Objects.hash("password"), "+1 909-909-9999", User.UserType.ADMIN.ordinal());
    }
    // For UI loading time testing.
    @Test
    void load150Rooms() {
        for (int i = 100; i < 400; ++i) {
            int bedCount = (int)((Math.random() * 100) % 4);
            if (bedCount == 0) {
                bedCount = 1;
            }
            List< Room.BedType> bedTypes = new ArrayList<>();
            for (int j = 0; j < bedCount; ++j)  {
                bedTypes.add(Room.BedType.values()[(int)(Math.random() * 100) % 4]);
            }
            Room.QualityLevel qualityLevel = Room.QualityLevel.values()[(int)(Math.random() * 100) % 4];
            Room.RoomSize roomSize = Room.RoomSize.values()[(int)(Math.random() * 100) % 6];
            Room r = new Room(i, bedCount, bedCount, Math.random() * 100,
                    !(Math.random() < 0.50), bedTypes,
                    qualityLevel, roomSize
                    );
            RoomRepository.addRoom(r);

            if (i - (100 * (int)(i / 100)) > 50) {
                i = (100 * ((int)((i) / 100) + 1)); // Going to next floor. Allowing for adding rooms.

            }
        }
    }
    @AfterAll
    static void dropTables() {
        // RoomRepository.dropTable();
//        ReservationRepository.dropTable();
//        UserRepository.dropTable();
    }
}
