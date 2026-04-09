package stay_and_shop_system.user;

import java.util.*;
import stay_and_shop_system.occupancy.*;

public interface GuestInterface extends User {
    List<Reservation> reservations = new ArrayList<>();
    ReservationService res = new ReservationService();

    void addReservation(Reservation r);
    void removeReservation(Reservation r);
    List<Reservation> findReservations();


}
