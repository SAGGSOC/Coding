import java.util.ArrayList;
import java.util.List;

class Edge implements Comparable<Edge> {
    int src, des, wt;
    public int compareTo(Edge edge) {
        return this.wt - edge.wt;
    }
    public Edge(int src, int des, int wt) {
        this.src = src;
        this.des = des;
        this.wt = wt;
    }
}
public class Kruskal {
    static int spanningTree(int V,ArrayList<ArrayList<ArrayList<Integer>>> adj){
        List<Edge> edges = new ArrayList<>();
        for(int i=0;i<V;i++){
            for(int j=0;j<adj.get(i).size();j++){
                int adjNode = adj.get(i).get(j).get(0);
                int wt = adj.get(i).get(j).get(1);
                int node = i;
                edges.add(new Edge(node, adjNode, wt));
            }
        }
        DisjointSet ds = new DisjointSet(V);
        Collections.sort(edges);
        int mstWt;
        for(int i=0;i<edges.size();i++){
            Edge edge = edges.get(i);
            if(ds.findPar(edges.get(i).src) != ds.findPar(edges.get(i).des)){
                mstWt += edges.get(i).wt;
                ds.unionBySize(edges.get(i).src, edges.get(i).des);
            }
        }
    }
}
