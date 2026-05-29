package se.hkr.ood.application;

import se.hkr.ood.domain.Material;
import se.hkr.ood.domain.MaterialRepository;

import java.sql.SQLException;
import java.util.List;

public class MaterialService {

    public static Material generateMaterial() {
        Material material = new Material();
        return material;
    }

    public static Material setName(Material material, String name) {
        material.setName(name);
        return material;
    }

    public static Material setImpactValue(Material material, int impactValue) {
        material.setImpactValue(impactValue);
        return material;
    }

    public static Material setRecyclingGuidance(Material material, List<String> guidance) {
        material.setRecyclingGuidance(guidance);
        return material;
    }

    public static Material createMaterial(String name, int impactValue, List<String> guidance) throws SQLException {
        Material material = new Material(name, impactValue, guidance);
        MaterialRepository.create(material);
        return material;
    }

    public static Material createMaterial(Material material) throws SQLException {
        MaterialRepository.create(material);
        return material;
    }

    public static Material fetchMaterial(String name) throws SQLException {
        return MaterialRepository.read(name);
    }

    public static List<Material> fetchAll() {
        return MaterialRepository.fetchAll();
    }

    public static void update(String attribute, String value, Material material) {
        MaterialRepository.update(attribute, value, material);
    }
}