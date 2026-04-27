package stay_and_shop_system.user;

public interface AdminInterface extends User {
    void createClerkAccount(Clerk clerk);
    void resetUserPassword(User user);
}
