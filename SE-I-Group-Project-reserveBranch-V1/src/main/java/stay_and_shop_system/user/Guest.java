package stay_and_shop_system.user;

import java.util.*;

// USer needs Email and Name at minimum for reservation; email and password  at minimum for account
// Need to make sure the user's name lines up with the one they make as the reservation
public class Guest implements GuestInterface {
	private String name = "John Doe";
    private String email = "dummyEmail@gmail.com";
    private String paymentId = "";
    private String phoneNumber;
    private int hash; // Stores the hash of the password, not the password.
    private UserType typeId = UserType.GUEST;


    public Guest(String n, String e, String p, PaymentMethod pm) {
        name = n;
        email = e;
        phoneNumber = p;
        GuestInterface.pm.setPaymentMethod(pm);
    }
    public Guest(String n, String e, int h, String p, PaymentMethod pm) {
        name = n;
        email = e;
        phoneNumber = p;
        hash = h;
        GuestInterface.pm.setPaymentMethod(pm);
    }
    public Guest(String n, String e) {
    	name = n;
    	email = e;
    }
    public Guest(String n, int h) {
    	name = n;
    	hash = h;
        throw new RuntimeException("TODO: Make sure creating an account requires putting an email also");
    }
    public Guest(String e, String n, int h, String p, String i) {
        email = e;
        name = n;
        hash = h;
        phoneNumber = p;
        paymentId = i;
    }
    public Guest(String e, String n, int h, String p) {
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
    public PaymentMethod getPaymentMethod() {
        return GuestInterface.pm;
    }



    
    // Did not hash or use equals by 'name' because names can be the same,
    // but emails can't.
    @Override
    public boolean equals(Object o) {
    	if (o == this) return true;
    	if (!(o instanceof Guest)) return false;
    	Guest temp = (Guest)o;
    	return email.equals(temp.email);
    }
    @Override
    public int hashCode() {
    	return Objects.hash(email);
    }
}
