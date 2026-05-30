package se.hkr.ood.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import se.hkr.ood.infrastructure.DatabaseManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MaterialRepositoryTest {

    @BeforeAll
    static void init() {
        try {
            // gotta delete the db if it exists from a previous run
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
        Material newMaterial = new Material("Cardboard", 5, new ArrayList<>());

        assertDoesNotThrow(() -> MaterialRepository.create(newMaterial));
    }

    @Test
    void testRead() throws SQLException {
        Material materialToSave = new Material("AnyString", 10, new ArrayList<>());
        MaterialRepository.create(materialToSave);

        assertDoesNotThrow(() -> {
            Material result = MaterialRepository.read("AnyString");
            assertNotNull(result);
            assertEquals("AnyString", result.getName());
            assertEquals(10, result.getImpact());
        });
    }

    @Test
    void testDelete() {
        assertDoesNotThrow(() -> MaterialRepository.delete());
    }

    @Test
    void testUpdate() {
        Material material = new Material("Old", 15, new ArrayList<>());
        MaterialRepository.create(material);

        assertDoesNotThrow(() -> {
            MaterialRepository.update("name", "New", material);
            assertEquals("New", material.getName());
        });

        assertDoesNotThrow(() -> {
            MaterialRepository.update("impactValue", "50", material);
            assertEquals(50, material.getImpact());
        });

        assertDoesNotThrow(() -> {
            List<String> newGuidancePre = Arrays.asList("Do something", "Yes");
            String newGuidance = String.join(",", newGuidancePre);

            MaterialRepository.update("recyclingGuidance", newGuidance, material);
            assertEquals(newGuidancePre, material.getGuidance());
        });
    }

    @Test
    void testFetchAll() {
        Material material = new Material("Everythingg", 2, new ArrayList<>());
        MaterialRepository.create(material);

        List<Material> results = MaterialRepository.fetchAll();

        assertNotNull(results);
        assertFalse(results.isEmpty());
    }

    // @Test // parse now parses result sets, TODO;
    // void testParse() {
    // Object dummyObject = new Object();
    // Material result = MaterialRepository.parse(dummyObject);

    // assertNotNull(result);
    // assertNotNull(result.getGuidance());
    // assertTrue(result.getGuidance().isEmpty());
    // }
}