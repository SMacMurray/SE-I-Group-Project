package stay_and_shop_system.store;

import stay_and_shop_system.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StoreRepository {
    static Connection connection = DatabaseConnection.connect();

    public static void createTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS Store (
                    productId INTEGER PRIMARY KEY,
                    price DOUBLE NOT NULL,
                    description TEXT NOT NULL,
                    name TEXT NOT NULL
                );
                """;
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create table : StoreRepository", e);
        }
    }

    public static void dropTable() {
        String sql = """
                DROP TABLE IF EXISTS Store
                """;
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to drop table : StoreRepository", e);
        }
    }

    public static List<Product> loadProducts() {
        String sql = """
                SELECT * FROM Store;
                """;
        List<Product> products = new ArrayList<>();
        try (ResultSet rSet = connection.createStatement().executeQuery(sql)) {
            while (rSet.next()) {
                products.add(mapResultSetToProduct(rSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public static void addProduct(Product p) {
        String sql = """
                INSERT INTO Store (productId, price, description, name) VALUES (?, ?, ?, ?)
                """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, p.getId());
            ps.setDouble(2, p.getPrice());
            ps.setString(3, p.getDescription());
            ps.setString(4, p.getName());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteProduct(Product p) {
        String sql = """
                DELETE FROM Store WHERE productId = ?
                """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, p.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Product loadProductFromId(int id) {
        String sql = """
                SELECT * FROM Store WHERE productId =\s
               \s""" + id;
        Product product = null;

        try (ResultSet rSet = connection.createStatement().executeQuery(sql)) {
            if (rSet.next()) {
                product = mapResultSetToProduct(rSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return product;
    }

    public static void modifyProduct(int productId, Product p) {
        if (loadProductFromId(productId) == null) {
            throw new IllegalArgumentException("Product ID is not in the repository");
        }
        String sql = """
                UPDATE Store SET price = ?, description = ?, name = ? WHERE productId = ?
                """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDouble(1, p.getPrice());
            ps.setString(2, p.getDescription());
            ps.setString(3, p.getName());
            ps.setInt(4, productId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    private static Product mapResultSetToProduct(ResultSet resultSet) throws SQLException {
        Product product = null;

        int productId = resultSet.getInt("productId");
        double price = resultSet.getDouble("price");
        String description = resultSet.getString("description");
        String name = resultSet.getString("name");

        product = new Product(productId, price, description, name);

        return product;
    }
}
