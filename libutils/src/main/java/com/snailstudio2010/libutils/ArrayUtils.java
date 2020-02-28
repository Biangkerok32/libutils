package com.snailstudio2010.libutils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by xuqiqiang on 2019/05/17.
 */
public class ArrayUtils {

    public static <T> ArrayList<T> createList(T... elements) {
        return new ArrayList<>(Arrays.asList(elements));
    }

    public static <T> ArrayList<T> asList(T[] array) {
        if (array == null) return null;
        return new ArrayList<>(Arrays.asList(array));
    }

    public static <T> boolean isEmpty(T[] array) {
        return array == null || array.length <= 0;
    }

    public static <T> boolean isEmpty(Collection<T> collection) {
        return collection == null || collection.size() <= 0;
    }

    public static boolean isEmpty(Map map) {
        return map == null || map.size() <= 0;
    }

    public static <T> int size(Collection<T> collection) {
        return collection == null ? 0 : collection.size();
    }

    public static <T> int size(T[] array) {
        return array == null ? 0 : array.length;
    }

    public static int size(Map map) {
        return map == null ? 0 : map.size();
    }

    public static String join(CharSequence delimiter, String[] elements) {
        StringBuilder contact = new StringBuilder();
        if (elements != null) {
            for (int i = 0; i < elements.length; i++) {
                contact.append(elements[i]);
                if (i < elements.length - 1) contact.append(delimiter);
            }
        }
        return contact.toString();
    }

    public static <E> void add(Collection<E> collection, E o) {
        if (collection == null || o == null || collection.contains(o)) return;
        collection.add(o);
    }

    public static <E> void add(List<E> collection, int index, E o) {
        if (collection == null || o == null || collection.contains(o)) return;
        collection.add(index, o);
    }

    public static boolean remove(Collection collection, Object o) {
        if (collection != null && o != null && collection.contains(o)) {
            return collection.remove(0);
        }
        return false;
    }
}