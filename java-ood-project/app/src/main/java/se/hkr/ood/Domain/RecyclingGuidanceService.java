package se.hkr.ood.domain;

import java.util.List;

public class RecyclingGuidanceService {
  public List<String> fetchGuidance(Product p) {
    return p.getMaterials().getFirst().getGuidance();
  }
}
