package com.clone;

import sun.misc.Unsafe;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.IdentityHashMap;
import java.util.Map;

/**
 * This class provides a full deep cloning functionality
 * of any object. The code is written to work in java 17.
 * It uses reflection Api and to make it work in java 17 or
 * higher don't forget to add in jvm options the following:
 * <p>
 * --add-opens=java.base/java.lang=ALL-UNNAMED
 * --add-opens java.base/sun.net.www.protocol.jar=ALL-UNNAMED
 * --add-opens=java.base/java.util=ALL-UNNAMED
 * <p>
 * as starting from java 17 the java's built-in modules
 * prevent some reflective modifications, such as:
 * field.setAccessible(true);
 */
public class CloneUtil {

    static Unsafe unsafe;

    /**
     * Cloning object is instantiating in such a way because
     * this way we don't care what constructor has the original object
     * we just create an instance and then initialize fields via reflection
     */
    static {
        try {

            Field singleoneInstanceField = Unsafe.class.getDeclaredField("theUnsafe");
            singleoneInstanceField.setAccessible(true);
            unsafe = (Unsafe) singleoneInstanceField.get(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <T> T clone(T object) throws Exception {

        Map<Object, Object> objectMap = new IdentityHashMap<>();

        T clone = deepClone(object, objectMap);

        //clean map after cloning
        objectMap.clear();

        return clone;
    }

    /**
     * Makes a deep clone of an object.
     * first it tries to find whether the cloning object has implemented clone method
     * or not. If yes then just that method is called to make the clone otherwise
     * clone is creating with the help of reflection. The objectMap is used to avoid cases
     * with cyclic references
     */
    private static <T> T deepClone(T object, Map<Object, Object> objectMap) throws Exception {
        if (object == null) {
            return null;
        }

        Method overriddenClone = getOwnOverriddenClone(object);

        if (overriddenClone != null) {
            return (T) overriddenClone.invoke(object);
        } else {

            Class<?> clazz = object.getClass();

            T clone = (T) (objectMap.containsKey(object) && objectMap.get(object) != null ? objectMap.get(object) :
                                                                                        unsafe.allocateInstance(clazz));
            objectMap.put(object, clone);

            Field[] fields = clazz.getDeclaredFields();

            for (Field field : fields) {

                if (!(Modifier.isStatic(field.getModifiers()))) {// Skip static fields
                    field.setAccessible(true);
                    Object originalValue = field.get(object);
                    if (originalValue != null && !field.getType().isPrimitive()) {
                        if (objectMap.containsKey(originalValue)) {
                            Object savedCloneValue = objectMap.get(originalValue);
                            field.set(clone, savedCloneValue);
                        } else {
                            Object copyValue;
                            boolean isArray = field.getType().isArray();
                            if (isArray) {
                                copyValue = cloneArray(originalValue, objectMap);
                            } else {
                                copyValue = deepClone(originalValue, objectMap);
                            }
                            objectMap.put(originalValue, copyValue);
                            field.set(clone, copyValue);
                        }
                    } else {
                        field.set(clone, originalValue);
                    }
                }
            }

            return clone;
        }
    }

    private static Object cloneArray(Object originalValue, Map<Object, Object> objectMap) throws Exception {
        int length = Array.getLength(originalValue);
        Class<?> componentType = originalValue.getClass().getComponentType();
        boolean isPrimitive = componentType.isPrimitive();
        Object copyArray = Array.newInstance(componentType, length);
        for (int i = 0; i < length; i++) {
            Array.set(copyArray, i, isPrimitive ? Array.get(originalValue, i) :
                    deepClone(Array.get(originalValue, i), objectMap));
        }

        return copyArray;
    }

    private static Method getOwnOverriddenClone(Object obj) {
        final String clone = "clone";
        try {
            Method cloneMethod = Object.class.getDeclaredMethod(clone);

            Method objCloneMethod = obj.getClass().getDeclaredMethod(clone);

            return !cloneMethod.equals(objCloneMethod) ? objCloneMethod : null;
        } catch (NoSuchMethodException e) {
            return null;
        }
    }
}