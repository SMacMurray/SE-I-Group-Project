package stay_and_shop_system.user;

import stay_and_shop_system.occupancy.Reservation;
import stay_and_shop_system.occupancy.Room;

import java.util.List;
import java.util.Objects;

public class GuestClerk implements GuestInterface, ClerkInterface{
    private String name = "John Doe";
    private String email = "dummyEmail@gmail.com";
    private String phoneNumber;
    private int hash; // Stores the hash of the password, not the password.
    private String paymentId;
    private UserType typeId = UserType.GUEST_CLERK;

    // Fix when needed
    public GuestClerk(String e, String n,  int h, String pn,  PaymentMethod pm) {
        name = n;
        email = e;
        phoneNumber = pn;
        hash = h;
        GuestInterface.pm.setPaymentMethod(pm);
    }
    // When a new Guest Account as a clerk is created/logged in.
    public GuestClerk(String e, String n, int h, String p, String i) {
        email = e;
        name = n;
        hash = h;
        phoneNumber = p;
        paymentId = i;
    }
    public GuestClerk(String e, String n, int h, String p) {
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

    public boolean addRoom(Room r) {
        return false;
    }

    public void modifyOwnInformation(Clerk clerk) {

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
