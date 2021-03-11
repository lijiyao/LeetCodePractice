package com.example.leetcodepractice;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testData() {
        Solution solution = new Solution();
        int[] num2 = new int[]{1, 4, 6, 6};
        int[] num1 = new int[]{0, 2, 2, 3};
        System.out.println("logcat: size = " + solution.findMedianSortedArrays(num1, num2));
    }
}