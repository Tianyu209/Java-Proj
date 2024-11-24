package hk.ust.comp3021.lab;

public class Dog{
    public int weightInPound;
    public static String name = "doggy";
    public Dog(int x ){weightInPound = x;}
    public void makeNoise(){
        if(weightInPound <10) System.out.println("aaaa");
        else if(weightInPound>20) System.out.println("bbb");
        else System.out.println("Bark!");
    }
    public static Dog maxDog(Dog d1,Dog d2){
        if(d1.weightInPound>d2.weightInPound) return d1;
        return d2;
    }
    public void test(){
        System.out.print(((Integer)1).toString());

    }
    public static void main(String[] args){
        Dog a = new Dog(9);
    }
}


