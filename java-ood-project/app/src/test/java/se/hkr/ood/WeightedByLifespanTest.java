package se.hkr.ood;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import se.hkr.ood.domain.EnviromentalImpactCalculator;
import se.hkr.ood.domain.Material;
import se.hkr.ood.domain.Product;
import se.hkr.ood.domain.WeightedByLifespanStrategy;

public class WeightedByLifespanTest {
    @Test
    void testCalculate() {
        EnviromentalImpactCalculator strategy = new WeightedByLifespanStrategy();
        List<String> expectedGuidance1 = Arrays.asList("Rinse thoroughly", "Give to Adrian");
        Material material1 = new Material("PET Plastic", 50, expectedGuidance1);
        List<String> expectedGuidance2 = Arrays.asList("Eat thoroughly", "Give to Nelson");
        Material material2 = new Material("Teflon", 90, expectedGuidance2);

        List<Material> materials = Arrays.asList(material1, material2);

        Product newProduct = new Product("John", "Human", 27, materials);

        assertNotNull(strategy.calculate(newProduct), "The Simple Sum Strategy should not be null");

        double expectedImpact = 0;

        for (Material material : materials) {
            expectedImpact += material.getImpact();
        }
        
        assertEquals(strategy.calculate(newProduct), expectedImpact / materials.size(), "The Simple Sum Strategy should return the correct value");
    }
}
