import java.util.ArrayList;
import java.util.List;

class Graph{
    int V;
    List<List<Integer>> adj;

    public Graph(int V){
        adj = new ArrayList<>();
        for(int i=0;i<V;i++){
            adj.add(new ArrayList<>());
        }
    }
    public void addEdge(int u,int v){
        adj.get(u).add(v);
        adj.get(v).add(u);
    }
}
public class NumberOfProvines {
    Graph g = new Graph(5);
    int vis[] = new int[V];
    int count = 0;
    for(int i=0;i<V;i++){
        if(!vis[i]){
            count++;
            dfs(i);
        }
    }
}
