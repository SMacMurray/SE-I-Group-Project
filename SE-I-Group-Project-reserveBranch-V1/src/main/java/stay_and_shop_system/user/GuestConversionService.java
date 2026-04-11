package stay_and_shop_system.user;

public class GuestConversionService {
    public static GuestInterface toGuest(User user, PaymentMethod pm) {
        GuestInterface newGuest = new Guest(user.getName(), user.getEmail(), user.getPhoneNumber(), pm);
        switch(user.getTypeId()) {
            case ADMIN:
                Admin admin = (Admin)user;
                GuestAdmin gA;
                gA = new GuestAdmin( admin.getName(), admin.getEmail(), admin.getPassword(), admin.getPhoneNumber(),  pm);
                newGuest = gA;
                break;
            case CLERK:
                Clerk clerk = (Clerk)user;
                GuestClerk gC;
                gC = new GuestClerk( clerk.getName(), clerk.getEmail(), clerk.getPassword(), clerk.getPhoneNumber(),  pm);
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
