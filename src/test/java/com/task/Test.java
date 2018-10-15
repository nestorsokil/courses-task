package com.task;

import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Test {
    @org.junit.Test
    public void testPossibleCourses() {
        var expected = Set.of(c(0), c(1), c(2), c(0, 1), c(0, 2), c(1, 2));
        var actual = Task.possibleCourses(3, 2);
        assertEquals(expected, actual);

        expected = Set.of(
                c(0), c(1), c(2), c(3), c(4),
                c(0, 1), c(0, 2), c(0, 3), c(0, 4), c(1, 2), c(1, 3), c(1, 4), c(2, 3), c(2, 4), c(3, 4),
                c(0, 1, 2), c(0, 1, 3), c(0, 2, 3), c(0, 1, 4), c(0, 2, 4), c(0, 3, 4), c(1, 2, 3), c(1, 2, 4), c(1, 3, 4), c(2, 3, 4)
        );
        actual = Task.possibleCourses(5, 3);
        assertEquals(expected, actual);
    }

    @org.junit.Test
    public void testPossibleCoursesWithPrerequisites() {
        var prerequisites = List.of(List.of(1, 0), List.of(2, 3));
        var expected = Set.of(c(0), c(3), c(4), c(0, 3), c(0, 4), c(3, 4), c(0, 3, 4));
        var actual = Task.possibleCoursesFirstYear(5, 3, prerequisites);
        assertEquals(expected, actual);
    }

    @org.junit.Test
    public void testHasCycles() {
        var prerequisites = asList(asList(0, 1), asList(1, 0));
        assertTrue("False negative", Task.hasCycles(prerequisites));

        prerequisites = asList(asList(0, 1), asList(1, 2), asList(2, 0));
        assertTrue("False negative", Task.hasCycles(prerequisites));

        prerequisites = asList(asList(1, 0), asList(2, 3), asList(2, 4), asList(5, 6));
        assertFalse("False positive", Task.hasCycles(prerequisites));
    }

    // helps to construct combinations
    private static <T> Set<T> c(T... args) {
        return Set.of(args);
    }
}
