package com.example.stream;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StreamCollector {

    // Example method using collector to map
    public static Map<Integer,String> mapByLength(List<String> list, boolean replace){
        if(replace){
            return list.stream()
                    .collect(Collectors.toMap(String::length, s -> s, (existing, replacement) -> replacement));
        } else {
            return list.stream()
                    .collect(Collectors.toMap(String::length, s -> s, (existing, replacement) -> existing));
        }
    }

    // Example method using collector joining
    public static String joinUsingSeparator(List<String> list, CharSequence separator){
       return list.stream().collect(Collectors.joining(separator));
    }

    // Example method using collector groupingBy
    public static Map<Integer, List<String>> getGrouped(List<String> list) {
        return list.stream().collect(Collectors.groupingBy(String::length));
    }

    // Example method using partitioningBy
    public static Map<Boolean, List<Integer>> partitioned(List<Integer> list) {
        return list.stream().collect(Collectors.partitioningBy(n -> n % 2 == 0));
    }




    public static void main(String[] args) {

        System.out.println("\n1) Example method using collector to map");
        List<String> list1 = List.of("one", "two", "three", "four", "five", "six", "seven", "eight", "nine");
        System.out.println("replacing: " + mapByLength(list1,true));
        System.out.println("existing: " + mapByLength(list1,false));

        System.out.println("\n2) Example method using collector joining");
        System.out.println(joinUsingSeparator(list1," "));
        System.out.println(joinUsingSeparator(list1,"****"));

        System.out.println("\n3) Example method using collector groupingBy");
        System.out.println(getGrouped(list1));

        System.out.println("\n4) Example method using collector partitioningBy");
        List<Integer> list2 = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        System.out.println(partitioned(list2));

    }

}


