import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.logging.Handler;

public class ServerNewConnection implements Runnable {
    // Fields
    HashMap allConnections;
    QueueFIFO inQ;

    // Constructor
    ServerNewConnection(HashMap myConnections, QueueFIFO myQ) {
        allConnections = myConnections;
        inQ = myQ;
    }

    // Methods
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(155);
            System.out.println("Waiting for a client on port 155....");
            while (!Thread.interrupted()) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Yay!  Connected! to " + clientSocket.getInetAddress().getHostAddress());

                ObjectInputStream dataReader = new ObjectInputStream(clientSocket.getInputStream());
                ObjectOutputStream dataWriter = new ObjectOutputStream(clientSocket.getOutputStream());

                // CommunicationOut is ALREADY running
                // Pass this new connection to CommunicationOut via allConnections
                ConnectionInfo newConnectionInfo = new ConnectionInfo(clientSocket.getInetAddress().getHostAddress(),
                                                                      clientSocket,
                                                                      dataWriter);
                allConnections.put(newConnectionInfo.getInetAddress(), newConnectionInfo);

                CommunicationIn communicationIn = new CommunicationIn(dataReader, inQ);
                Thread communicationInThread = new Thread(communicationIn, "communicationInThread "+newConnectionInfo.getInetAddress());
                communicationInThread.start();

                dataWriter.writeObject("Hi everyone.  I don't talk about falafel");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
