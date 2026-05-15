package se.hkr.ood.domain;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MaterialTest {

    @Test
    void testNoArgsConstructor() {
        Material material = new Material();
        assertNull(material.getGuidance());
    }

    @Test
    void testAllArgsConstructorAndGetGuidance() {
        List<String> expectedGuidance = Arrays.asList("Rinse thoroughly", "Give to Adrian");
        Material material = new Material("PET Plastic", 50, expectedGuidance);

        assertEquals(expectedGuidance, material.getGuidance());
    }

    @Test
    void testSetRecyclingGuidance() {
        Material material = new Material();
        List<String> newGuidance = Arrays.asList("Do something with it", "Yes indeed");
        
        material.setRecyclingGuidance(newGuidance);

        assertEquals(newGuidance, material.getGuidance());
    }

    @Test
    void testGetImpact(){
        int impactValue = 8;
        Material material = new Material("Hand", impactValue, new ArrayList<>());

        assertEquals(impactValue, material.getImpact());
    }
}