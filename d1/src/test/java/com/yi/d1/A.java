package com.yi.d1;

public class A {
    A() {
        i = j++;
    }
//    A() {
//        i = (j++ != 0) ? ++j : --j;
//    }

    public int i;
    public static int j = 0;
}
