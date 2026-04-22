package se.hkr.ood.domain;

import java.util.List;

public class Material extends MaterialRepository {
  private String name;
  private int impactValue;
  private List<String> recyclingGuidance;

  Material(String name, int impactValue, List<String> recyclingGuidance) {
    this.name = name;
    this.impactValue = impactValue;
    this.recyclingGuidance = recyclingGuidance;
  }

  public List<String> getGuidance() {
    return this.recyclingGuidance;
  }
}
