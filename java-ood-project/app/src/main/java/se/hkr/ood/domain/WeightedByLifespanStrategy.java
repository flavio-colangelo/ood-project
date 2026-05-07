package se.hkr.ood.domain;

public class WeightedByLifespanStrategy implements EnviromentalImpactCalculator {
    public double calculate(Product product) {
        return 1.0;
    }
}
