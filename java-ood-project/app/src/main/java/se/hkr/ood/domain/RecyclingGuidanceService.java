package se.hkr.ood.domain;

import java.util.ArrayList;
import java.util.List;

public class RecyclingGuidanceService {
  static public List<List<String>> fetchGuidance(Product product) {
    List<Material> materials = product.getMaterials();
    List<List<String>> guidance = new ArrayList<>();
    for (int i = 0; i < materials.size(); i++) {
      guidance.add(materials.get(i).getGuidance());
    }
    return guidance;
  }
}
