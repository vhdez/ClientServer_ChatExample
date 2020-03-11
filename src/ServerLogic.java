import java.util.HashMap;

public class ServerLogic implements Runnable {
    // Fields
    QueueFIFO inQ;
    QueueFIFO outQ;
    HashMap allConnections;

    // Constructors
    ServerLogic(QueueFIFO inQ, QueueFIFO outQ, HashMap connections) {
        this.inQ = inQ;
        this.outQ = outQ;
        this.allConnections = connections;
    }

    // Methods
    public void run() {
        while (!Thread.interrupted()) {

            // GET from in
            Object obj = inQ.get();
            while (obj == (Integer)QueueFIFO.NOTAVALUE) {
                Thread.currentThread().yield();
                obj = inQ.get();
            }

            // LOGIC
            Message message = (Message)obj;
            System.out.println("LOGIC: " + obj);
            if (message.toID == MessageValue.SERVERID) {
                // INTERCEPT messages to server
                if (message.getData() == MessageValue.CONNECTDATA) {
                    String clientID = message.fromID;
                    String clientIP = message.fromIP;
                    // save the client's name with its IP
                    ConnectionInfo clientInfo = (ConnectionInfo)allConnections.get(clientIP);
                    if (clientInfo != null) {
                        allConnections.remove(clientIP);
                        allConnections.put(clientID, clientInfo);
                        System.out.println("LOGIC: Client found: " + clientID + ", " + clientIP);
                    } else {
                        System.out.println("SERVER ERROR: Client missing: " + clientID + ", " + clientIP);
                    }
                } else if (message.getData() == MessageValue.DISCONNECTDATA) {
                    // TODO
                }

            } else {
                // PUT to out
                boolean didPutWork = outQ.put(obj);
                while (!didPutWork) {
                    Thread.currentThread().yield();
                    didPutWork = outQ.put(obj);
                }

                System.out.println("LOGIC: did put" + obj);
            }

        }


    }
}
