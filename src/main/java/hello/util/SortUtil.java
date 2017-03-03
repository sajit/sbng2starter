package hello.util;

import java.lang.reflect.Array;
import java.util.List;

public class SortUtil {

    private static String[] doQuickSort(String[] strs, int low,int high){
        if(low<high){
            int partitionIdx = partition(strs,low,high);
            
            doQuickSort(strs,low,partitionIdx-1);
            doQuickSort(strs,partitionIdx+1,high);
        }
        return strs;
    }

    private static int partition(String[] arr, int start, int end) {


        int i=start,j=end;
        String pivotval = arr[start];
        while(i<j){
            while(i<end && arr[i].compareTo(pivotval)<=0){
                i++;
            }
            while(j>start && arr[j].compareTo(pivotval)>0){
                j--;
            }
            if(i<j){
                swap(arr,i,j);
            }
        }
        swap(arr,start,j);
        //printArr(arr);
        return j;
    }

    private static void printArr(String[] arr) {
        for(int i=0;i<arr.length;i++){
            System.out.print(arr[i]+",");
        }
        System.out.println();
    }


    private static void swap(String[] strs, int a, int b) {
        String temp = strs[a];
        strs[a] = strs[b];
        strs[b] = temp;
    }

    public static String[] quickSort(String[] list){
        return doQuickSort(list,0,list.length-1);
    }
}
