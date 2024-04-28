package com.clone.objects;

import java.util.Arrays;

public class ObjectWithArray {
    private String[] stringArray;

    public ObjectWithArray(String[] stringArray) {
        this.stringArray = stringArray;
    }

    public String[] getStringArray() {
        return stringArray;
    }

    public void setStringArray(String[] stringArray) {
        this.stringArray = stringArray;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ObjectWithArray that = (ObjectWithArray) o;
        return Arrays.equals(stringArray, that.stringArray);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(stringArray);
    }
}
