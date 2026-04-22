package se.hkr.ood.domain;

import java.util.ArrayList;
import java.util.List;

import se.hkr.ood.domain.Product;

public class ProductRepository implements Repository {
    public void create(Object object) {

    }
    
    public void create(Product product) {

    }

    public Product read(String name) {
        return new Product("product", "category", 1, new ArrayList<>());
    }

    public void delete() {

    }

    public void update(String attribute, String value) {

    }

    public List<Object> fetchAll() {
        return new ArrayList<Object>();
    }

    public Product parse(Object object) {
        return new Product("product", "category", 1, new ArrayList<>());
    };
}
