package test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import stay_and_shop_system.occupancy.Room;
import stay_and_shop_system.occupancy.database.RoomRepository;
import stay_and_shop_system.store.Product;
import stay_and_shop_system.store.StoreRepository;
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
        StoreRepository.createTable();
    }
    @Test
    void addUsers() {
        AccountController.createClerk("testClerk@email.com", "Joey Jr", "password", "+1 123-456-7890");
        UserRepository.createEmployeeAccount("testAdmin@email.com", "Joey Jr", Objects.hash("password"), "+1 123-456-7890", User.UserType.ADMIN.ordinal());
    }
    // For UI loading time testing.
    @Test
    void load150Rooms() {
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

            if (i - (100 * (int)(i / 100)) > 50) {
                i = (100 * ((int)((i) / 100) + 1)); // Going to next floor. Allowing for adding rooms.

            }
        }
    }
    @Test
    void addProducts() {
        StoreRepository.addProduct(new Product(1, 20.0, "Cheese sourced from a local farm", "Gourmet Cheese"));
        StoreRepository.addProduct(new Product(2, 50.0, "Curb chain faux gold necklace", "Gold Necklace"));
        StoreRepository.addProduct(new Product(3, 30.0, "\"Ocean's Water\" branded shirt", "Ocean's Water T-Shirt"));
        StoreRepository.addProduct(new Product(4, 20.0, "\"Ocean's Water\" branded miscellaneous fishing bobs and lures", "Ocean's Water Fishing Accessories Set"));
    }
    @AfterAll
    static void dropTables() {
        // RoomRepository.dropTable();
//        ReservationRepository.dropTable();
//        UserRepository.dropTable();
    }
}
