package com.sosnoski.lambdaintro;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class NameSort {
    
    private static final Name[] NAMES = new Name[] {
        new Name("Sally", "Smith"),
        new Name("John", "Smith"),
        new Name("Mary", "Smith"),
        new Name("John", "Queue"),
        new Name("Adam", "Simms"),
        new Name("Adam", "Smith"),
        new Name("Suzie", "Queue"),
        new Name("George", "Simms")
    };
    
    private static void printNames(String caption, Name[] names) {
        System.out.println();
        System.out.println(caption);
        for (Name name : names) {
            System.out.println(name.toString());
        }
    }

    public static void main(String[] args) {

        // sort array using anonymous inner class
        Name[] copy = Arrays.copyOf(NAMES, NAMES.length);
        Arrays.sort(copy, new Comparator<Name>() {
            @Override
            public int compare(Name a, Name b) {
                return a.compareTo(b);
            }
        });
        printNames("Names sorted with anonymous inner class:", copy);

        // sort array using lambda expression
        copy = Arrays.copyOf(NAMES, NAMES.length);
        Arrays.sort(copy, (a, b) -> a.compareTo(b));
        printNames("Names sorted with lambda expression:", copy);
        
        // use predicate composition to remove matching names
        List<Name> list = new ArrayList<>();
        for (Name name : NAMES) {
            list.add(name);
        }
        Predicate<Name> pred1 = name -> "Sally".equals(name.firstName);
        Predicate<Name> pred2 = name -> "Queue".equals(name.lastName);
        list.removeIf(pred1.or(pred2));
        printNames("Names filtered by predicate:", list.toArray(new Name[list.size()]));

        // sort array using key extractor lambdas
        copy = Arrays.copyOf(NAMES, NAMES.length);
        Comparator<Name> comp = Comparator.comparing(name -> name.lastName);
        comp = comp.thenComparing(name -> name.firstName);
        Arrays.sort(copy, comp);
        printNames("Names sorted with key extractor comparator:", copy);
        
        // but this gives compilator errors for incompatible types, even with cast
//        Comparator<Name> com1 = Comparator.comparing(name1 -> name1.lastName)
//            .thenComparing(name2 -> name2.firstName);

        // sort array using existing methods as lambdas
        copy = Arrays.copyOf(NAMES, NAMES.length);
        comp = Comparator.comparing(Name::getLastName).thenComparing(Name::getFirstName);
        Arrays.sort(copy, comp);
        printNames("Names sorted with existing methods as lambdas:", copy);
    }
}