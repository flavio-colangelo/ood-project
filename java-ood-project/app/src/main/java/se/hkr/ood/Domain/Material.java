package se.hkr.ood.domain;

import java.util.List;

public class Material {
  private String name;
  private int impactValue;
  private List<String> recyclingGuidance;

  public Material() {
  }

  public Material(String name, int impactValue, List<String> recyclingGuidance) {
    this.name = name;
    this.impactValue = impactValue;
    this.recyclingGuidance = recyclingGuidance;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setImpactValue(int impactValue) {
    this.impactValue = impactValue;
  }

  public void setRecyclingGuidance(List<String> recyclingGuidance) {
    this.recyclingGuidance = recyclingGuidance;
  }

  public List<String> getGuidance() {
    return this.recyclingGuidance;
  }
}
