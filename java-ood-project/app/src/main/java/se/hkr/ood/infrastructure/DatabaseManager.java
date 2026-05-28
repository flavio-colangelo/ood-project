package se.hkr.ood.infrastructure;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import se.hkr.ood.domain.Material;
import se.hkr.ood.domain.Product;

public class DatabaseManager {
    private final static String FOREIGN_STMT = "PRAGMA foreign_keys = ON;";
    private final static String URL = "jdbc:sqlite:ood.db";

    public static void init(String[] args) throws SQLException {

        try (Connection conn = DriverManager.getConnection(URL)) {
            if (conn != null) {
                // System.out.println("Conected to database ood.db\n");

                createTable(conn);
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    private static void createTable(Connection conn) throws SQLException {
        String sql = ""
                + FOREIGN_STMT
                + ""
                + "CREATE TABLE IF NOT EXISTS materials ("
                + "name TEXT PRIMARY KEY NOT NULL,"
                + "impactValue INTEGER NOT NULL,"
                + "recyclingGuidance TEXT NOT NULL" // text separated by a comma gets parsed as list of strings cause
                                                    // it's easier to deal with eventually!!!
                + ");"
                + ""
                + ""
                + "CREATE TABLE IF NOT EXISTS products ("
                + "name TEXT PRIMARY KEY NOT NULL,"
                + "category TEXT NOT NULL,"
                + "enstimatedLifespan INTEGER NOT NULL,"
                + ");"
                + ""
                + ""
                + "CREATE TABLE IF NOT EXISTS product_materials ("
                + "id INTEGER AUTOINCREMENT PRIMARY KEY NOT NULL,"
                + "productName TEXT NOT NULL,"
                + "materialName TEXT NOT NULL,"
                + "FOREIGN KEY (productName) REFERENCES products(name),"
                + "FOREIGN KEY (materialName) REFERENCES materials(name)"
                + ");";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    private static String makeStatement(String tableName, List<String> columns) {
        StringBuilder cols = new StringBuilder();
        StringBuilder placeholders = new StringBuilder();

        for (int i = 0; i < columns.size(); i++) {
            cols.append(columns.get(i));
            placeholders.append("?");
            if (i < columns.size() - 1) {
                cols.append(", ");
                placeholders.append(", ");
            }
        }
        return "INSERT OR REPLACE INTO " + tableName + " (" + cols + ") VALUES (" + placeholders + ")";
    }

    public static List<String> getTableColumns(String tableName) throws SQLException {
        List<String> columns = new ArrayList<>();
        String query = "SELECT * FROM " + tableName + " LIMIT 0";

        try (Connection conn = DriverManager.getConnection(URL);
                PreparedStatement pstmt = conn.prepareStatement(query);
                ResultSet rs = pstmt.executeQuery()) {

            ResultSetMetaData metaData = rs.getMetaData();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                columns.add(metaData.getColumnName(i));
            }
        }
        return columns;
    }

    public static void push(String tableName, Map<String, Object> columnValues) throws SQLException {
        List<String> columns = new ArrayList<>(columnValues.keySet());
        String sql = makeStatement(tableName, columns);

        try (Connection conn = DriverManager.getConnection(URL);
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            int paramIndex = 1;
            for (String col : columns) {
                pstmt.setObject(paramIndex++, columnValues.get(col));
            }

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    // https://java-design-patterns.com/patterns/callback/#programmatic-example-of-callback-pattern-in-java
    public interface RowMapper<T> {
        T mapRow(java.sql.ResultSet rs) throws SQLException;
    }

    public static <T> T fetch(String tableName, String pkColumn, String pkValue, RowMapper<T> mapper) {
        String sql = "SELECT * FROM " + tableName + " WHERE " + pkColumn + " = ?";
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setObject(1, pkValue);
            try (java.sql.ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return mapper.mapRow(rs);
            }
        } catch (SQLException e) {
            System.err.println("Database Query Error: " + e.getMessage());
        }
        return null;
    }

    public static <T> List<T> fetchList(String sql, RowMapper<T> mapper, Object... params) {
        List<T> results = new ArrayList<>();
        
        try (Connection conn = DriverManager.getConnection(URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    results.add(mapper.mapRow(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Database Query Error: " + e.getMessage());
        }
        
        return results;
    }
}
