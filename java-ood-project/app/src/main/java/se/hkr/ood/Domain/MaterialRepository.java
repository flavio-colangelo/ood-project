package se.hkr.ood.domain;

import java.util.ArrayList;
import java.util.List;

import se.hkr.ood.domain.Material;

public class MaterialRepository implements Repository {
    public void create() {
    }

    public Material read(String name) {
        return new Material();
    }

    public void delete() {

    }

    public void update(String attribute, String value) {

    }

    public List<Object> fetchAll() {
        return new ArrayList<Object>();
    }

    public Material parse(Object object) {
        return new Material();
    };
}
