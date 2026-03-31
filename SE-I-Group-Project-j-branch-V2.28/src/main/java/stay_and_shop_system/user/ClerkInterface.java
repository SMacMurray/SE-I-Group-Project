package stay_and_shop_system.user;

import stay_and_shop_system.occupancy.Room;

public interface ClerkInterface extends User {
    boolean addRoom(Room r);
    void modifyInformation(Clerk clerk);
}
