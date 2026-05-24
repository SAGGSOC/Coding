import java.util.*;

class Permutations{
    public static void generatePermutations(String str, int i, HashSet<String> set, int[] vis){
        // use visited or swap
        /*
           a  b  c 
           0  0  0

           pick a and swap with b and then swap with c   bac and cab.       abc, acb, cba, cab, bca , bac
           pick b and then swap with a and swap with c.  bac and acb   
           pick c then swap with a and then swap with b.  cba. and acb 
        */
        if(i == str.length()){
            return;
        }

        if(vis[i] == )
    }
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the String");

        String str = sc.next();
        int n = str.length();
        
        int vis[] = new int[n];
        Arrays.fill(vis, 0);
        HashSet<String> set = new  HashSet<>();
        generatePermutations(str, 0, set, vis);
    }
}