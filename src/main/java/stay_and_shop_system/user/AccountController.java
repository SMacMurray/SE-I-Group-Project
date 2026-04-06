package stay_and_shop_system.user;

// Handler for account related tasks.
public class AccountController {
    // Ignore email and phone numbers fields for now for simplicity.
    public static int createAccount(String username, String password, String email, String phoneNumber) {
        int output = 1;
        boolean accountFound = AccountSystem.findAccount(username);
        if (!accountFound){
            int hash = password.hashCode();
            output = (AccountSystem.createAccount(username, hash, email, phoneNumber) ? 0 : -1);
        }
        return output;
    }

    public static int login(String username, String password){
        int hash = password.hashCode();
        return (AccountSystem.authenticate(username, hash) ? 0 : 1);
    }
}
