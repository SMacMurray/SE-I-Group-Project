package stay_and_shop_system.user;

public class GuestConverter {
    public static GuestInterface toGuest(User user) {
        GuestInterface newGuest = new Guest(user.getName(), user.getEmail(), user.getPhoneNumber(), new PaymentMethod());
        switch(user.getId()) {
            case ADMIN:
                Admin admin = (Admin)user;
                GuestAdmin gA;
                gA = new GuestAdmin(admin.getEmail(), admin.getName(), admin.getPhoneNumber(), admin.getPassword(), new PaymentMethod());
                newGuest = gA;
                break;
            case CLERK:
                Clerk clerk = (Clerk)user;
                GuestClerk gC;
                gC = new GuestClerk(clerk.getEmail(), clerk.getName(), clerk.getPhoneNumber(), clerk.getPassword(), new PaymentMethod());
                newGuest = gC;
                break;
            default:
                break;
        }

        return newGuest;
    }
//    public static User toPreviousClass(GuestInterface guest) {
//
//    }
}
