import java.io.EOFException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.WeakHashMap;

public class Client {
    public void main(String[] args) {
        try {
            Thread.currentThread().setName("Main");

            Socket mySock = new Socket("127.0.0.1", 155);
            System.out.println("Yay!  I connected to server " + mySock.toString());

            ObjectOutputStream dataWriter = new ObjectOutputStream(mySock.getOutputStream());
            ObjectInputStream dataReader = new ObjectInputStream(mySock.getInputStream());

            // Handshake with server
            Message connectMsg = new Message(MessageValue.CONNECTDATA, "Mr. Hernandez", InetAddress.getLocalHost().getHostAddress(), MessageValue.SERVERID);
            dataWriter.writeObject(connectMsg);
            dataWriter.flush();

            QueueFIFO inQ = new QueueFIFO();
            CommunicationIn communicationIn = new CommunicationIn(dataReader, inQ);
            Thread communicationInThread = new Thread(communicationIn, "communicationIn");
            communicationInThread.start();

            QueueFIFO outQ = new QueueFIFO();
            ConnectionInfo serverConnection = new ConnectionInfo(mySock.getInetAddress().toString(), mySock, dataWriter);
            CommunicationOut communicationOut = new CommunicationOut(serverConnection, outQ);
            Thread communicationOutThread = new Thread(communicationOut, "communicationOut");
            communicationOutThread.start();

            Message helloMsg = new Message("Hi from Mr. Hernandez", "Mr. Hernandez", InetAddress.getLocalHost().getHostAddress(), "ALL");
            boolean didPutWork = false;
            didPutWork = outQ.put(helloMsg);
            while (!didPutWork) {
                Thread.currentThread().yield();
                didPutWork = outQ.put(helloMsg);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
