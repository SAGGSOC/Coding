class DisjointSet{
    int parent[];
    int rank[];
    int size[];
    public DisjointSet(int V){
        rank = new int[V];
        parent = new int[V];
        size = new int[V];
        for(int i=0;i<V;i++){
            parent[i] = i;
            rank[i] = 0;
            size[i] = 1;
        }
    }
    int findUlpParent(int u){
        if(parent[u] == u)
             return u;
        int ulP  = findUlpParent(u);
        parent[u] = ulP;
        return parent[u];    
    }
    void unionByRank(int u, int v){
        int ulPU = findUlpParent(u);
        int ulPV = findUlpParent(v);
        if(ulPU == ulPV)
            return;
        if(rank[ulPU] < rank[ulPV]){
            parent[ulPU] = ulPV;
        } else if(rank[ulPU] > rank[ulPV]){
            parent[ulPV] = ulPU;
        } else {
            parent[ulPV] = ulPU;
            rank[ulPU] += 1;
        }
    }
    void unionBySize(int u, int v){
        int ulpU = findUlpParent(u);
        int ulpV = findUlpParent(v);

        if(ulpU == ulpV){
            return;
        }
        if(size[ulpV] > size[ulpU]){
            parent[ulpU] = ulpV;
            size[ulpV] = size.get[ulpU]+size.get[ulpV];
        } else {
            size[ulpU] = size[ulpU]+size[ulpV];
        }
    }
}
public class MinOpToMakeGraphConnected {
    public int solve(int n, int[][] edges){
        DisjointSet ds = new DisjointSet(n);
        int cntExtras = 0;
        int m = edges.length;
        for(int i=0;i<m;i++){
            int u = edges[i][0];
            int v = edges[i][1];
            if(ds.findUlpParent(u) == ds.findUlpParent(v)){
                cntExtras++;
            } else {
                ds.unionBySize(u, v);
            }
        }
        int cntC = 0;
        for(int i=0;i<n;i++){
            if(ds.parent[i] == i)
                cntC++;
        }
        int ans = cntC - 1;
        if(cntExtras>= ans)
            return ans;
        return -1;

    }
}
