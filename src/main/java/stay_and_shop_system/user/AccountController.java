package stay_and_shop_system.user;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import stay_and_shop_system.occupancy.Reservation;

import java.sql.SQLException;
import java.util.Objects;

// Handler for account related tasks.
public class AccountController {
    // Ignore email and phone numbers fields for now for simplicity.
    // In case we ever decide to change how passwords are hashed.
    private static int hashPassword(String password) { return Objects.hash(password); }

    public static boolean validatePhoneNumber(String phoneNumber){
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        Phonenumber.PhoneNumber guestPhoneNumber = new Phonenumber.PhoneNumber();
        System.out.println(phoneNumber);
        boolean validPhoneNumber;
        try {
            guestPhoneNumber = phoneUtil.parse(phoneNumber, null);
            validPhoneNumber = phoneUtil.isValidNumber(guestPhoneNumber);
        } catch (NumberParseException e) {
            return false;
        }
        return validPhoneNumber;
    }

    public static int createAccount(String email, String username, String password, String phoneNumber){
        int hash = hashPassword(password);
        if (!validatePhoneNumber(phoneNumber)) { return 2; }
        if (!Reservation.validateEmail(email)) { return 3; }
        boolean accountFound = UserRepository.findAccount(email);
        if (!accountFound){
            return (UserRepository.createAccount(email, username, hash, phoneNumber) ? 0 : -1);
        }
        return 1;
    }

    public static int createClerk(String email, String username, String password, String phoneNumber){
        int hash = hashPassword(password);
        if (!validatePhoneNumber(phoneNumber)) { return 2; }
        if (!Reservation.validateEmail(email)) { return 3; }
        boolean accountFound = UserRepository.findAccount(email);
        if (!accountFound){
            return (UserRepository.createClerk(email, username, hash, phoneNumber) ? 0 : -1);
        }
        return 1;
    }

    public static int login(String email, String password){
        int hash = hashPassword(password);
        return (UserRepository.authenticate(email, hash) ? 0 : 1);
    }

    public static int logout(){
        try {
            UserRepository.setSessionAccount(null);
            return 0;
        }
        catch (SQLException e){
            System.out.println(e);
            e.printStackTrace();
        }
        return 1;
    }

    public static int updatePassword(String email, String password){
        int hash = hashPassword(password);
        if (!Reservation.validateEmail(email)) { return 3; }
        boolean accountFound = UserRepository.findUser(email);
        if (accountFound){
            return (UserRepository.updatePassword(email, hash) ? 0 : -1);
        }
        return 1;
    }
}
