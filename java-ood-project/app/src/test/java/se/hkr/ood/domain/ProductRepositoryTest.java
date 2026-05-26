package se.hkr.ood.domain;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductRepositoryTest {

    @Test
    void testCreate() {
        Product newProduct = new Product("Table", "Furniture", 15, new ArrayList<>());

        assertDoesNotThrow(() -> ProductRepository.create(newProduct), 
                "Create method should execute without errors");
    }

    @Test
    void testRead() {
        Product result = ProductRepository.read("AnyName");

        assertNotNull(result, "Read should return a Product object");
        assertNotNull(result.getMaterials(), "The hardcoded product should have an initialized materials list");
        assertTrue(result.getMaterials().isEmpty(), "The hardcoded materials list should be empty");
        
    }

    @Test
    void testDelete() {
        assertDoesNotThrow(() -> ProductRepository.delete(), 
                "Delete method should execute without errors");
    }

    @Test
    void testUpdate() {
        Product productToUpdate = new Product();

        assertDoesNotThrow(() -> ProductRepository.update("category", "Electronics", productToUpdate), 
                "Update method should execute without errors");
    }

    @Test
    void testFetchAll() {
        List<Product> results = ProductRepository.fetchAll();

        assertNotNull(results, "FetchAll should return a list, not null");
        assertTrue(results.isEmpty(), "Currently, fetchAll returns an empty list");
    }

    @Test
    void testParse() {
        Object dummyObject = new Object();

        Product result = ProductRepository.parse(dummyObject);

        assertNotNull(result, "Parse should return a valid Product object");
        assertNotNull(result.getMaterials(), "The parsed product should have an initialized materials list");
    }
}