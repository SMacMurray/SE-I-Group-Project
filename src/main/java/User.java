public class User {
    private String name;
    private String email;
    private String phoneNumber;
    private int hash; // Stores the hash of the password, not the password.

    // Dummy constructor, can probably remove this in the future.
    User(){
        name = "Joey JoJo Jr. Shabadoo";
        hash = 0;
    }

    User(String name, int hash){
        this.name = name;
        this.hash = hash;
    }

    void setName(String x){ name = x; }
    void setEmail(String x){ email = x; }
    void setPhoneNumber(String x){ phoneNumber = x; }
    void setPassword(String x){ hash = x.hashCode(); }

    String getName(){ return name; }
    String getEmail(){ return email; }
    String getPhoneNumber(){ return phoneNumber; }
    int getPassword(){ return hash; }
}
