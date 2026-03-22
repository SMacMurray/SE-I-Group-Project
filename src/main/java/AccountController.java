
// Handler for account related tasks.
public class AccountController {
    // Ignore email and phone numbers fields for now for simplicity.
    static int createAccount(String username, String password){
        int hash = password.hashCode();
        boolean accountFound = FauxAccountSystem.findAccount(username, hash);
        if (!accountFound){
            return FauxAccountSystem.createAccount(username, hash);
        }
        return 403;
    }
}
