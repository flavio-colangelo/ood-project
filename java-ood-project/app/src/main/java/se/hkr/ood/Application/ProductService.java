package se.hkr.ood.application;

import se.hkr.ood.domain.Product;
import se.hkr.ood.domain.ProductRepository;
import se.hkr.ood.domain.RecyclingGuidanceService;

import java.util.ArrayList;
import java.util.List;

public class ProductService {

    public static Product createProduct(String name, String category, int lifespan) {
        Product product = new Product(name, category, lifespan, new ArrayList<>());
        ProductRepository.create(product);
        return product;
    }

    public static Product fetchProduct(String name) {
        return ProductRepository.read(name);
    }

    public static double environmentalImpact(Product product, EnvironmentalImpactCalculator strategy) {
        return strategy.calculate(product);
    }

    public static List<String> recyclingGuidance(Product product) {
        return RecyclingGuidanceService.fetchGuidance(product);
    }
}