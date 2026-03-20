import java.util.*;

public class StoreService {
	List<Product> cart = new ArrayList<>();
	
	public void addItem(Product p) {
		cart.add(p);
		// Note: We may or may not need to update the UI after this (not in this class tho).
	}
	public void removeItemFromCart(Product p) {
		cart.remove(p);
		// Note: We may or may not need to update the UI after this (not in this class tho).
	}
}
