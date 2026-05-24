import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
class Pair{
    int first, second;
    public Pair(int first, int second){
        this.first = first;
        this.second = second;
    }
}
public class MinMultToReachEnd {
    static int countPaths(int n, List<List<Integer>> nodes){
        ArrayList<ArrayList<Pair>> adj = new ArrayList<>();
        for(int i=0;i<n;i++){
            adj.add(new ArrayList<>());
        }
        for(int i=0;i<m;i++){
            adj.get(nodes.get(i).get(0)).add(new Pair(nodes.get(i).get(1), nodes.get(i).get(2)));
            adj.get(nodes.get(i).get(1)).add(new Pair(nodes.get(i).get(0), nodes.get(i).get(2)));
        }
        PriorityQueue<Pair> pq = new PriorityQueue<>((x, y) -> y.first - x.first);
        int[] dist = new int[n];
        int[] ways = new int[n];
        for(int i=0;i<n;i++){
            dist[i] = (int)1e9;
            ways[i] = 0;
        }
        dist[0] = 0;
        ways[0] = 1;
        pq.add(new Pair(0, 0));
        int mod = (int)(1e9 + 7);
        while(pq.size() != 0){
            int dis = pq.peek().first;
            int node = pq.peek().second;
            pq.remove();

            for(Pair it:adj.get(node)){
                int adjNode = it.first;
                int edW = it.second;
                if(dis + edW < dist[adjNode]){
                    dist[adjNode] = dis + edW;
                    pq.add(new Pair(dist[adjNode], adjNode));
                    ways[adjNode] = ways[node];
                }
                else if(dis + edW == dist[adjNode]){
                    ways[adjNode] = (ways[adjNode] + ways[node]) % mod;
                }
            }
        }

        return ways[n-1] % mod;
    }
}
