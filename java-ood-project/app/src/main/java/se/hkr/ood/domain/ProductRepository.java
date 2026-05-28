package se.hkr.ood.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import se.hkr.ood.exceptions.ApplicationRuntimeException;
import se.hkr.ood.exceptions.ProductNotFoundException;
import se.hkr.ood.infrastructure.DatabaseManager;

public class ProductRepository {

    static public void create(Product product) {
        Map<String, Object> productMap = new LinkedHashMap<>();
        productMap.put("name", product.getName());
        productMap.put("category", product.getCategory());
        productMap.put("enstimatedLifespan", product.getEstimatedLifespan());

        try {
            DatabaseManager.push("products", productMap);
            if (product.getMaterials() != null) {
                for (Material material : product.getMaterials()) {
                    Map<String, Object> junctionMap = new LinkedHashMap<>();
                    junctionMap.put("productName", product.getName());
                    junctionMap.put("materialName", material.getName());

                    DatabaseManager.push("product_materials", junctionMap);
                }
            }
        } catch (SQLException e) {
            throw new ApplicationRuntimeException("Failed to save Product: " + e.getMessage());
        }
    }

    static public Product read(String name) throws SQLException {
        Product product = DatabaseManager.fetch("products", "name", name, rs -> ProductRepository.parse(rs)); // it complains if I add curly braces
        
        if (product == null) {
            throw new ProductNotFoundException("Product '" + name + "' not found.");
        }

        List<String> materialNames = DatabaseManager.fetchList("product_materials", "productName", name, rs -> {
            return rs.getString("materialName");
        });

        List<Material> materials = new ArrayList<>();
        for (String matName : materialNames) {
            Material mat = DatabaseManager.fetch("materials", "name", matName, rs -> MaterialRepository.parse(rs));

            if (mat != null) {
                materials.add(mat);
            }
        }

        product.setMaterials(materials);
        return product;
    }

    static public List<Product> fetchAll() throws SQLException {
        List<Product> products = DatabaseManager.fetchList("products", rs -> ProductRepository.parse(rs));

        for (Product product : products) {
            List<String> materialNames = DatabaseManager.fetchList("product_materials", "productName", product.getName(), rs -> rs.getString("materialName"));

            List<Material> materials = new ArrayList<>();
            for (String matName : materialNames) {
                Material mat = DatabaseManager.fetch("materials", "name", matName, rs -> MaterialRepository.parse(rs));
                if (mat != null) {
                    materials.add(mat);
                }
            }

            product.setMaterials(materials);
        }

        return products;
    }

    static public void delete() {

    }

    static public void update(String attribute, String value, Product product) {
        try {
            DatabaseManager.update("products", "name", product.getName(), attribute, value);

            String fetchName = attribute.equals("name") ? value : product.getName(); // in case the pk got updated

            Product refreshedProduct = read(fetchName);

            product.setName(refreshedProduct.getName());
            product.setCategory(refreshedProduct.getCategory());
            product.setLifespan(refreshedProduct.getEstimatedLifespan());
            product.setMaterials(refreshedProduct.getMaterials());

        } catch (SQLException e) {
            throw new ApplicationRuntimeException("Failed to update Product: " + e.getMessage());
        }
    }

    static public Product parse(ResultSet rs) throws SQLException {
        String pName = rs.getString("name");
        String category = rs.getString("category");
        int lifespan = rs.getInt("enstimatedLifespan");
        return new Product(pName, category, lifespan, new ArrayList<>());
    }
}