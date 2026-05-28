package se.hkr.ood.domain;

import java.util.Map;

public interface DatabaseObject {
    String getTableName();

    Map<String, Object> getColumnValues(); //Map<column name, data>
}
