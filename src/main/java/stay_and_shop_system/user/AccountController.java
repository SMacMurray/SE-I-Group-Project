package stay_and_shop_system.user;

import java.sql.SQLException;

// Handler for account related tasks.
public class AccountController {
    // Ignore email and phone numbers fields for now for simplicity.
    public static int createAccount(String username, String password, String email, String phoneNumber){
        int hash = password.hashCode();
        boolean accountFound = AccountSystem.findAccount(username);
        if (!accountFound){
            return (AccountSystem.createAccount(username, hash, email, phoneNumber) ? 0 : -1);
        }
        return 1;
    }

    public static int login(String username, String password){
        int hash = password.hashCode();
        return (AccountSystem.authenticate(username, hash) ? 0 : 1);
    }

    public static int logout(){
        try {
            AccountSystem.setSessionAccount(null);
            return 0;
        }
        catch (SQLException e){
            System.out.println(e);
            e.printStackTrace();
        }
        return 1;
    }
}
