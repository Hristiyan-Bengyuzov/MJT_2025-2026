
// zad 1
public static String longestUniqueSubstring(String s) {
    int maxStart = 0;
    int maxLen = 0;

    for (int i = 0; i < s.length(); i++) {
        boolean[] seen = new boolean[26];
        int currLen = 0;

        for (int j = i; j < s.length(); j++) {
            int letterInd = s.charAt(j) - 'a';
            if (!seen[letterInd])
                break;

            currLen++;
            seen[letterInd] = true;
        }

        if (currLen > maxLen) {
            maxLen = currLen;
            maxStart = i;
        }
    }

    return s.substring(maxStart, maxStart + maxLen);
}

// zad 2
public static int getSum(int[] arr) {
    int sum = 0;
    for (int num : arr) {
        sum += num;
    }
    return sum;
}

public static int minDifference(int[] tasks) {
    if (tasks == null || tasks.length == 0)
        return 0;

    if (tasks.length == 1)
        return tasks[0];

    int totalSum = getSum(tasks);
    int totalSubsets = 1 << tasks.length - 1;
    int minDiff = Integer.MAX_VALUE;

    for (int mask = 1; mask < totalSubsets; mask++) {
        int firstTeamSum = 0;

        for (int i = 0; i < tasks.length; i++) {
            if ((mask & (1 << i)) != 0) {
                firstTeamSum += tasks[i];
            }
        }

        int secondTeamSum = totalSum - firstTeamSum;
        minDiff = Math.min(Math.abs(firstTeamSum - secondTeamSum), minDiff);
    }

    return minDiff;
}

void main() {
    System.out.println(longestUniqueSubstring("abcabcbb")); // "abc"
    System.out.println(longestUniqueSubstring("bbbbb")); // "b"
    System.out.println(longestUniqueSubstring("pwwkew")); // "wke"
    System.out.println(longestUniqueSubstring("abcdefg")); // "abcdefg"
    System.out.println(longestUniqueSubstring("x")); // "x"
    System.out.println(longestUniqueSubstring("")); // ""

    System.out.println(minDifference(new int[]{1, 2, 3, 4, 5})); // 1
    System.out.println(minDifference(new int[]{10, 20, 15, 5})); // 0
    System.out.println(minDifference(new int[]{7, 3, 2, 1, 5, 4})); // 0
    System.out.println(minDifference(new int[]{9, 1, 1, 1})); // 6
    System.out.println(minDifference(new int[]{})); // 0
    System.out.println(minDifference(new int[]{120})); // 120
    System.out.println(minDifference(new int[]{30, 30})); // 0
}
