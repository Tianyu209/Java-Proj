package hk.ust.comp3021.lab;

public class TestCode {
    public static void main(String[] args) {
        // Line A
//        try {
//            System.out.print(1.toString());
//        } catch (Exception e) {
//            System.out.println("Line A: " + e);
//        }

        // Line B
//        try {
//            System.out.print('1'.toString());
//        } catch (Exception e) {
//            System.out.println("Line B: " + e);
//        }

        // Line C
        try {
            System.out.print("1".toString());
        } catch (Exception e) {
            System.out.println("Line C: " + e);
        }

        // Line D
        try {
            System.out.print(((Integer) 1).toString());
        } catch (Exception e) {
            System.out.println("Line D: " + e);
        }

        // Line E
//        try {
//            System.out.print(Integer(1).toString());
//        } catch (Exception e) {
//            System.out.println("Line E: " + e);
//        }

        // Line F
        try {
            System.out.print(((Character) (char) 1).toString());
        } catch (Exception e) {
            System.out.println("Line F: " + e);
        }

        // Line G
        try {
            System.out.print((1 + "").toString());
        } catch (Exception e) {
            System.out.println("Line G: " + e);
        }
//        try {
//            System.out.print((1 + '').toString());
//        } catch (Exception e) {
//            System.out.println("Line H: " + e);
//        }
    }
}
