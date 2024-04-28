package com.clone;


import com.clone.objects.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CloneUtilTest {

    @Test
    /**
     * tests the example from GitHub
     */
    public void testInitial() throws Exception {
        List<String> books = new ArrayList<>();
        books.add("The Pilgrim’s Progress");
        books.add("Robinson Crusoe");
        books.add("The Sun Also Rises");

        Man original = new Man("John", 30, books);
        Man cloned = CloneUtil.clone(original);

        assertEquals(original, cloned);
        assertNotSame(original, cloned);

        original.setAge(36);
        original.setName("Jimmy");
        original.getFavoriteBooks().remove(0);
        original.getFavoriteBooks().set(0, original.getFavoriteBooks().get(0).concat("_updated"));

        System.out.println(original);
        System.out.println(cloned);
    }

    @Test
    /**
     * tests an object which has nested object
     */
    public void testNestedObjects() throws Exception {
        SimpleObject nestedSimpleObject = new SimpleObject(10, "Nested");
        NestedObject original = new NestedObject(1, nestedSimpleObject);
        NestedObject cloned = CloneUtil.clone(original);

        assertEquals(original, cloned);

        assertNotSame(original, cloned);

        cloned.getSimpleObject().setValue("Changed");
        assertNotEquals(original.getSimpleObject().getValue(), cloned.getSimpleObject().getValue());
    }

    @Test
    /**
     * tests an object which has reference to itself
     */
    public void testCircularReferences() throws Exception {
        CircularObject original = new CircularObject();
        CircularObject cloned = CloneUtil.clone(original);

        assertEquals(original, cloned);

        assertNotSame(original, cloned);

        cloned.setValue("Changed");
        assertNotEquals(original.getValue(), cloned.getValue());
    }

    @Test
    /**
     * tests an object which has reference to another object
     * and in his turn the second object has reference to first object
     */
    public void testNestedCircular() throws Exception {
        EnclosedObject original = new EnclosedObject(1);
        EnclosedObject cloned = CloneUtil.clone(original);

        assertEquals(original, cloned);

        assertNotSame(original, cloned);
    }

    @Test
    /**
     * tests an immutable object
     */
    public void testImmutableObjects() throws Exception {
        ImmutableObject original = new ImmutableObject(10, "Immutable");
        ImmutableObject cloned = CloneUtil.clone(original);

        assertEquals(original, cloned);

        assertNotSame(original, cloned);

    }

    @Test
    /**
     * tests an object which has an array inside
     */
    public void testObjectWithArray() throws Exception {
        String[] originalArray = {"a", "b", "c"};
        ObjectWithArray original = new ObjectWithArray(originalArray);
        ObjectWithArray cloned = CloneUtil.clone(original);

        assertEquals(original, cloned);

        assertNotSame(original, cloned);

        assertNotSame(original.getStringArray(), cloned.getStringArray());

        cloned.getStringArray()[0] = "modified";
        assertNotEquals(original.getStringArray()[0], cloned.getStringArray()[0]);
    }

    @Test
    /**
     * tests an object which overrides Object's clone method
     * in this case according to the CloneUtil's logic just the clone()
     * method must be called
     */
    public void testObjectWithCustomClone() throws Exception {
        CustomCloneableObject original = new CustomCloneableObject(1, "Original");
        CustomCloneableObject cloned = CloneUtil.clone(original);

        assertEquals(original, cloned);

        assertNotSame(original, cloned);

        cloned.setName("Modified");
        assertNotEquals(original.getName(), cloned.getName());
    }
}
