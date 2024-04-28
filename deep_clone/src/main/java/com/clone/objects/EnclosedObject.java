package com.clone.objects;

import java.util.Objects;

public class EnclosedObject {
    private int enclosedId;
    private Nested nestedObject;

    public EnclosedObject(int enclosedId) {
        this.enclosedId = enclosedId;
        this.nestedObject = new Nested(2, this);
    }

    public int getEnclosedId() {
        return enclosedId;
    }

    public void setEnclosedId(int enclosedId) {
        this.enclosedId = enclosedId;
    }

    public Nested getNestedObject() {
        return nestedObject;
    }

    public void setNestedObject(Nested nestedObject) {
        this.nestedObject = nestedObject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EnclosedObject that = (EnclosedObject) o;
        return enclosedId == that.enclosedId && Objects.equals(nestedObject, that.nestedObject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(enclosedId, nestedObject);
    }
}

class Nested {
    private int nestedId;
    private EnclosedObject enclosedObject;

    public Nested(int nestedId, EnclosedObject enclosedObject) {
        this.nestedId = nestedId;
        this.enclosedObject = enclosedObject;
    }

    public int getNestedId() {
        return nestedId;
    }

    public void setNestedId(int nestedId) {
        this.nestedId = nestedId;
    }

    public EnclosedObject getEnclosedObject() {
        return enclosedObject;
    }

    public void setEnclosedObject(EnclosedObject enclosedObject) {
        this.enclosedObject = enclosedObject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Nested that = (Nested) o;
        return nestedId == that.nestedId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nestedId);
    }
}