package com.alkemy.ong.util;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MapperUtil {

    /**
     * Method used for apply a function to each item of a collection of entities
     * @param list List of entities
     * @param function Function applied to each of the items of the collection
     * @return List transformed
     * @param <E> Entity
     * @param <T> Dto
     */
    public static <E, T> List<T> streamListNonNull(List<E> list, Function<E, T> function) {
        if (list == null) return null;
        return list.stream()
                .map(function)
                .collect(Collectors.toList());
    }

}
