package se.hkr.ood.domain;

import java.util.List;

public class Product {
  private String name;
  private String category;
  private int enstimatedLifespan;
  private List<Material> materials;

  public Product(String name, String category, int enstimatedLifespan, List<Material> materials) {
    this.name = name;
    this.category = category;
    this.enstimatedLifespan = enstimatedLifespan;
    this.materials = materials;
  }

  public List<Material> getMaterials() {
    return this.materials;
  }
}
