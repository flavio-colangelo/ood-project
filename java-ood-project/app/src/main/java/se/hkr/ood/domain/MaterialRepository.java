package se.hkr.ood.domain;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import se.hkr.ood.exceptions.ApplicationRuntimeException;
import se.hkr.ood.exceptions.MaterialNotFoundException;
import se.hkr.ood.infrastructure.DatabaseManager;

public class MaterialRepository {

    static public void create(Material material) throws SQLException {
        Map<String, Object> dbMap = new LinkedHashMap<>();
        dbMap.put("name", material.getName());
        dbMap.put("impactValue", material.getImpact());

        String guidance = material.getGuidance() != null ? String.join(",", material.getGuidance()) : "";
        dbMap.put("recyclingGuidance", guidance);

        DatabaseManager.push("materials", dbMap);
    }

    static public Material read(String name) throws SQLException {
        Material material = DatabaseManager.fetch("materials", "name", name, rs -> MaterialRepository.parse(rs));

        if (material == null) {
            System.out.println("This is null [test btw]");
            throw new MaterialNotFoundException("Material '" + name + "' not found.");
        }

        return material;
    }

    static public void update(String attribute, String value, Material material) {
        try {
            DatabaseManager.update("materials", "name", material.getName(), attribute, value);

            String fetchName = attribute.equals("name") ? value : material.getName(); // in case the pk got updated

            Material refreshedMaterial = read(fetchName);

            material.setName(refreshedMaterial.getName());
            material.setImpactValue(refreshedMaterial.getImpact());
            material.setRecyclingGuidance(refreshedMaterial.getGuidance());

        } catch (SQLException e) {
             throw new ApplicationRuntimeException("Failed to update Material: " + e.getMessage());
        }
    }

    static public List<Material> fetchAll() {
        return DatabaseManager.fetchList("materials", rs -> MaterialRepository.parse(rs));
    }

    static public Material parse(ResultSet rs) throws SQLException {
        String matName = rs.getString("name");
        int impact = rs.getInt("impactValue");
        List<String> guidanceList = java.util.Arrays.asList(rs.getString("recyclingGuidance").split(","));
        return new Material(matName, impact, guidanceList);
    }

    static public void delete() {

    }
}
