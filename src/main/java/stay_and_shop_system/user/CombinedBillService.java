package stay_and_shop_system.user;

import stay_and_shop_system.occupancy.Reservation;
import stay_and_shop_system.occupancy.ReservationService;
import stay_and_shop_system.store.Product;
import stay_and_shop_system.store.StoreService;

import java.util.ArrayList;
import java.util.List;

public class CombinedBillService {
    private final ReservationService reservationService = new ReservationService();
    private final StoreService storeService = new StoreService();

    public List<Reservation> getReservationsForEmail(String email) {
        if (email == null || email.isBlank()) {
            return new ArrayList<>();
        }

        Guest guest = new Guest("Billing Lookup", email);
        return reservationService.findReservationsOfGuest(guest);
    }

    public List<Product> getProductsForEmail(String email) {
        return storeService.getCart(email);
    }

    public double getStayTotal(String email) {
        double total = 0.0;
        for (Reservation reservation : getReservationsForEmail(email)) {
            total += calculateReservationTotal(reservation);
        }
        return roundMoney(total);
    }

    public double getShoppingTotal(String email) {
        return roundMoney(storeService.getShoppingTotal(email));
    }

    public double getCombinedTotal(String email) {
        return roundMoney(getStayTotal(email) + getShoppingTotal(email));
    }

    public double calculateReservationTotal(Reservation reservation) {
        if (reservation == null || reservation.getStartDate() == null || reservation.getEndDate() == null) {
            return 0.0;
        }

        long millisBetween = reservation.getEndDate().getTimeInMillis()
                - reservation.getStartDate().getTimeInMillis();

        long nights = millisBetween / (1000L * 60 * 60 * 24);
        if (nights <= 0) {
            nights = 1;
        }

        return roundMoney(reservation.getRate() * nights);
    }

    private double roundMoney(double amount) {
        return Math.round(amount * 100.0) / 100.0;
    }
}