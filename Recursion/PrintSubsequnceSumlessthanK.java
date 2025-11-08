import java.util.*;



public  class PrintSubsequnceSumlessthanK {


/**
 * f[i, [], s]
 *
 *
 */

public static  void printSubsequnceSumlessthanK(int index,int[] arr,int s,ArrayList<Integer> arr1,int k, int n){
    if(index == n){
        if(s == k){
            System.out.print(arr1);
            System.out.println();
        }
        return;
    }

    arr1.add(arr[index]);
    printSubsequnceSumlessthanK(index+1,arr,s+arr[index],arr1,k,n);
    arr1.remove(arr1.size()-1);

    printSubsequnceSumlessthanK(index+1,arr,s,arr1,k,n);
}
public static void main(String[] args){
    Scanner sc = new Scanner(System.in);
    int n = sc.nextInt();
    System.out.println("Enter the array");
    int arr[] = new int[n];
    for(int i=0;i<n;i++){
        arr[i] = sc.nextInt();
    }
    System.out.println("Enter the sum");
    int k = sc.nextInt();
    ArrayList<Integer> arr1 = new ArrayList<>();
    printSubsequnceSumlessthanK(0, arr, 0, arr1, k, n);
}
}