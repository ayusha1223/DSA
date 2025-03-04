// 2b

package DSA;

import java.util.Arrays;

public class Closepair {
     public static int[] findClosestPair(int[] xCoords, int[] yCoords) {
        int n = xCoords.length;
        int minDistance = Integer.MAX_VALUE;
        int[] result = new int[2]; 

       
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j)
                    continue; 

              
                int distance = Math.abs(xCoords[i] - xCoords[j]) + Math.abs(yCoords[i] - yCoords[j]);

                
                if (distance < minDistance
                        || (distance == minDistance && (i < result[0] || (i == result[0] && j < result[1])))) {
                    minDistance = distance;
                    result[0] = i;
                    result[1] = j;
                }
            }
        }

        return result;
    }

    public static void main(String[] args) {
        // Case 1
        int[] xCoords = { 1, 2, 3, 2, 4 };
        int[] yCoords = { 2, 3, 1, 2, 3 };
        System.out.println("Test Case 1:");
        System.out.println("Input: xCoords = [1, 2, 3, 2, 4], yCoords = [2, 3, 1, 2, 3]");
        System.out.println("Output: " + Arrays.toString(findClosestPair(xCoords, yCoords))); 

        // Case 2
        int[] xCoords2 = { 1, 1, 1, 1, 1 };
        int[] yCoords2 = { 1, 2, 3, 4, 5 };
        System.out.println("\nTest Case 2:");
        System.out.println("Input: xCoords = [1, 1, 1, 1, 1], yCoords = [1, 2, 3, 4, 5]");
        System.out.println("Output: " + Arrays.toString(findClosestPair(xCoords2, yCoords2))); 

        // Case 3
        int[] xCoords3 = { 0, 10, 20 };
        int[] yCoords3 = { 0, 10, 20 };
        System.out.println("\nTest Case 3:");
        System.out.println("Input: xCoords = [0, 10, 20], yCoords = [0, 10, 20]");
        System.out.println("Output: " + Arrays.toString(findClosestPair(xCoords3, yCoords3)));
    }
}
