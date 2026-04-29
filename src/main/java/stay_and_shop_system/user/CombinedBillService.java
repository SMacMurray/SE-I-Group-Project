package stay_and_shop_system.user;

import stay_and_shop_system.occupancy.Reservation;
import stay_and_shop_system.occupancy.database.ReservationRepository;
import stay_and_shop_system.store.Product;
import stay_and_shop_system.store.StoreService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CombinedBillService {
    private final StoreService storeService = new StoreService();

    public List<Reservation> getReservationsForEmail(String email) {
        if (email == null || email.isBlank()) {
            return new ArrayList<>();
        }

        int guestId = Math.abs(Objects.hash(email));
        return ReservationRepository.loadReservationsOfGuestId(guestId);
    }

    public List<Reservation> getReservationsForEmailAndReservationId(String email, int reservationId) {
        List<Reservation> matchingReservations = new ArrayList<>();

        for (Reservation reservation : getReservationsForEmail(email)) {
            if (reservation != null && reservation.getReservationId() == reservationId) {
                matchingReservations.add(reservation);
            }
        }

        return matchingReservations;
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
    public double getStayTotal(List<Reservation> reservations) {
        double total = 0.0;

        for (Reservation reservation : reservations) {
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