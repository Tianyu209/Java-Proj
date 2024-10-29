package hk.ust.comp3021.lab;

public class Main {
    public static void main(String[] args) {
        SubClass<Integer> objA = new SubClass<Integer>();objA.print("1");
//        SubClass<Object> objB = new SubClass<Integer>();objB.print("1");
        SubClass<Object> objC = new SubClass<Object>();objC.print("1");
//        SubClass<Object> objD = new SubClass<String>();objD.print("1");
        BaseClass objE = new SubClass<String>();objE.print("1");
        BaseClass objF = new SubClass<Integer>();objF.print("1");
//        BaseClass objG = new SubClass<Integer>();objG.print(1);
        BaseClass objH = new SubClass<Object>();objH.print("1");
//        BaseClass objI = new SubClass<Object>();objI.print(1);


    }

    public static int add(int a, int b) {
        return a + b;
    }
}
class BaseClass {
    void print(String s) {
        System.out.println("BaseClass" + s);
    }
}

class SubClass<E> extends BaseClass {
    void print(E e) {
        System.out.println("SubClass" + e);
    }
}

