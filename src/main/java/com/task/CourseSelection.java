package com.task;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

/**
 * Simple abstraction that represents a set of courses. For conciseness purpose only.
 */
public class CourseSelection extends HashSet<Integer> {
    private CourseSelection(Collection<? extends Integer> c) {
        super(c);
    }

    public static CourseSelection of(int[] selection) {
        return new CourseSelection(Arrays.stream(selection).boxed().collect(Collectors.toSet()));
    }
}
