import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConnectionInfo {
    // Fields
    String inetAddress;
    Socket socket;
    ObjectOutputStream dataWriter;

    // Constructors
    public ConnectionInfo(String inetAddress, Socket socket, ObjectOutputStream dataWriter) {
        this.inetAddress = inetAddress;
        this.socket = socket;
        this.dataWriter = dataWriter;
    }

    // Methods
    public String getInetAddress() {
        return inetAddress;
    }

    public void setInetAddress(String inetAddress) {
        this.inetAddress = inetAddress;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public ObjectOutputStream getDataWriter() {
        return dataWriter;
    }

    public void setDataWriter(ObjectOutputStream dataWriter) {
        this.dataWriter = dataWriter;
    }
}
