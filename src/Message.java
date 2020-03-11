import java.io.Serializable;

public class Message implements Serializable {
    static final long serialVersionUID = 509L;

    // Fields
    Object data;
    String fromID;
    String fromIP;
    Object toID;

    public Message(Object data, String fromID, String fromIP, Object toID) {
        this.data = data;
        this.fromID = fromID;
        this.fromIP = fromIP;
        this.toID = toID;
    }

    public String toString() {
        return fromID + "@" + fromIP + " to " + toID + ": " + data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getFromID() {
        return fromID;
    }

    public void setFromID(String fromID) {
        this.fromID = fromID;
    }

    public String getFromIP() {
        return fromIP;
    }

    public void setFromIP(String fromIP) {
        this.fromIP = fromIP;
    }

    public Object getToID() {
        return toID;
    }

    public void setToID(Object toID) {
        this.toID = toID;
    }
}
