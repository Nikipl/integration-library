package ru.evotor.framework;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by a.kuznetsov on 30/04/2017.
 */

public final class Utils {
    public static <T extends Enum<T>> T safeValueOf(Class<T> clazz, String name, T defaultValue) {
        Objects.requireNonNull(clazz);
        if (name == null) {
            return defaultValue;
        }

        try {
            return java.lang.Enum.valueOf(clazz, name);
        } catch (IllegalArgumentException e) {
            return defaultValue;
        }
    }

    public static <T, R> List<R> filterByClass(List<T> source, Class<R> clazz) {
        List<R> list = new ArrayList<>();
        for (T t : source) {
            if (clazz.isInstance(t)) {
                list.add(((R) t));
            }
        }
        return list;
    }
}
