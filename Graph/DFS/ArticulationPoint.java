import java.util.ArrayList;

public class ArticulationPoint {
    private int timer = 1;
    private void dfs(int node, int parent, int[] vis, int tin[], int low[], int mark[],ArrayList<ArrayList<Integer>> adj){
        vis[node] = 1;
        tin[node] = low[node] = timer;
        timer++;
        int child = 0;
        for(Integer it: adj.get(node)){
            if(it == parent){
                continue;
            }
            if(vis[it] == 0){
                dfs(it, node, vis, tin, low, mark, adj);
                low[node] = Math.min(low[node], low[it]);
                if(low[it] >= tin[node] && parent != -1){
                    mark[node] = 1;
                }
                child++;
            } else {
                low[node] = Math.min(low[node], tin[it]);
            }
        }
        if(child > 1 && parent == -1){
            mark[node] = 1;
        }
    }
    public int[] articulationPoint(int V, ArrayList<ArrayList<Integer>> adj){
        int[] vis = new int[V];
        int[] tin = new int[V];
        int[] low = new int[V];
        int[] mark = new int[V];
        dfs(0, -1, vis, tin, low, mark, adj);
        int cnt = 0;
        for(int i = 0; i < V; i++){
            if(mark[i] == 1){
                cnt++;
            }
        }
        return new int[]{cnt};
    }
}
