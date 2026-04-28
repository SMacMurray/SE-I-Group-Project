package stay_and_shop_system.user;

public interface User {
    void setName(String x);
    void setEmail(String x);
    void setPhoneNumber(String x);
    void setPassword(String x);
    void setTypeId(UserType ut);
    String getName();
    String getEmail();
    String getPhoneNumber();
    int getPassword();
    UserType getTypeId();

    public enum UserType {
        ADMIN,
        CLERK,
        GUEST_ADMIN,
        GUEST_CLERK,
        GUEST
    }
}