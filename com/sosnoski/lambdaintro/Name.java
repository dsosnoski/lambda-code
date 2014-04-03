package com.sosnoski.lambdaintro;

public class Name implements Comparable<Name> {
    public final String firstName;
    public final String lastName;

    public Name(String first, String last) {
        firstName = first;
        lastName = last;
    }

    // only needed for chained comparator
    public String getFirstName() {
        return firstName;
    }

    // only needed for chained comparator
    public String getLastName() {
        return lastName;
    }

    @Override
    // only needed for direct comparator (not for chained comparator)
    public int compareTo(Name other) {
        int diff = lastName.compareTo(other.lastName);
        if (diff == 0) {
            diff = firstName.compareTo(other.firstName);
        }
        return diff;
    }

    public String toString() {
        return lastName + ", " + firstName;
    }
}