import java.util.ArrayList;

class DisjointSet{
    int parent[];
    int rank[];
    int size[];

    public DisjointSet(int V){

        parent = new int[V];

        rank = new int[V];

        size = new int[V];

        for(int i=0;i<V;i++){

            parent[i] = i;

            rank[i] = 0;

            size[i] = 1;

        }

    }
    int findUlpParent(int node){
        if(node == parent[node]){
            return node;
        }
        int ulp = findUlpParent(node);
        parent[node] = ulp;
        return parent[node];
    }
}
public class NumberOfIslands {
    public List<Integer> numberOfIslands(int n, int m, int operators[][]){
        DisjointSet ds = new DisjointSet(n*m);
        int[][] vis = new int[n][m];

        int cnt = 0;
        List<Integer> ans = new ArrayList<>();
        int len = operators.length;
        for(int i=0;i<len;i++){
            int row = operators[i][0];
            int col = operators[i][1];

            if(vis[row][col] == 1){
                ans.add(cnt);
                continue;
            }
            vis[row][col] = 1;
            cnt++;
            int dr[] = {-1, 0, 1, 0};
            int dc[] = {0, -1, 0, 1};

            for(int ind=0;ind<4;ind++){
                int adjr = row + dr[ind];
                int adjc = col + dc[ind];
                if(vis[adjr][adjr] == 1){
                    int nodeNo = row*m + col;
                    int adjNo = adjr*m + adjc;
                    if(ds.findUlpParent(nodeNo) != ds.findUlpParent(adjNo)){
                        ds.unionBySize(nodeNo, adjNo);
                        cnt--;
                    }
                }
            }
        }
        ans.add(cnt);
        return ans;
    }
    
}
