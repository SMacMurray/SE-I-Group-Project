package stay_and_shop_system.store;

import java.util.*;

public class StoreService {
	private static final Map<String, List<Product>> cartsByEmail = new HashMap<>();

	public void addItem(String guestEmail, Product p) {
		if (guestEmail == null || guestEmail.isBlank() || p == null) {
			return;
		}
		cartsByEmail.computeIfAbsent(guestEmail, k -> new ArrayList<>()).add(p);
	}

	public void removeItemFromCart(String guestEmail, Product p) {
		if (guestEmail == null || guestEmail.isBlank() || p == null) {
			return;
		}
		List<Product> cart = cartsByEmail.get(guestEmail);
		if (cart != null) {
			cart.remove(p);
		}
	}

	public List<Product> getCart(String guestEmail) {
		if (guestEmail == null || guestEmail.isBlank()) {
			return new ArrayList<>();
		}
		return new ArrayList<>(cartsByEmail.getOrDefault(guestEmail, new ArrayList<>()));
	}

	public double getShoppingTotal(String guestEmail) {
		double total = 0.0;
		for (Product p : getCart(guestEmail)) {
			total += p.getPrice();
		}
		return Math.round(total * 100.0) / 100.0;
	}

	public void clearCart(String guestEmail) {
		if (guestEmail == null || guestEmail.isBlank()) {
			return;
		}
		cartsByEmail.remove(guestEmail);
	}
}