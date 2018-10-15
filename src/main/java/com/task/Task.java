package com.task;

import java.util.*;
import java.util.stream.IntStream;

public final class Task {
    private Task() {}

    // Task 1-2
    /**
     * Finds all possible selections of courses. Each course will be given int label in range [0; total-1]
     * @param total Total number of courses.
     * @param max Maximum number of courses to take (minimum is always 1).
     * @return A set containing all possible courseSelections.
     */
    public static Set<CourseSelection> possibleCourses(int total, int max) {
        if (total > 100 || total < 2 || max > 100 || max < 2 || max >= total) {
            throw new IllegalArgumentException("My magic numbers don't like that");
        }
        var result = new HashSet<CourseSelection>();
        var labels = IntStream.range(0, total).toArray();
        var combinationPlaceholder = new int[max];
        findCombinations(0, max, max, labels, combinationPlaceholder, result);
        return result;
    }

    // Task 3
    /**
     * Same as {@link Task#possibleCourses(int, int)}, but only returns first-year selections due to provided prerequisites.
     * @param total Total number of courses.
     * @param possible Size of combinations.
     * @param prerequisites List of prerequisite pairs.
     * @return A set containing all possible courseSelections.
     */
    public static Set<CourseSelection> possibleCoursesFirstYear(int total, int possible, List<List<Integer>> prerequisites) {
        if (prerequisites.isEmpty()) {
            return possibleCourses(total, possible);
        }
        if (total > 100 || total < 2 || possible > 100 || possible < 2 || possible >= total || prerequisites.size() > 100) {
            throw new IllegalArgumentException("My magic numbers don't like that");
        }
        var prerequisitesAsMap = new HashMap<>();
        for (var pair : prerequisites) {
            if (pair.size() != 2) {
                throw new IllegalArgumentException("Should be a list of pairs");
            }
            prerequisitesAsMap.put(pair.get(0), pair.get(1));
        }
        var allowedLabels = IntStream.range(0, total)
                .filter(p -> !prerequisitesAsMap.containsKey(p))
                .toArray();
        var result = new HashSet<CourseSelection>();
        var combinationPlaceholder = new int[possible];
        findCombinations(0, possible, possible, allowedLabels, combinationPlaceholder, result);
        return result;
    }

    /**
     * This method does all the "heavy lifting" by looking though the labels and picking the unique combinations.
     */
    private static void findCombinations(int index, int shift, int combinationSize, int[] labels,
                                         int[] combination, Set<CourseSelection> result) {
        if (shift == 0) {
            result.add(CourseSelection.of(combination));
            return;
        }

        for (int i = index; i <= labels.length - shift; i++) {
            int combinationIndex = combinationSize - shift;
            combination[combinationIndex] = labels[i];
            findCombinations(i + 1,shift - 1, combinationSize, labels, combination, result);

            if (combinationSize > 1) {
                var subCombinationSize = combinationSize - 1;
                var subCombination = new int[subCombinationSize];
                findCombinations(i, shift - 1, subCombinationSize, labels, subCombination, result);
            }
        }
    }

    // Task 4
    /**
     * Detects whether all the provided prerequisites can be satisfied.
     * @param prerequisites List of prerequisite pairs.
     * @return true if there is a cycle, i.e. the prerequisites cannot be satisfied; false otherwise
     */
    public static boolean hasCycles(List<List<Integer>> prerequisites) {
        if (prerequisites.size() > 100 || prerequisites.isEmpty()) {
            throw new IllegalArgumentException("Keep in range [1, 100]");
        }
        final Map<Integer, List<Integer>> outgoing = new HashMap<>();
        final Set<Integer> uniqueCourses = new HashSet<>();
        for (List<Integer> pair : prerequisites) {
            if (pair.size() != 2) {
                throw new IllegalArgumentException("Should be a list of pairs");
            }
            uniqueCourses.addAll(pair);
            var node = pair.get(0);
            var prerequisite = pair.get(1);
            if (!outgoing.containsKey(node)) {
                List<Integer> neighbors = new ArrayList<>();
                neighbors.add(prerequisite);
                outgoing.put(node, neighbors);
            } else {
                outgoing.get(node).add(prerequisite);
            }
        }
        // the objective is really to detect a cycle in a directed graph
        return new Graph(outgoing, uniqueCourses).findCycle();
    }
}