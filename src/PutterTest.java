public class PutterTest implements Runnable {
    // Fields
    QueueFIFO myQ;
    int startCount;

    // Constructors
    PutterTest(QueueFIFO q, int startAmount) {
        myQ = q;
        startCount = startAmount;
    }

    // Method
    public void run() {
        for (int i = startCount; i < startCount+100; i++) {
            boolean didPutWork = false;
            didPutWork = myQ.put(Thread.currentThread().getName() + " " + i);
            while (!didPutWork) {
                Thread.currentThread().yield();
                didPutWork = myQ.put(Thread.currentThread().getName() + " " + i);
            }

        }

    }
}
