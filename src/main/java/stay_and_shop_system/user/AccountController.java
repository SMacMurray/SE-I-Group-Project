package stay_and_shop_system.user;

import java.sql.SQLException;
import java.util.Objects;

// Handler for account related tasks.
public class AccountController {
    // In case we ever decide to change how passwords are hashed.
    private static int hashPassword(String password) { return Objects.hash(password); }

    public static int createAccount(String email, String username, String password, String phoneNumber){
        int hash = hashPassword(password);
        boolean accountFound = UserRepository.findAccount(email);
        if (!accountFound){
            return (UserRepository.createAccount(email, username, hash, phoneNumber) ? 0 : -1);
        }
        return 1;
    }

    public static int createClerk(String email, String username, String password, String phoneNumber){
        int hash = hashPassword(password);
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
        boolean accountFound = UserRepository.findUser(email);
        if (accountFound){
            return (UserRepository.updatePassword(email, hash) ? 0 : -1);
        }
        return 1;
    }
}
