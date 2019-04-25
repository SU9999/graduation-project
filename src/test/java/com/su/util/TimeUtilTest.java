package com.su.util;

import org.junit.Test;

public class TimeUtilTest {
    @Test
    public void testTimeMill(){
        System.out.println(System.currentTimeMillis());
    }

    @Test
    public void testDouble(){
        Double d1 = new Double(0.01);
        Double d2 = new Double(0.01);

        System.out.println(d1.equals(d2));
        System.out.println(0.01==0.01);
    }

    @Test
    public void testString(){
        Integer a = Integer.parseInt("");
        System.out.println(a);
    }
}
