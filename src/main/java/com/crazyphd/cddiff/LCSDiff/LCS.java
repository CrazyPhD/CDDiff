package com.crazyphd.cddiff.LCSDiff;

public class LCS {
    /**
     * Construct LCS matrix
     * @param a file #1 contents
     * @param b file #2 contents
     * @return LCS matrix
     */
    public static int[][] build(String[] a, String[] b) {
        int m = a.length;
        int n = b.length;
        int[][] lcs = new int[m+1][n+1];
        for (int i = m; i >= 0; i--) {
            for (int j = n; j >= 0; j--) {
                if (i == m || j == n) {
                    lcs[i][j] = 0;
                } else if (a[i].equals(b[j])) {
                    lcs[i][j] = lcs[i+1][j+1] + 1;
                } else {
                    lcs[i][j] = Math.max(lcs[i][j+1], lcs[i+1][j]);
                }
            }
        }
        return lcs;
    }
}
