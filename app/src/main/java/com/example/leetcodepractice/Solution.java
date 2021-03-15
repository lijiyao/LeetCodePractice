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
        // 整数除整数，商是整数，所以最好在被除数加小数点
//        int medianLength = (int) length / 2 - 1;
        int medianLength = (int) (length / 2.0 - 1);
        if (isEven) {
            result = (nums[medianLength] + nums[medianLength + 1]) / 2.0;
        } else {
            result = nums[medianLength + 1];
        }
        return result;
    }

    // 5. 最长回文子串
    public String longestPalindrome(String s) {
        if (null == s || 0 == s.length()) return "";

        String subStr1 = "";
        int pos = 0, w = 0, length = s.length();
        for (int i = 0; i < length; i++) {
            for (int j = 0; i - j >= 0 && i + j < length; j++) {
                if (s.charAt(i + j) == s.charAt(i - j)) {
                    if (j > w) {
                        w = j;
                        pos = i;
                    }
                } else {
                    break;
                }
            }
        }
        subStr1 = s.substring(pos - w, pos + w + 1);

        String subStr2 = "";
        int pos2 = 0, w2 = -1;
        for (int i = 0; i < length; i++) {
            for (int j = 0; i + j + 1 < length && i - j >= 0; j++) {
                if (s.charAt(i - j) == s.charAt(i + j + 1)) {
                    if (j > w2) {
                        w2 = j;
                        pos2 = i;
                    }
                } else {
                    break;
                }
            }
        }
        subStr2 = s.substring(pos2 - (-1 == w2 ? 0 : w2), pos2 + w2 + 2);

        return subStr1.length() > subStr2.length() ? subStr1 : subStr2;
    }

    // 6. Z 字形变换
    public String convert(String s, int numRows) {
        if (null == s || 0 == s.length() || 0 == numRows || 1 == numRows) return s;

        ArrayList<ArrayList<Character>> rowList = new ArrayList<>();
        for (int i = 0; i < numRows; i++) {
            rowList.add(new ArrayList<>());
        }

        boolean add = true;
        int pos = 0;
        for (int i = 0; i < s.length(); i++) {
            rowList.get(pos).add(s.charAt(i));
            if (0 == pos % numRows) {
                add = true;
            } else if (numRows - 1 == pos % numRows) {
                add = false;
            }
            if (add) {
                pos += 1;
            } else {
                pos -= 1;
            }
        }

        StringBuilder resultStr = new StringBuilder();
        for (ArrayList<Character> charList : rowList) {
            for (Character character : charList) {
                resultStr.append(character);
            }
        }
        return resultStr.toString();
    }

    // 6. Z 字形变换，应该有第二种方法，是一个公式，但是我暂时没想出来
    public String convert2(String s, int numRows) {
        if (null == s || 0 == s.length() || 0 == numRows || 1 == numRows) return s;

        char[] charS = s.toCharArray();

        for (int i = 0; i < s.length(); i++) {
            charS[i] = s.charAt(i);
        }
        return String.valueOf(charS);
    }
}
