package se.hkr.ood.domain;

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

    static public Product read(String name) {
        Product product = DatabaseManager.fetch("products", "name", name, rs -> {
            String pName = rs.getString("name");
            String category = rs.getString("category");
            int lifespan = rs.getInt("enstimatedLifespan");
            
            return new Product(pName, category, lifespan, new ArrayList<>());
        });


        String sql = "SELECT m.* FROM materials m " +
                     "JOIN product_materials pm ON m.name = pm.materialName " +
                     "WHERE pm.productName = ?";
                     
        List<Material> materials = new ArrayList<>(DatabaseManager.fetchList(sql, rs -> {
            String matName = rs.getString("name");
            int impact = rs.getInt("impactValue");
            
            List<String> guidanceList = new ArrayList<>(java.util.Arrays.asList(rs.getString("recyclingGuidance").split(",")));
            
            return new Material(matName, impact, guidanceList);
        }, name));


        if (product == null) {
            throw new ProductNotFoundException("Product '" + name + "' not found.");
        }
        
        product.setMaterials(materials);
        
        return product;
    }

    static public void delete() {

    }

    static public void update(String attribute, String value, Product product) {

    }

    static public List<Product> fetchAll() {
        return null;
    }

    static public Product parse(Object object) { 
        return null;
    }
}