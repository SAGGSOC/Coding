import java.util.HashSet;

public class MakingALargeIsland {
    int maxConnections(int grid[][]){
        int n =grid.length;
        DisjointSet ds = new DisjointSet(n*n);

        int dr[] = {-1,0,1,0};
        int dc[] = {0,-1,0,1};

        for(int row = 0;row < n;row++){
            for(int col = 0;col<n;col++){
                if(grid[row][col] == 1){
                    for(int ind=0;ind<4;ind++){
                        int newr = row+dr[ind];
                        int newc = col+dc[ind];
                        if(isValid(newr,newc,n) && grid[newr][newc] == 1){
                            int nodeNo = row*n+col;
                            int adjNodeNo = newr*n+newc;
                            ds.unionBySize(nodeNo,adjNodeNo);
                        }
                    }
                }
            }
        }

        int mx = 0;
        for(int row =0;row<n;row++){
            for(int col=0;col<n;col++){
                if(grid[row][col] == 1)
                    continue;
                HashSet<Integer> components = new HashSet<>();
                for(int ind = 0;ind<4;ind++){
                    int newr = row + dr[ind];
                    int newc = col + dc[ind];

                    if(isValid(newr, newc, n)){

                        if(grid[newr][newc] == 1){
                            components.add(ds.findUPar(newr*n+newc));
                        }

                    }
                }
                int sizeTotal = 0;
                for(Integer parents : components){
                    sizeTotal +=  ds.size.get(parents);
                }

                mx = Math.max(mx, sizeTotal+1);
            }
        }
        for(int cellNo = 0;cellNo<n*n;cellNo++){

            mx = Math.max(mx, ds.size.get(ds.findUPar(cellNo)));
        }

        return mx;
    }

    private boolean isValid(int row, int col, int n){
        return row >= 0 && row < n && col >= 0 && col < n;
    }
}
