package se.hkr.ood.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import se.hkr.ood.infrastructure.DatabaseManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductRepositoryTest {

    @BeforeAll
    static void init() {
        try {
            java.io.File dbFile = new java.io.File("ood.db");
            if (dbFile.exists()) {
                dbFile.delete();
            }
            DatabaseManager.init();
        } catch (SQLException e) {
            fail("Failed to initialize the database for tests: " + e.getMessage());
        }
    }

    @Test
    void testCreate() {
        Product newProduct = new Product("Table", "Furniture", 15, new ArrayList<>());

        assertDoesNotThrow(() -> ProductRepository.create(newProduct),
                "Create method should execute without errors");
    }

    @Test
    void testRead() throws SQLException {

        Product productToSave = new Product("AnyProduct", "Somethingg", 5, new ArrayList<>());
        ProductRepository.create(productToSave);

        assertDoesNotThrow(() -> {
            Product result = ProductRepository.read("AnyProduct");

            assertNotNull(result);
            assertEquals("AnyProduct", result.getName());
            assertEquals("Somethingg", result.getCategory());
            assertEquals(5, result.getEstimatedLifespan());

            assertNotNull(result.getMaterials());
            assertTrue(result.getMaterials().isEmpty());
        });
    }

@Test
    void testUpdate() {

        Product product = new Product("OldName", "OldCategory", 5, new ArrayList<>());
        ProductRepository.create(product);

        assertDoesNotThrow(() -> {
            ProductRepository.update("name", "NewName", product);
            assertEquals("NewName", product.getName());
        });

        assertDoesNotThrow(() -> {
            ProductRepository.update("category", "NewCategory", product);
            assertEquals("NewCategory", product.getCategory());
        });

        assertDoesNotThrow(() -> {
            ProductRepository.update("enstimatedLifespan", "50", product);
            assertEquals(50, product.getEstimatedLifespan());
        });
    }

    @Test
    void testFetchAll() throws SQLException {
        Product product = new Product("CoolProductzz", "Category", 5, new ArrayList<>());
        ProductRepository.create(product);

        List<Product> results = ProductRepository.fetchAll();

        assertNotNull(results);
        assertFalse(results.isEmpty());
    }

    // @Test // parse now parses resultSet, TODO;
    // void testParse() {
    // Object dummyObject = new Object();

    // Product result = ProductRepository.parse(dummyObject);

    // assertNotNull(result, "Parse should return a valid Product object");
    // assertNotNull(result.getMaterials(), "The parsed product should have an
    // initialized materials list");
    // }
}