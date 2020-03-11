public class QueueFIFO implements DataStructure {
    // Fields
    Object[] objects;
    int putIndex;
    int getIndex;
    int objectCount;
    static int NOTAVALUE = -5;
    int totalGets;

    // Constructors
    QueueFIFO() {
        objects = new Object[100];
        putIndex = 0;
        getIndex = 0;
        objectCount = 0;
        totalGets = 0;
    }

    // Methods
    synchronized public boolean put(Object obj) {
        if (objectCount > 99) {
            return false;
        }

        objects[putIndex] = obj;
        objectCount = objectCount + 1;
        if (putIndex < 99) {
            putIndex = putIndex + 1;
        } else {
            putIndex = 0;
        }

        return true;
    }

    synchronized public Object get() {
        if (objectCount < 1) {
            return QueueFIFO.NOTAVALUE;
        }

        Object returnObject = objects[getIndex];
        objectCount = objectCount - 1;
        if (getIndex < 99) {
            getIndex = getIndex + 1;
        } else {
            getIndex = 0;
        }

        totalGets = totalGets + 1;
        return returnObject;
    }
}
