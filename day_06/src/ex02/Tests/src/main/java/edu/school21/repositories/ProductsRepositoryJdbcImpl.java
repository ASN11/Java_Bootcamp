package edu.school21.repositories;

import edu.school21.models.Product;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

public class ProductsRepositoryJdbcImpl implements ProductsRepository {

    private final DataSource dataSource;
    public ProductsRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();

        try (Connection conn = dataSource.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Product");

            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                products.add(AddProduct(resultSet));
            }

        } catch (SQLException e) {
            System.out.println("Unable to connect to database.");
        }
        return products;
    }

    @Override
    public Optional<Product> findById(Long id) {
        Product product = new Product();

        try (Connection conn = dataSource.getConnection()) {
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Product WHERE identifier = " + id);

            if (resultSet.next()) {
                product = AddProduct(resultSet);
            } else {
                throw new SQLException("Продукта с таким ID нет в системе.");
            }
        } catch (SQLException e) {
            System.out.println("Unable to connect to database.");
        }

        return Optional.of(product);
    }

    @Override
    public void update(Product product) {
        try (Connection conn = dataSource.getConnection()) {

            PreparedStatement stmt = conn.prepareStatement("UPDATE Product SET " +
                    "name = ?, " +
                    "price = ? " +
                    "WHERE identifier = " + product.getIdentifier());

            stmt.setString(1, product.getName());
            stmt.setDouble(2, product.getPrice());
            stmt.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            System.out.println("Unable to connect to database.");
        }
    }

    @Override
    public void save(Product product) {
        try (Connection conn = dataSource.getConnection()) {

            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Product (identifier, name, price) " +
                            "VALUES (?, ?, ?)");

            stmt.setInt(1, product.getIdentifier());
            stmt.setString(2, product.getName());
            stmt.setDouble(3, product.getPrice());
            stmt.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            System.out.println("Unable to connect to database.");
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection conn = dataSource.getConnection()) {
            Statement statement = conn.createStatement();
            statement.executeUpdate("DELETE FROM Product WHERE identifier = " + id);

        } catch (SQLException e) {
            System.out.println("Unable to connect to database.");
        }
    }

    private Product AddProduct(ResultSet resultSet) throws SQLException {
        return new Product(
                resultSet.getInt("identifier"),
                resultSet.getString("name"),
                resultSet.getDouble("price")
        );
    }
}
