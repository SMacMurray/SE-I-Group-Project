package test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
//import stay_and_shop_system.LoadCSV;
import stay_and_shop_system.DatabaseConnection;
import stay_and_shop_system.occupancy.Reservation;
import stay_and_shop_system.occupancy.ReservationController;
import stay_and_shop_system.occupancy.Room;
import stay_and_shop_system.occupancy.RoomService;
import stay_and_shop_system.occupancy.database.ReservationRepository;
import stay_and_shop_system.occupancy.database.RoomRepository;
import stay_and_shop_system.user.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.*;

import org.sqlite.SQLiteConnection;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;


public class CreateAccountTesting {
    private static final String DATABASE_URL = "jdbc:sqlite:hotelSystem.db";
    private static final String BACKUP_URL = "jdbc:sqlite:backup.db";
    private static final String DATABASE_PATH = "./hotelSystem.db";
    private static final String BACKUP_PATH = "./backup.db";

    /*
    Literally copy the db file every time we do a test and restore it
    afterwards. There is no reason backing up a SQLite db has to be as
    hard as it is.
    */
    @BeforeEach
    void backupDatabase() throws IOException {
        Path original = Paths.get(DATABASE_PATH);
        Path backup = Paths.get(BACKUP_PATH);
        Files.copy(original, backup, StandardCopyOption.REPLACE_EXISTING);

        UserRepository.dropTable();
        UserRepository.initAccountTable();
        UserRepository.setUser(null);
    }
    @AfterEach
    void restoreDatabase() throws IOException {
        Path original = Paths.get(DATABASE_PATH);
        Path backup = Paths.get(BACKUP_PATH);
        Files.copy(backup, original, StandardCopyOption.REPLACE_EXISTING);
        Files.delete(backup);
    }

    @Test
    void createAccount() {
        AccountController.createAccount("test@email.com", "Joey JoJo Jr. Shabadoo", "Password", "+1 123-456-7890");
    }

    @Test
    void createAndFindAccount() {
        AccountController.createAccount("test@email.com", "Joey JoJo Jr. Shabadoo", "Password", "+1 123-456-7890");
        assert(UserRepository.findAccount("test@email.com"));
    }

    @Test
    void signIn() {
        AccountController.createAccount("test@email.com", "Joey JoJo Jr. Shabadoo", "Password", "+1 123-456-7890");
        AccountController.login("test@email.com", "Password");
        User user = UserRepository.getSessionAccount();
        assertNotNull(user);
        assertInstanceOf(GuestInterface.class, user);
    }

    @Test
    void signInBadEmail() {
        AccountController.createAccount("test@email.com", "Joey JoJo Jr. Shabadoo", "Password", "+1 123-456-7890");
        assertEquals(1, AccountController.login("EEEEEEEEEEEEEEEEEEEEmail@email.com", "Password"));
    }

    @Test
    void signInBadPassword() {
        AccountController.createAccount("test@email.com", "Joey JoJo Jr. Shabadoo", "Password", "+1 123-456-7890");
        assertEquals(1, AccountController.login("test@email.com", "LETMEIN"));
    }

    @Test
    void logOut() {
        AccountController.createAccount("test@email.com", "Joey JoJo Jr. Shabadoo", "Password", "+1 123-456-7890");
        AccountController.login("test@email.com", "Password");
        AccountController.logout();
        assertNull(UserRepository.getSessionAccount());
    }

    @Test
    void createClerk() {
        AccountController.createClerk("test@email.com", "Joey JoJo Jr. Shabadoo", "Password", "+1 123-456-7890");
    }

    @Test
    void signInClerk() {
        AccountController.createClerk("test@email.com", "Joey JoJo Jr. Shabadoo", "Password", "+1 123-456-7890");
        AccountController.login("test@email.com", "Password");
        User user = UserRepository.getSessionAccount();
        assertNotNull(user);
        assertInstanceOf(ClerkInterface.class, user);
    }

    @Test
    void updatePassword() {
        AccountController.createAccount("test@email.com", "Joey JoJo Jr. Shabadoo", "Password", "+1 123-456-7890");
        AccountController.updatePassword("test@email.com", "PASSWORD");
        assertEquals(1, AccountController.login("test@email.com", "Password"));
    }

    @Test
    void updatePasswordBadEmail() {
        AccountController.createAccount("test@email.com", "Joey JoJo Jr. Shabadoo", "Password", "+1 123-456-7890");
        assertEquals(1, AccountController.updatePassword("EEEEEEEEEEEEEEmail@email.com", "PASSWORD"));
    }
}
