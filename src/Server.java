import java.util.HashMap;

public class Server {
    public static void main(String[] args) {
        // Store all connections here!
        HashMap<String, ConnectionInfo> allConnections = new HashMap<String, ConnectionInfo>();

        QueueFIFO myInputQueue = new QueueFIFO();
        QueueFIFO myOutputQueue = new QueueFIFO();

        CommunicationOut communicationOut = new CommunicationOut(allConnections, myOutputQueue, true);
        Thread communicationOutThread = new Thread(communicationOut, "communicationOut");
        communicationOutThread.start();

        ServerLogic myLogic = new ServerLogic(myInputQueue, myOutputQueue, allConnections);
        Thread logicThread = new Thread(myLogic);
        logicThread.start();

        ServerNewConnection myConnectionThread = new ServerNewConnection(allConnections, myInputQueue);
        Thread serverNewConnectionThread = new Thread(myConnectionThread, "serverConnectionThread");
        serverNewConnectionThread.start();

        System.out.println("Server is running!");
    }
}
