package com.clone.objects;

import java.util.Objects;

public class CircularObject {
    private String value;
    private CircularObject next;

    public CircularObject() {
        this.value = "Circular";
        this.next = this;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public CircularObject getNext() {
        return next;
    }

    public void setNext(CircularObject next) {
        this.next = next;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CircularObject that = (CircularObject) o;
        return Objects.equals(value, that.value) && Objects.equals(next.value, that.next.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, next);
    }
}
