package se.hkr.ood.domain;

import java.util.ArrayList;
import java.util.List;

import se.hkr.ood.domain.Product;

public class ProductRepository {
    static public void create(Product product) {

    }

    static public Product read(String name) {
        return new Product("product", "category", 1, new ArrayList<>());
    }

    static public void delete() {

    }

    static public void update(String attribute, String value, Product product) {

    }

    static public List<Product> fetchAll() {
        return new ArrayList<Product>();
    }

    static public Product parse(Object object) {
        return new Product("product", "category", 1, new ArrayList<>());
    };
}
