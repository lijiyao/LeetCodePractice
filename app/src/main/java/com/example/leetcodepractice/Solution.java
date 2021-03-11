package com.example.leetcodepractice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class Solution {
    private static final String TAG = "logcat";

    // 1. 两数之和，使用数组循环方法
    public int[] twoSumByArray(int[] nums, int target) {
        int maxTarget = (int) Math.pow(10, 9);
        if (2 <= nums.length && nums.length <= Math.pow(10, 3) && maxTarget >= Math.abs(target)) {
            for (int i = 0; i < nums.length; i++) {
                if (maxTarget < Math.abs(nums[i])) return null;

                for (int j = i + 1; j < nums.length; j++) {
                    if (target == nums[i] + nums[j]) {
                        return new int[]{i, j};
                    }
                }
            }
        }
        return null;
    }

    // 1. 两数之和，使用哈希表方法，相比数组循环方法，时间复杂度较低，空间复杂度较高
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(target - nums[i])) {
                return new int[]{map.get(target - nums[i]), i};
            }
            map.put(nums[i], i);
        }
        return null;
    }

    // 2. 两数相加，使用链表
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode head = null;
        ListNode tail = null;
        int[] sum = new int[]{0, 0};
        while (null != l1 || null != l2) {
            sum = addTwoNumbersSum(null != l1 ? l1.val + sum[1] : sum[1], null != l2 ? l2.val : 0);
            if (null == head) {
                head = tail = new ListNode(sum[0]);
            } else {
                tail.next = new ListNode(sum[0]);
                tail = tail.next;
            }
            if (null != l1) l1 = l1.next;
            if (null != l2) l2 = l2.next;
        }
        if (0 < sum[1]) {
            tail.next = new ListNode(sum[1]);
        }
        return head;
    }

    // 2. 两数相加的子方法，链表元素相加
    public int[] addTwoNumbersSum(int val1, int val2) {
        int sum = val1 + val2;
        return new int[]{sum % 10, sum >= 10 ? 1 : 0};
    }

    // 3. 无重复字符的最长子串
    public int lengthOfLongestSubstring(String s) {
        if (null == s) return 0;

        int rk = -1, ans = 0, n = s.length();
        HashSet<Character> set = new HashSet<>();
        for (int i = 0; i < n; i++) {
            if (0 != i) {
                set.remove(s.charAt(i - 1));
            }

            while (rk + 1 < n && !set.contains(s.charAt(rk + 1))) {
                set.add(s.charAt(rk + 1));
                rk += 1;
            }
            ans = Math.max(rk - i + 1, ans);
        }

        return ans;
    }

    // 4. 寻找两个正序数组的中位数，主要分两步，一是合并数组，而是计算数组的中位数
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        return findMedianFromIntArrays(mergeIntArray(nums1, nums2));
    }

    // 4. 寻找两个正序数组的中位数的子方法，合并数组
    public int[] mergeIntArray(int[] nums1, int[] nums2) {
        int length1 = nums1.length, length2 = nums2.length;
        int sumLength = length1 + length2;
        int[] nums = new int[sumLength];
        int position1 = 0, position2 = 0, sumPosition = 0;
        while (position1 < length1 || position2 < length2) {
            if (position1 < length1 && position2 < length2) {
                if (nums1[position1] < nums2[position2]) {
                    nums[sumPosition] = nums1[position1];
                    position1 += 1;
                } else {
                    nums[sumPosition] = nums2[position2];
                    position2 += 1;
                }
            } else if (position1 < length1) {
                nums[sumPosition] = nums1[position1];
                position1 += 1;
            } else {
                nums[sumPosition] = nums2[position2];
                position2 += 1;
            }
            sumPosition += sumPosition < sumLength - 1 ? 1 : 0;
        }
        return nums;
    }

    // 4. 寻找两个正序数组的中位数的子方法，计算中位数
    public double findMedianFromIntArrays(int[] nums) {
        double result = 0.0;
        int length = nums.length;
        if (0 == length) return result;
        boolean isEven = 0 == length % 2;
        int medianLength = (int) length / 2 - 1;
        if (isEven) {
            result = (nums[medianLength] + nums[medianLength + 1]) / 2.0;
        } else {
            result = nums[medianLength + 1];
        }
        return result;
    }

    // 5、最长回文子串
    public String longestPalindrome(String s) {
        if (null == s) return "";

        String resultStr = "";
        Map<Character, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            if (map.containsKey(s.charAt(i))) {
                List<Integer> list = map.get(s.charAt(i));
                if (null != list) {
                    list.add(i);
                }
            } else {
                List<Integer> list = new ArrayList<>();
                list.add(i);
                map.put(s.charAt(i), list);
            }
        }

        for (Character c : map.keySet()) {
            List<Integer> list = map.get(c);
            if (null == list) continue;
            if (1 >= list.size() && resultStr.length() < 1) {
                resultStr = s.charAt(list.get(0)) + "";
                continue;
            }
            for (int i = 1; i < list.size(); i++) {
                if (resultStr.length() < list.get(i) - list.get(i - 1)) {
                    resultStr = s.substring(list.get(i - 1), list.get(i) + 1);
                }
            }
        }

        return resultStr;
    }
}
