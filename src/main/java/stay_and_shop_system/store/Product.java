package stay_and_shop_system.store;

import java.util.*;

public class Product {
	private int id;
	private double price;
	private String description;
	private String name;

    public Product(int id, double price, String description, String name) {
        this.id = id;
        this.price = price;
        this.description = description;
        this.name = name;
    }

	public Product() {}

	public int getId() { return id; }
	public double getPrice() { return price; }
	public String getDescription() { return description; }
	public String getName() { return name; }

	public void setId(int i) { id = i; }
	public void setPrice(double p) {price = p;}
	public void setDescription(String d) { description = d; }
	public void setName(String n) { name = n; }

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		Product product = (Product) o;
		return id == product.id && Double.compare(price, product.price) == 0 && Objects.equals(description, product.description) && Objects.equals(name, product.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, price, description, name);
	}
}
