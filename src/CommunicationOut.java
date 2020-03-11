import java.util.HashMap;
import java.util.logging.Handler;

public class CommunicationOut implements Runnable {
    // Fields
    // SERVER-only
    HashMap<String, ConnectionInfo> allConnections;
    // CLIENT-only
    ConnectionInfo serverConnection;
    QueueFIFO outQ;
    boolean isServer;

    // Constructors
    // SERVER-only
    CommunicationOut(HashMap connections, QueueFIFO myQ, boolean serverMode) {
        allConnections = connections;
        outQ = myQ;
        isServer = serverMode;
    }

    // CLIENT-only
    CommunicationOut(ConnectionInfo server, QueueFIFO myQ) {
        serverConnection = server;
        allConnections = null;
        outQ = myQ;
        isServer = false;
    }

    // Methods
    @Override
    public void run() {
        while (!Thread.interrupted()) {
            Object latestData = outQ.get();
            while (latestData == (Integer) QueueFIFO.NOTAVALUE) {
                Thread.yield();
                latestData = outQ.get();
            }

            System.out.println(Thread.currentThread().getName() + ":  " + latestData);

            Message dataToSend = (Message) latestData;
            ConnectionInfo target;
            if (isServer) {
                target= allConnections.get(dataToSend.toID);
            } else {
                target = serverConnection;
            }
            if (target != null) {
                // individual target
                try {
                    target.getDataWriter().writeObject(dataToSend);
                    target.getDataWriter().flush();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                if (!isServer) {
                    System.out.println("ERROR: client trying to use allConnections!!! BAD!!!!");
                }
                // all target
                allConnections.forEach((id, connectionInfo) -> {
                    try {
                        if (!connectionInfo.getInetAddress().equalsIgnoreCase(dataToSend.getFromIP())) {
                            connectionInfo.getDataWriter().writeObject(dataToSend);
                            connectionInfo.getDataWriter().flush();
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });
            }
        }
    }
}
