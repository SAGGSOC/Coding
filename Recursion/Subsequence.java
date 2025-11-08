package Recursion;

import java.util.*;


/**
 *
 *  [3, 1, 2]
 *  {} Empty
 *  3
 *  1
 *  2
 *  3 1
 *  1 2
 *  3 2
 *  3 1 2
 *
 *  Order has to maintained
 *
 *  This will be done using Recursion
 *
 *  take/ notTake Approach
 *
 *  3 _ 2
 *
 *  _ 1 2
 *
 *  [].add(arr[i]);
 *  f(ind + 1, []); take
 *  [].remove(arr[i]);
 *  f(ind,[])
 */
public class Subsequence {

    static void f(int ind, int arr[], ArrayList<Integer> arr1, int n){
        if(ind >= n) {
            System.out.println(arr1);
            return;
        }
        arr1.add(arr[ind]);
        f(ind + 1, arr, arr1, n);
        arr1.remove(arr1.size() - 1);

        f(ind + 1, arr, arr1, n);
    }
    public  static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        System.out.println("Enter the array");
        int arr[] = new int[n];
        for(int i=0;i<n;i++){
            arr[i] = sc.nextInt();
        }
        ArrayList<Integer> arr1 = new ArrayList<>();
        f(0, arr, arr1, n);
        f(0, arr, arr1, n);
    }
}