package hk.ust.comp3021;

    public class test extends Thread {
        private String name;
        static Integer count = 0;

        public test(String name) {
            this.name = name;
            count++;
        }

        public void write() {
            System.out.print(name);
            System.out.print(name);
        }

        // Uncomment one of the following implementations to test
//        public void run() {
//             write();
//         }
//         Option C: Synchronized run method (instance level)
//         public synchronized void run() {
//             write();
//         }

        // Option E: Synchronized block on `this`
//         public void run() {
//             synchronized (this) {
//                 write();
//             }
//         }

        // Option F: Synchronized block on static `count`
        public void run() {
            synchronized (count) {
                write();
            }
        }

        public static void main(String[] args) throws InterruptedException {
            Thread A = new test("A");
            Thread B = new test("B");
            Thread.sleep(100);
            A.start();
            B.start();

        }
    }


