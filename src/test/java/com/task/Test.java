package com.task;

import java.util.List;
import java.util.Set;

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

        expected = Set.of(
                c(0), c(1), c(2), c(3), c(4),
                c(0, 1), c(0, 2), c(0, 3), c(0, 4), c(1, 2), c(1, 3), c(1, 4), c(2, 3), c(2, 4), c(3, 4),
                c(0, 1, 2), c(0, 1, 3), c(0, 2, 3), c(0, 1, 4), c(0, 2, 4), c(0, 3, 4), c(1, 2, 3), c(1, 2, 4), c(1, 3, 4), c(2, 3, 4),
                c(0, 1, 2, 3), c(0, 1, 2, 4), c(0, 1, 3, 4), c(0, 2, 3, 4), c(1, 2, 3, 4)
        );
        actual = Task.possibleCourses(5, 4);
        assertEquals(expected, actual);
    }

    @org.junit.Test
    public void testPossibleCoursesWithPrerequisites() {
        var prerequisites = List.of(List.of(1, 0), List.of(2, 3));
        var expected = Set.of(c(0), c(3), c(4), c(0, 3), c(0, 4), c(3, 4), c(0, 3, 4));
        var actual = Task.possibleCoursesFirstSemester(5, 3, prerequisites);
        assertEquals(expected, actual);
    }

    @org.junit.Test
    public void testHasCycles() {
        var prerequisites = List.of(List.of(0, 1), List.of(1, 0));
        assertTrue("False negative", Task.hasCycles(prerequisites));

        prerequisites = List.of(List.of(0, 1), List.of(1, 2), List.of(2, 0));
        assertTrue("False negative", Task.hasCycles(prerequisites));

        prerequisites = List.of(List.of(1, 0), List.of(2, 3), List.of(2, 4), List.of(5, 6));
        assertFalse("False positive", Task.hasCycles(prerequisites));
    }

    // helps to construct combinations
    private static <T> Set<T> c(T... args) {
        return Set.of(args);
    }
}
