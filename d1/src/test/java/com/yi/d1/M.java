package com.yi.d1;

import java.util.Arrays;

public class M {
    static A a2 = new A();

//    public static void main(String[] args) {
//        System.out.println("a2.i=" + a2.i);
//        System.out.println("a2.j=" + a2.j);
//        A a1 = new A();
//
//        System.out.println("a1.i=" + a1.i);
//        System.out.println("a1.j=" + a1.j);
//        System.out.println("a2.i=" + a2.i);
//        System.out.println("a2.j=" + a2.j);
//    }

    public static void main(String[] args) {
        for (int i = 1; i < 100; i++) {
            System.out.println(findNextPrime(i));
        }
    }

//    2,3,5,7,11,13,17,19,23,29,31,37,41,43,47,53,59,61,67,71,73,79,83,89,97,

    private static int findNextPrime(int i) {
        while (true) {
            int n = i++;
            Integer[] aa = new Integer[] {2,3,5,7};
            if (Arrays.asList(aa).contains(n)) {
                return n;
            }
            if (n % 2 != 0 && n % 3 != 0 && n % 5 != 0 && n % 7 != 0 && !canSqrt(n)) {
                return n;
            }
        }
    }

    private static boolean canSqrt(int i) {
        double r = (double) i;
        double d = Math.sqrt(r);
        return d - Math.floor(d) == 0;
    }
}
