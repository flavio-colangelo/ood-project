package se.hkr.ood.domain;

import java.util.List;

interface Repository {
    void create(Object object);
    Object read(String name);
    void update(String attribute, String value);
    void delete();
    List<Object> fetchAll();
    Object parse(Object object);
}
