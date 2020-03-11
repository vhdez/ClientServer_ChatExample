public class ConcurrentTester {
    public static void main(String[] args) {
        QueueFIFO q= new QueueFIFO();

        PutterTest putter1 = new PutterTest(q, 0);
        Thread putterThread1 = new Thread(putter1, "putterThread1");
        putterThread1.start();

        PutterTest putter2 = new PutterTest(q, 100);
        Thread putterThread2 = new Thread(putter2, "putterThread2");
        putterThread2.start();

        PutterTest putter3 = new PutterTest(q, 200);
        Thread putterThread3 = new Thread(putter3, "putterThread3");
        putterThread3.start();

        try {
            Thread.sleep(10000);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        GetterTest getter1 = new GetterTest(q);
        Thread getterThread1 = new Thread(getter1, "getterThread1");
        getterThread1.start();

        GetterTest getter2 = new GetterTest(q);
        Thread getterThread2 = new Thread(getter2, "getterThread2");
        getterThread2.start();



    }
}
