public class GetterTest implements Runnable {
    // Fields
    QueueFIFO myQ;

    // Constructors
    GetterTest(QueueFIFO q) {
        myQ = q;
    }

    // Methods
    public void run() {
        for (int i = 0; i < 150; i++) {
            Object obj = myQ.get();
            while (obj == (Integer)QueueFIFO.NOTAVALUE) {
                Thread.currentThread().yield();
                obj = myQ.get();
            }
            System.out.println(Thread.currentThread().getName() + " " + obj);
            try {
                Thread.sleep(100);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }

        System.out.println(Thread.currentThread().getName() + " totalGets=" + myQ.totalGets);
    }

}
