//1a

package DSA;

public class Criticaltemp {
    
    public static int findMinMeasurements(int k, int n) {
        
        int[][] dp = new int[k + 1][n + 1];

        
        for (int i = 1; i <= n; i++) {
            dp[1][i] = i;
        }

        for (int i = 1; i <= k; i++) {
            dp[i][0] = 0;
        }

       
        for (int i = 2; i <= k; i++) { 
            for (int j = 1; j <= n; j++) { 
                dp[i][j] = Integer.MAX_VALUE;
                for (int x = 1; x <= j; x++) {
                    
                    int res = 1 + Math.max(dp[i - 1][x - 1], dp[i][j - x]);
                    dp[i][j] = Math.min(dp[i][j], res);
                }
            }
        }

        return dp[k][n];
    }

    
    public static void main(String[] args) {
        int[][] testCases = {
                { 1, 2, 2 }, 
                { 2, 6, 3 },
                { 3, 14, 4 }, 
        };

        for (int[] testCase : testCases) {
            int k = testCase[0];
            int n = testCase[1];
            int expected = testCase[2];
            int result = findMinMeasurements(k, n);

            
            System.out.println("Test Case: k = " + k + ", n = " + n);
            System.out.println("Expected Output: " + expected);
            System.out.println("Actual Output: " + result);
            System.out.println("Result: " + (result == expected ? "PASS" : "FAIL"));
            System.out.println();
        }
    }
}
