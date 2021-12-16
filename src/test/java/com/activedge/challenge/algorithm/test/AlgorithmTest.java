package com.activedge.challenge.algorithm.test;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class AlgorithmTest {
    public static void main(String[] args) throws IOException {
	int[] array1 = { 1, 3, 6, 4, 1, 2 };
	int[] array2 = { 5, -1, -3 };
	System.out.println("result for array1 : " + smallestPositiveIntegerNotOcurringInArray(array1));
	System.out.println("result for array2 : " + smallestPositiveIntegerNotOcurringInArray(array2));
    }

    static int smallestPositiveIntegerNotOcurringInArray(int[] A) {
	// Step one get the length of the array
	int N = A.length;
	// step two create a hash set to store unit values
	Set<Integer> set = new HashSet<>();
	for (int a : A) {
	    // step three add only positive integers from the array to the set
	    if (a > 0) {
		set.add(a);
	    }
	}
	// step four if the set is empty at this point, then that means all integers in
	// the array
	// were negative so the smallest positive integer not in the array is 1.
	if (set.isEmpty())
	    return 1;

	// step five..loop true the set of positive integers, increment the value of i
	// and make it end at the original size
	// of the main array. when we see a number in-between that doesn't appear in the
	// set, that is the number we are
	// looking for.
	for (int i = 1; i <= N + 1; i++) {
	    if (!set.contains(i)) {
		return i;
	    }
	}
	return 1;
    }

}
