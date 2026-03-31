package stay_and_shop_system.user;

import java.util.*;
import stay_and_shop_system.occupancy.Reservation;

public interface GuestInterface extends User {
    List<Reservation> reservations = new ArrayList<>();

    void addReservation(Reservation r);
    void removeReservation(Reservation r);

}
