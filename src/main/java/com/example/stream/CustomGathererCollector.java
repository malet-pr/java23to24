package com.example.stream;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class CustomGathererCollector {

    public static <T, K> Stream<Map.Entry<K, List<T>>> gatherBy(Stream<T> stream, Function<T, K> classifier) {
        Map<K, List<T>> grouped = stream.collect(Collectors.groupingBy(classifier));
        return grouped.entrySet().stream();
    }

    public static <T> Stream<List<T>> gatherChunks(Stream<T> stream, int size) {
        Iterator<T> iterator = stream.iterator();
        return Stream.generate(() -> {
            List<T> chunk = new ArrayList<>();
            for (int i = 0; i < size && iterator.hasNext(); i++) {
                chunk.add(iterator.next());
            }
            return chunk;
        }).takeWhile(list -> !list.isEmpty());
    }

    public static Collector<String, StringJoiner, String> joiningWithBrackets(String prefix, String suffix) {
        return Collector.of(
                () -> new StringJoiner(", ", prefix, suffix),
                StringJoiner::add,
                StringJoiner::merge,
                StringJoiner::toString
        );
    }

    public static <T> Collector<T, ?, Long> countUnique() {
        return Collector.of(
                HashSet::new,
                Set::add,
                (set1, set2) -> { set1.addAll(set2); return set1; },
                set -> (long) set.size()
        );
    }


    public static void main(String[] args) {

        Stream<String> fruits = Stream.of("apple", "banana", "cherry", "date");
        gatherBy(fruits, String::length).forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));

        Stream<Integer> numbers = IntStream.rangeClosed(1, 10).boxed();
        gatherChunks(numbers, 3).forEach(chunk -> System.out.println(chunk));

        Stream<String> names = Stream.of("Alice", "Bob", "Charlie");
        System.out.println(names.collect(joiningWithBrackets("[", "]")));

        Stream<String> words = Stream.of("apple", "banana", "apple", "cherry", "banana","date");
        System.out.println(words.collect(countUnique()));

    }



}
