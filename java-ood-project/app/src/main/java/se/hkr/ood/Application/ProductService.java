package se.hkr.ood.application;

import se.hkr.ood.domain.Material;
import se.hkr.ood.domain.Product;
import se.hkr.ood.domain.ProductRepository;
import se.hkr.ood.domain.RecyclingGuidanceService;
import se.hkr.ood.domain.EnviromentalImpactCalculator;

import java.util.ArrayList;
import java.util.List;

public class ProductService {

    public static Product generateProduct() {
        Product product = new Product();
        return product;
    }

    public static Product setName(Product product, String name) {
        product.setName(name);
        return product;
    }

    public static Product setCategory(Product product, String category) {
        product.setCategory(category);
        return product;
    }

    public static Product setlifespan(Product product, int lifespan) {
        product.setLifespan(lifespan);
        return product;
    }

    public static Product setMaterials(Product product, List<Material> materials) {
        product.setMaterials(materials);
        return product;
    }

    public static Product createProduct(String name, String category, int lifespan, List<Material> materials) {
        Product product = new Product(name, category, lifespan, new ArrayList<>());
        ProductRepository.create(product);
        return product;
    }

    public static Product createProduct(Product product) {
        ProductRepository.create(product);
        return product;
    }

    public static Product fetchProduct(String name) {
        return ProductRepository.read(name);
    }

    public static double enviromentalImpact(Product product, EnviromentalImpactCalculator strategy) {
        return strategy.calculate(product);
    }

    public static List<String> recyclingGuidance(Product product) {
        return RecyclingGuidanceService.fetchGuidance(product);
    }
}