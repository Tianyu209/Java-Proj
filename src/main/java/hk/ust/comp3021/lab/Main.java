package hk.ust.comp3021.lab;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.print(1/0);
        } catch (ArithmeticException e) {
            System.out.print("A");
            throw new RuntimeException(); // This throws RuntimeException
        } catch (RuntimeException re) {
            System.out.print("B"); // This will catch the thrown RuntimeException
        } finally {
            System.out.print("C"); // Always runs
        }
        System.out.print("D"); // Will run if exceptions are caught
    }
}
