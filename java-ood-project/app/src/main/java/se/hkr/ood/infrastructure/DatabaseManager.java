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

    public static void init() throws SQLException {

        try (Connection conn = DriverManager.getConnection(URL)) {
            if (conn != null) {
                // System.out.println("Conected to database ood.db\n");

                createTable(conn);
                System.out.println("This has run!");
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    private static void createTable(Connection conn) throws SQLException {
        try (java.sql.Statement stmt = conn.createStatement()) {
            stmt.execute("PRAGMA foreign_keys = ON;");

            stmt.execute("CREATE TABLE IF NOT EXISTS materials ("
                    + "name TEXT PRIMARY KEY NOT NULL,"
                    + "impactValue INTEGER NOT NULL,"
                    + "recyclingGuidance TEXT NOT NULL"
                    + ");");

            stmt.execute("CREATE TABLE IF NOT EXISTS products ("
                    + "name TEXT PRIMARY KEY NOT NULL,"
                    + "category TEXT NOT NULL,"
                    + "enstimatedLifespan INTEGER NOT NULL"
                    + ");");

            stmt.execute("CREATE TABLE IF NOT EXISTS product_materials ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "productName TEXT NOT NULL,"
                    + "materialName TEXT NOT NULL,"
                    + "FOREIGN KEY (productName) REFERENCES products(name),"
                    + "FOREIGN KEY (materialName) REFERENCES materials(name)"
                    + ");");

        } catch (SQLException e) {
            throw new SQLException("Failed to create tables: " + e.getMessage(), e);
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
    // Row mapper maps every single row of a resultset to an object (<T>).
    // Individual functionality in the repositories!!!!
    public interface RowMapper<T> {
        T mapRow(java.sql.ResultSet rs) throws SQLException;
    }

    // When a primary key column and value is used the rowmapper defined for
    // whatever object it was written for is called and it creates the right object.
    //
    public static <T> T fetch(String tableName, String pkColumn, String pkValue, RowMapper<T> mapper)
            throws SQLException {
        List<String> columns = getTableColumns(tableName);
        String columnsString = String.join(", ", columns);
        String sql = "SELECT " + columnsString + " FROM " + tableName + " WHERE " + pkColumn + " = ?"; // explicit columns!!!
        try (Connection conn = DriverManager.getConnection(URL);
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setObject(1, pkValue);
            try (java.sql.ResultSet rs = pstmt.executeQuery()) {
                if (rs.next())
                    return mapper.mapRow(rs);
            }
        } catch (SQLException e) {
            System.err.println("Database Query Error: " + e.getMessage());
        }
        return null;
    }

    // params can also be a list I'm pretty sure
    public static <T> List<T> fetchList(String tableName, String filterColumn, Object filterValue, RowMapper<T> mapper) {
        try {
            List<String> columns = getTableColumns(tableName);
            String columnsString = String.join(", ", columns);
            String sql = "SELECT " + columnsString + " FROM " + tableName;
            
            if (filterColumn != null) {
                sql += " WHERE " + filterColumn + " = ?"; // not sure we're gonna need this but just in case!!
            }
            
            try (Connection conn = DriverManager.getConnection(URL);
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                if (filterColumn != null) {
                    pstmt.setObject(1, filterValue);
                }

                List<T> results = new ArrayList<>();
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        results.add(mapper.mapRow(rs));
                    }
                }
                return results;
            }
        } catch (SQLException e) {
            System.err.println("Database Query Error: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
