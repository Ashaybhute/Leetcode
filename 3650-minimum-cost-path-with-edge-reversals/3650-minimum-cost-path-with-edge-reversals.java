import java.util.*;

class Solution {
    public int minCost(int n, int[][] edges) {
        
        List<int[]>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }
        
        
        for (int[] e : edges) {
            int u = e[0], v = e[1], w = e[2];
            
            graph[u].add(new int[]{v, w});
            
            graph[v].add(new int[]{u, 2 * w});
        }
        
        
        final int INF = Integer.MAX_VALUE / 2;
        int[] dist = new int[n];
        Arrays.fill(dist, INF);
        dist[0] = 0;
        
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        pq.add(new int[]{0, 0}); 
        
        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int cost = cur[0];
            int node = cur[1];
            
            if (cost > dist[node]) continue;
            if (node == n - 1) return cost;
            
            for (int[] nxt : graph[node]) {
                int nei = nxt[0], w = nxt[1];
                int newCost = cost + w;
                if (newCost < dist[nei]) {
                    dist[nei] = newCost;
                    pq.add(new int[]{newCost, nei});
                }
            }
        }
        
        return dist[n - 1] == INF ? -1 : dist[n - 1];
    }
}
