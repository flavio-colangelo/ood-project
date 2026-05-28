package se.hkr.ood.domain;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import se.hkr.ood.exceptions.ApplicationRuntimeException;
import se.hkr.ood.exceptions.MaterialNotFoundException;
import se.hkr.ood.infrastructure.DatabaseManager;

public class MaterialRepository {

    static public void create(Material material) {
        try {
            Map<String, Object> dbMap = new LinkedHashMap<>();
            dbMap.put("name", material.getName());
            dbMap.put("impactValue", material.getImpact());

            String guidance = material.getGuidance() != null ? String.join(",", material.getGuidance()) : "";
            dbMap.put("recyclingGuidance", guidance);

            DatabaseManager.push("materials", dbMap);
        } catch (SQLException e) {
            throw new ApplicationRuntimeException("Failed to save Product: " + e.getMessage());
        }
    }

    static public Material read(String name) {
        Material material = DatabaseManager.fetch("materials", "name", name, rs -> {
            String matName = rs.getString("name");
            int impact = rs.getInt("impactValue");
            List<String> guidanceList = java.util.Arrays.asList(rs.getString("recyclingGuidance").split(","));
            return new Material(matName, impact, guidanceList);
        });

        if (material == null) {
            throw new MaterialNotFoundException("Material '" + name + "' not found."); // idk why this is complaining I
                                                                                       // feel like it would not work
                                                                                       // without this
        }

        return material;
    }

    static public void update(String attribute, String value, Material material) {

    }

    static public List<Material> fetchAll() {
        return null;
    }

    static public Material parse(Object object) {
        return null;
    };

    static public void delete() {

    }
}
