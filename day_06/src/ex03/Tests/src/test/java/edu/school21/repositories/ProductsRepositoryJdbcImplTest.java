package edu.school21.repositories;

import edu.school21.models.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductsRepositoryJdbcImplTest {

    final List<Product> EXPECTED_FIND_ALL_PRODUCTS = new ArrayList<>(
            Arrays.asList(
                new Product(1, "Product 1", 10.99),
                new Product(2, "Product 2", 19.99),
                new Product(3, "Product 3", 7.50),
                new Product(4, "Product 4", 25.00),
                new Product(5, "Product 5", 5.99)
            )
    );
    final List<Product> EXPECTED_FIND_ALL_DELETED_PRODUCTS = new ArrayList<>(
            Arrays.asList(
                new Product(1, "Product 1", 10.99),
                new Product(3, "Product 3", 7.50),
                new Product(4, "Product 4", 25.00),
                new Product(5, "Product 5", 5.99)
            )
    );
    final Product EXPECTED_FIND_BY_ID_PRODUCT = new Product(2, "Product 2", 19.99);
    final Product EXPECTED_UPDATED_PRODUCT = new Product(2, "New product", 100.00);
    final Product EXPECTED_SAVED_PRODUCT = new Product(10, "New_new product", 100.00);

    private EmbeddedDatabase dataSource;
    private ProductsRepository productsRepository;

    @BeforeEach
    public void init() {
        dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("schema.sql")
                .addScript("data.sql")
                .build();
        productsRepository = new ProductsRepositoryJdbcImpl(dataSource);
    }

    @Test
    void productFindAll() {
        Assertions.assertEquals(EXPECTED_FIND_ALL_PRODUCTS, productsRepository.findAll());
    }

    @Test
    void productFindById() {
        Assertions.assertEquals(EXPECTED_FIND_BY_ID_PRODUCT, productsRepository.findById(2L).get());
    }

    @Test
    void productUpdate() {
        productsRepository.update(EXPECTED_UPDATED_PRODUCT);
        Assertions.assertEquals(EXPECTED_UPDATED_PRODUCT, productsRepository.findById(2L).get());
    }

    @Test
    void productSave() {
        productsRepository.save(EXPECTED_SAVED_PRODUCT);
        Assertions.assertEquals(EXPECTED_SAVED_PRODUCT, productsRepository.findById(10L).get());
    }

    @Test
    void productDelete() {
        productsRepository.delete(2L);
        Assertions.assertEquals(EXPECTED_FIND_ALL_DELETED_PRODUCTS, productsRepository.findAll());
    }

    @AfterEach
    void clear() {
        dataSource.shutdown();
    }
}
