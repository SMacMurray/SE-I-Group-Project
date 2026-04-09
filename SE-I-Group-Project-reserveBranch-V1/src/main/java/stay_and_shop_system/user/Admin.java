package stay_and_shop_system.user;

public class Admin implements AdminInterface {
	private String name = "John Doe";
	private String email = "dummyEmail@gmail.com";
	private String phoneNumber;
	private int hash; // Stores the hash of the password, not the password.
	private UserType id = UserType.ADMIN;

	public Admin(String n, String e) {
		name = n;
		email = e;
	}
	public Admin(String n, int h) {
		name = n;
		hash = h;
		throw new RuntimeException("TODO: Make sure creating an account requires putting an email also");
	}
	public Admin(String e, String n, int h, String p) {
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
	public void setId(UserType id) { this.id = id; }

	public String getName() {
		return name;
	}
	public String getEmail() {
		return email;
	}
	public String getPhoneNumber() { return phoneNumber; }
	public int getPassword() { return hash; }
	public UserType getId() { return id; }

	public void createClerkAccount(Clerk clerk) {

	}

	public void resetUserPassword(User user) {

	}
}
