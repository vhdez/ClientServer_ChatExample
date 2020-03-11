import java.io.EOFException;
import java.io.ObjectInputStream;
import java.nio.charset.IllegalCharsetNameException;

public class CommunicationIn implements Runnable {
    // Fields
    ObjectInputStream dataReader;
    QueueFIFO inQ;

    // Constructors
    CommunicationIn(ObjectInputStream in, QueueFIFO myQ) {
        dataReader = in;
        inQ = myQ;
    }

    // Methods

    public void run() {
        while (!Thread.interrupted()) {
            try {
                // READ
                Object data = dataReader.readObject();
                while (data == null) {
                    data = dataReader.readObject();
                }

                // REPORT
                System.out.println(Thread.currentThread().getName() + ": " + data);
                if (data instanceof Message) {

                    // THEN PUT
                    boolean didPutWork = inQ.put(data);
                    while (!didPutWork) {
                        Thread.currentThread().yield();
                        didPutWork = inQ.put(data);
                    }
                }

            } catch (EOFException eof) {
                // System.out.println("someone stopped sending me data!!!");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }
}
