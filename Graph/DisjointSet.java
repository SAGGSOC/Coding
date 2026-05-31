import java.util.*;
class DisjointSet{

    List<Integer> rank = new ArrayList<>();
    List<Integer> parent = new ArrayList<>();
    List<Integer> size = new ArrayList<>();
    DisjointSet(int n){
        rank = new ArrayList<>();
        parent = new ArrayList<>();
        size = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            rank.add(0);
            parent.add(i);
            size.add(1);
        }
    }

    public int findUlpParent(int node){
        if(node == parent.get(node)){
            return node;
        }
        int ulp = findUlpParent(parent.get(node));
        parent.set(node , ulp);
        return parent.get(node);
    }

    public void unionByRank(int u, int v){
        int ulpU = findUlpParent(u);
        int ulpV = findUlpParent(v);

        if(ulpU == ulpV){
            return;
        }
        if(rank.get(ulpV) < rank.get(ulpU)){
            parent.set(ulpV, ulpU);
        } else if(rank.get(ulpU) < rank.get(ulpV)){
            parent.set(ulpU, ulpV);
        } else {
            parent.set(ulpV, ulpU);
            rank.set(ulpU, rank.get(ulpU)+1);
        }
    }
    public void unionBySize(int u, int v){
        int ulpU = findUlpParent(u);
        int ulpV = findUlpParent(v);

        if(ulpU == ulpV){
            return;
        }
        if(size.get(ulpV) > size.get(ulpU)){
            parent.set(ulpU, ulpV);
            size.set(ulpV, size.get(ulpU)+size.get(ulpV));
        } else {
            size.set(ulpU, size.get(ulpU)+size.get(ulpV));
        }
    }


}