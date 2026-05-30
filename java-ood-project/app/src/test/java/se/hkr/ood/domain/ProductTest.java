package se.hkr.ood.domain;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void testNoArgsConstructor() {
        Product product = new Product();

        assertNull(product.getMaterials(), "Materials should be null when using the empty constructor");
    }

    @Test
    void testAllArgsConstructor() {
        List<Material> mockMaterials = new ArrayList<>();
        mockMaterials.add(new Material("Plastic", 10, Arrays.asList("Put in plastic bin", "Do not eat!"))); 

        Product product = new Product("Desk", "Furniture", 10, mockMaterials);

        assertEquals(mockMaterials, product.getMaterials(), "The materials list should match the one passed in the constructor");
    }

    @Test
    void testSetAndGetMaterials() {
        Product product = new Product();
        List<Material> newMaterials = new ArrayList<>();

        product.setMaterials(newMaterials);

        assertEquals(newMaterials, product.getMaterials(), "getMaterials should return the exact list provided by setMaterials");
    }
}