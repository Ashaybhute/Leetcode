import java.util.*;

class Solution {

    public long minimumCost(
            String source,
            String target,
            String[] original,
            String[] changed,
            int[] cost
    ) {
        int n = source.length();
        if (n != target.length()) return -1;

        // Collect all unique strings
        Set<String> set = new HashSet<>();
        for (String s : original) set.add(s);
        for (String s : changed) set.add(s);
        for (char c : source.toCharArray()) set.add(String.valueOf(c));
        for (char c : target.toCharArray()) set.add(String.valueOf(c));

        List<String> list = new ArrayList<>(set);
        int m = list.size();

        Map<String, Integer> id = new HashMap<>();
        for (int i = 0; i < m; i++) {
            id.put(list.get(i), i);
        }

        long INF = (long) 1e18;
        long[][] dist = new long[m][m];

        for (int i = 0; i < m; i++) {
            Arrays.fill(dist[i], INF);
            dist[i][i] = 0;
        }

        // Direct costs
        for (int i = 0; i < original.length; i++) {
            int u = id.get(original[i]);
            int v = id.get(changed[i]);
            dist[u][v] = Math.min(dist[u][v], cost[i]);
        }

        // Floyd–Warshall
        for (int k = 0; k < m; k++) {
            for (int i = 0; i < m; i++) {
                if (dist[i][k] == INF) continue;
                for (int j = 0; j < m; j++) {
                    if (dist[k][j] == INF) continue;
                    dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                }
            }
        }

        // Group conversion indices by substring length
        Map<Integer, List<Integer>> byLen = new HashMap<>();
        for (int i = 0; i < original.length; i++) {
            int len = original[i].length();
            byLen.computeIfAbsent(len, k -> new ArrayList<>()).add(i);
        }

        // DP
        long[] dp = new long[n + 1];
        Arrays.fill(dp, INF);
        dp[n] = 0;

        for (int i = n - 1; i >= 0; i--) {

            // Try single character match (no cost)
            if (source.charAt(i) == target.charAt(i)) {
                dp[i] = dp[i + 1];
            }

            // Try only valid lengths
            for (int len : byLen.keySet()) {
                if (i + len > n) continue;

                String s1 = source.substring(i, i + len);
                String s2 = target.substring(i, i + len);

                Integer u = id.get(s1);
                Integer v = id.get(s2);
                if (u == null || v == null) continue;

                long c = dist[u][v];
                if (c == INF) continue;

                dp[i] = Math.min(dp[i], c + dp[i + len]);
            }
        }

        return dp[0] >= INF ? -1 : dp[0];
    }
}
