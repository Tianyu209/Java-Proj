package hk.ust.comp3021.lab;

public class Main {
    public static int add(int a, int b) {
        return a + b;
    }
    public static void windowPosSum(int[] a, int n) {
        /** your code here */
        for(int i=0;i<a.length;i++){
            int res=0;
            if(a[i]<0) continue;
            for(int j=i;j<a.length && j<i+n+1;j++){
                res += a[j];
            }
            a[i] = res;
        }

    }

    public static void main(String[] args) {

    }
}