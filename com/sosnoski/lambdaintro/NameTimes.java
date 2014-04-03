package com.sosnoski.lambdaintro;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class NameTimes {
    
    private static final char[] NAME_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    
    private final Name[] unsortedNames;
    
    private NameTimes(int count) {
        unsortedNames = new Name[count];
        Random rand = new Random(System.currentTimeMillis());
        for (int i = 0; i < count; i++) {
            unsortedNames[i] = new Name(nameString(rand, nameLength(rand)), nameString(rand, nameLength(rand)));
        }
    }

    private static int nameLength(Random rand) {
        return rand.nextInt(18) + 2;
    }
    
    private static String nameString(Random rand, int length) {
        StringBuilder builder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            builder.append(NAME_CHARS[rand.nextInt(NAME_CHARS.length)]);
        }
        return builder.toString();
    }
    
    private static void sortDirect(Name[] names) {
        Arrays.sort(names);
    }
    
    private static void sortInnerClass(Name[] names) {
        Arrays.sort(names, new Comparator<Name>() {
            @Override
            public int compare(Name a, Name b) {
                return a.compareTo(b);
            }
        });
    }
    
    private static void sortLambda(Name[] names) {
        Arrays.sort(names, (a, b) -> a.compareTo(b));
    }
    
    private static void sortComposedComparator(Name[] names) {
        Comparator<Name> comp = Comparator.comparing(Name::getLastName).thenComparing(Name::getFirstName);
        Arrays.sort(names, comp);
    }
    
    private int timeSort(String variant, Sorter sorter) {
        Name[] working = Arrays.copyOf(unsortedNames, unsortedNames.length);
        long start = System.nanoTime();
        sorter.sort(working);
        int time = (int)((System.nanoTime() - start + 500000) / 1000000);
        System.out.println(" " + variant + " took " + time + " ms");
        return time;
    }
    
    private interface Sorter {
        void sort(Name[] names);
    }

    public static void main(String[] args) {
        int count = 100000;
        NameTimes times = new NameTimes(count);
        String[] names = new String[] { "sortInnerClass", "sortDirect", "sortLambda", "sortComposedComparator"};
        Sorter[] sorters = new Sorter[] { NameTimes::sortInnerClass, NameTimes::sortDirect, NameTimes::sortLambda, NameTimes::sortComposedComparator};
        long[] bestimes = new long[] { Long.MAX_VALUE, Long.MAX_VALUE, Long.MAX_VALUE, Long.MAX_VALUE };
        for (int i = 0; i < 10; i++) {
            if (i > 0) {
                System.out.println(" ... pausing for JVM to settle");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {}
            }
            System.out.println("Timing sort of " + count + " random names");
            for (int j = 0; j < names.length; j++) {
                int time = times.timeSort(names[j], sorters[j]);
                if (bestimes[j] > time) {
                    bestimes[j] = time;
                }
            }
        }
        System.out.println();
        System.out.println("Best times:");
        for (int i = 0; i < names.length; i++) {
            System.out.println(" " + names[i] + " = " + bestimes[i]);
        }
    }
}