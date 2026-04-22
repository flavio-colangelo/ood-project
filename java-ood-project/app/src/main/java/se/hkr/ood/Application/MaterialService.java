package se.hkr.ood.application;

import se.hkr.ood.domain.Material;
import se.hkr.ood.domain.MaterialRepository;

import java.util.List;

public class MaterialService {

    public static void createMaterial(String name, int impactValue, List<String> guidance) {
        Material material = new Material(name, impactValue, guidance);
        MaterialRepository.create(material);
    }

    public static Material fetchMaterial(String name) {
        return MaterialRepository.read(name);
    }
}