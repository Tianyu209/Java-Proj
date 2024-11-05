package hk.ust.comp3021.lab;

interface M {
    int compute(int x);
}
public class MyClass{
    public static void main(String[] args){
        M i = new M(){
            public int compute(int x){return x*x;}};
        System.out.println(i.compute(10));
    }
}
