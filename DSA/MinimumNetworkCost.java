package DSA;

//3a

import java.util.*;

public class MinimumNetworkCost {

    
    static class UnionFind {
        int[] parent;
        int[] rank;

        UnionFind(int n) {
            parent = new int[n + 1];
            rank = new int[n + 1];
            for (int i = 0; i <= n; i++) {
                parent[i] = i;
                rank[i] = 1;
            }
        }

        int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]); 
            }
            return parent[x];
        }

        boolean union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);
            if (rootX == rootY)
                return false; 

           
            if (rank[rootX] > rank[rootY]) {
                parent[rootY] = rootX;
            } else if (rank[rootX] < rank[rootY]) {
                parent[rootX] = rootY;
            } else {
                parent[rootY] = rootX;
                rank[rootX]++;
            }
            return true;
        }
    }

    public static int minCostToConnectDevices(int n, int[] modules, int[][] connections) {
        
        List<int[]> edges = new ArrayList<>();

        
        for (int i = 0; i < n; i++) {
            edges.add(new int[] { 0, i + 1, modules[i] }); 
        }

       
        for (int[] connection : connections) {
            edges.add(new int[] { connection[0], connection[1], connection[2] });
        }

        
        edges.sort((a, b) -> a[2] - b[2]);

       
        UnionFind uf = new UnionFind(n);

        int totalCost = 0;
        int edgesUsed = 0;

        
        for (int[] edge : edges) {
            int u = edge[0], v = edge[1], cost = edge[2];
            if (uf.union(u, v)) {
                totalCost += cost;
                edgesUsed++;
                if (edgesUsed == n)
                    break; 
            }
        }

        return totalCost;
    }

    public static void main(String[] args) {
        
        int n1 = 3;
        int[] modules1 = { 1, 2, 2 };
        int[][] connections1 = { { 1, 2, 1 }, { 2, 3, 1 } };
        System.out.println("Test Case 1:");
        System.out.println("Input: n = 3, modules = [1, 2, 2], connections = [[1, 2, 1], [2, 3, 1]]");
        System.out.println("Output: " + minCostToConnectDevices(n1, modules1, connections1)); 

       
        int n2 = 4;
        int[] modules2 = { 3, 4, 2, 5 };
        int[][] connections2 = { { 1, 2, 2 }, { 2, 3, 3 }, { 3, 4, 1 }, { 1, 4, 4 } };
        System.out.println("\nTest Case 2:");
        System.out.println(
                "Input: n = 4, modules = [3, 4, 2, 5], connections = [[1, 2, 2], [2, 3, 3], [3, 4, 1], [1, 4, 4]]");
        System.out.println("Output: " + minCostToConnectDevices(n2, modules2, connections2));

        int n3 = 5;
        int[] modules3 = { 1, 1, 1, 1, 1 };
        int[][] connections3 = { { 1, 2, 1 }, { 2, 3, 1 }, { 3, 4, 1 }, { 4, 5, 1 } };
        System.out.println("\nTest Case 3:");
        System.out.println(
                "Input: n = 5, modules = [1, 1, 1, 1, 1], connections = [[1, 2, 1], [2, 3, 1], [3, 4, 1], [4, 5, 1]]");
        System.out.println("Output: " + minCostToConnectDevices(n3, modules3, connections3)); 
    }
}