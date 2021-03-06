package com.interview;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Ming Li
 */
public class Utils {
    public static List<String> range(int start, int end) {
        return IntStream.rangeClosed(start, end).mapToObj(String::valueOf).collect(Collectors.toList());
    }
}
