package com.example.leetcodepractice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class Solution {
    private static final String TAG = "logcat";

    /**
     * 问题：
     * 1. 两数之和，使用数组循环方法
     * 描述：
     * 给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 的那 两个 整数，并返回它们的数组下标。
     * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素不能使用两遍。
     * 你可以按任意顺序返回答案。
     * 解决方法：
     * 使用数组循环方法，循环查找目标值
     * @param nums, 2 <= nums.length <= 103, -109 <= nums[i] <= 109, -109 <= target <= 109
     * @param target, 整数目标值
     * @return 只会存在一个有效答案
     */
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

    /**
     * 问题：
     * 1. 两数之和，使用数组循环方法
     * 描述：
     * 给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 的那 两个 整数，并返回它们的数组下标。
     * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素不能使用两遍。
     * 你可以按任意顺序返回答案。
     * 解决方法：
     * 使用哈希表方法，相比数组循环方法，时间复杂度较低，空间复杂度较高。
     * @param nums, 2 <= nums.length <= 103, -109 <= nums[i] <= 109, -109 <= target <= 109
     * @param target, 整数目标值
     * @return 只会存在一个有效答案
     */
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

    /**
     * 问题：
     * 2. 两数相加，使用链表
     * 描述：
     * 给你两个 非空 的链表，表示两个非负的整数。它们每位数字都是按照 逆序 的方式存储的，并且每个节点只能存储 一位 数字。
     * 请你将两个数相加，并以相同形式返回一个表示和的链表。
     * 你可以假设除了数字 0 之外，这两个数都不会以 0 开头。
     * 解决方法：
     * 两数相加，大于10需要进位。
     * @param l1, 每个链表中的节点数在范围 [1, 100] 内, 0 <= Node.val <= 9, 题目数据保证列表表示的数字不含前导零
     * @param l2, 每个链表中的节点数在范围 [1, 100] 内, 0 <= Node.val <= 9, 题目数据保证列表表示的数字不含前导零
     * @return
     */
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

    /**
     * 2. 两数相加的子方法，链表元素相加
     * @param val1
     * @param val2
     * @return
     */
    public int[] addTwoNumbersSum(int val1, int val2) {
        int sum = val1 + val2;
        return new int[]{sum % 10, sum >= 10 ? 1 : 0};
    }

    /**
     * 问题：
     * 3. 无重复字符的最长子串
     * 描述：
     * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
     * 解决方法：
     * 使用滑动窗口，
     * @param s, 0 <= s.length <= 5 * 104, 由英文字母、数字、符号和空格组成
     * @return
     */
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

    // 7. 整数反转
    public int reverse(int x) {
        try {
            int result = 0;
            int max = (int) Math.pow(2, 31);
            int length = String.valueOf(Math.abs(x)).length();
            for (int i = 1; i <= length; i++) {
                int y = x % 10;
                x /= 10;
                result = (int) (result + y * Math.pow(10, length - i));
                if (result >= max || result <= -max) return 0;
            }
            return result;
        } catch (Exception e) {
            return 0;
        }
    }
}
