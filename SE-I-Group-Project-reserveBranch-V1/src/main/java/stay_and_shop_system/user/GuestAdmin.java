package stay_and_shop_system.user;

import java.util.Objects;

public class GuestAdmin implements GuestInterface, AdminInterface{
    private String name = "John Doe";
    private String email = "dummyEmail@gmail.com";
    private String phoneNumber;
    private int hash; // Stores the hash of the password, not the password.
    private String paymentId;
    private UserType typeId = UserType.GUEST_ADMIN;
    // private List<Reservation> reservations = new ArrayList<>();

    // Fix when needed
    public GuestAdmin(String e, String n,  int h, String pn,  PaymentMethod pm) {
        name = n;
        email = e;
        phoneNumber = pn;
        hash = h;
        GuestInterface.pm.setPaymentMethod(pm);
    }
    public GuestAdmin(String e, String n, int h, String p, String i) {
        email = e;
        name = n;
        hash = h;
        phoneNumber = p;
        paymentId = i;
    }
    public GuestAdmin(String e, String n, int h, String p) {
        email = e;
        name = n;
        hash = h;
        phoneNumber = p;
    }
    public void setName(String x) {
        name = x;
    }
    public void setEmail(String x) {
        email = x;
    }
    public void setPhoneNumber(String x) { phoneNumber = x; }
    public void setPassword(String x) { hash = x.hashCode(); }
    public void setPaymentId(String pId) { paymentId = pId; }
    public void setTypeId(UserType id) { this.typeId = id; }
    public void setPaymentMethod(PaymentMethod pm) {
        GuestInterface.pm.setPaymentMethod(pm);
    }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }
    public int getPassword() { return hash; }
    public String getPaymentId() { return paymentId; }
    public UserType getTypeId() { return typeId; }
    public int getGuestId() { return Math.abs(Objects.hash(email)); }
    public PaymentMethod getPaymentMethod() {
        return GuestInterface.pm;
    }


    //public List<Reservation> findReservations(){ return res.findReservationsOfName(name); }

    public void createClerkAccount(Clerk clerk) {

    }

    public void resetUserPassword(User user) {

    }


    // Did not hash or use equals by 'name' because names can be the same,
    // but emails can't.
    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Guest)) return false;
        Guest temp = (Guest)o;
        return email.equals(temp.getEmail());
    }
    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
