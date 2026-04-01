package stay_and_shop_system.store;

import java.util.*;

public class Product {
	private double price;
	private String description;
	private String name;
	
	public double getPrice() { return price; }
	public String getDescription() { return description; }
	public String getName() { return name; }
	
	public void setPrice(double p) {price = p;}
	public void setDescription(String d) { description = d; }
	public void setName(String n) { name = n; }
	
	@Override
	public boolean equals(Object o) {
		if (o == this) { return true; }
		if (!(o instanceof Product)) { return false; }
		Product p = (Product)o;
		return (price == p.price) && (description.equals(p.description)) && (name.equals(p.name));
	}
	@Override
	public int hashCode() {
		return Objects.hash(price, description, name);
	}
}
