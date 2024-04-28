package com.clone.objects;

import java.util.Objects;

public class NestedObject {
    private int id;
    private SimpleObject simpleObject;

    public NestedObject(int id, SimpleObject simpleObject) {
        this.id = id;
        this.simpleObject = simpleObject;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SimpleObject getSimpleObject() {
        return simpleObject;
    }

    public void setSimpleObject(SimpleObject simpleObject) {
        this.simpleObject = simpleObject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NestedObject that = (NestedObject) o;
        return id == that.id && Objects.equals(simpleObject, that.simpleObject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, simpleObject);
    }
}
