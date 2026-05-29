package se.hkr.ood.domain;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RecyclingGuidanceServiceTest {
  @Test
  void fetchGuidanceWithSingleMaterialProduct() {
    List<String> guidance = new ArrayList<>();
    guidance.add("This is the recycling guidance");
    Material material = new Material("materialName", 1, guidance);
    List<Material> materials = new ArrayList<>();
    materials.add(material);
    Product product = new Product("productName", "productCategory", 1, materials);
    List<List<String>> actualGuidance = new ArrayList<>();
    actualGuidance.add(guidance);

    assertNotNull(RecyclingGuidanceService.fetchGuidance(product), "Guidance should not be Null");
    assertEquals(actualGuidance, RecyclingGuidanceService.fetchGuidance(product),
        "Guidance should be the same as the one in the sent product material");
  }

  @Test
  void fetchGuidanceWithEmptyProduct() {
    Product product = new Product();

    assertThrows(NullPointerException.class, () -> {
      RecyclingGuidanceService.fetchGuidance(product);
    });
  }

  @Test
  void fetchGuidanceWithMultipleMaterials() {
    List<String> guidance1 = new ArrayList<>();
    guidance1.add("This is the recycling guidance for material 1");
    Material material1 = new Material("material1Name", 1, guidance1);
    List<String> guidance2 = new ArrayList<>();
    guidance2.add("This is the recycling guidance for material 2");
    Material material2 = new Material("material2Name", 1, guidance2);
    List<Material> materials = new ArrayList<>();
    materials.add(material1);
    materials.add(material2);
    Product product = new Product("productName", "productCategory", 1, materials);
    List<List<String>> guidanceS = new ArrayList<>();
    guidanceS.add(guidance1);
    guidanceS.add(guidance2);
    assertNotNull(RecyclingGuidanceService.fetchGuidance(product), "Guidance should not be Null");
    assertEquals(guidanceS, RecyclingGuidanceService.fetchGuidance(product),
        "Guidance should be the same as the one in the sent product materials");
  }
}
