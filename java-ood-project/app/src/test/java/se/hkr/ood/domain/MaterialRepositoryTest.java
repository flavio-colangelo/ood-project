package se.hkr.ood.domain;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MaterialRepositoryTest {

    @Test
    void testCreate() {
        Material newMaterial = new Material("Cardboard", 5, new ArrayList<>());
        
        assertDoesNotThrow(() -> MaterialRepository.create(newMaterial));
    }

    @Test
    void testRead() throws SQLException {
        Material result = MaterialRepository.read("AnyString");

        assertNotNull(result);
        assertNotNull(result.getGuidance());
        assertTrue(result.getGuidance().isEmpty());
    }

    @Test
    void testDelete() {
        assertDoesNotThrow(() -> MaterialRepository.delete());
    }

    @Test
    void testUpdate() {
        Material materialToUpdate = new Material();
        
        assertDoesNotThrow(() -> MaterialRepository.update("name", "John", materialToUpdate));
    }

    @Test
    void testFetchAll() {
        List<Material> results = MaterialRepository.fetchAll();

        assertNotNull(results);
        assertTrue(results.isEmpty());
    }

    // @Test // parse now parses result sets, TODO;
    // void testParse() {
    //     Object dummyObject = new Object();
    //     Material result = MaterialRepository.parse(dummyObject);

    //     assertNotNull(result);
    //     assertNotNull(result.getGuidance());
    //     assertTrue(result.getGuidance().isEmpty());
    // }
}