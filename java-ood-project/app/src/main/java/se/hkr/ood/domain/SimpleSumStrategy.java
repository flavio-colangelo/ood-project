package se.hkr.ood.domain;

import java.util.List;

public class SimpleSumStrategy implements EnviromentalImpactCalculator {
    public double calculate(Product product) {
        List<Material> materials = product.getMaterials();
        double sum = 0;
        for (int i = 0; i < materials.size(); i++) {
            sum += materials.get(i).getImpact();
        }
        return sum;
    }
}
