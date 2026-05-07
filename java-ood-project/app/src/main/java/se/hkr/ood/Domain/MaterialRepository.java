package se.hkr.ood.domain;

import java.util.ArrayList;
import java.util.List;

import se.hkr.ood.domain.Material;

public class MaterialRepository {
    static public void create(Material material) {

    }

    static public Material read(String name) {
        return new Material("name", 1, new ArrayList<>());
    }

    static public void delete() {

    }

    static public void update(String attribute, String value, Material material) {

    }

    static public List<Material> fetchAll() {
        return new ArrayList<Material>();
    }

    static public Material parse(Object object) {
        return new Material("name", 1, new ArrayList<>());
    };
}
