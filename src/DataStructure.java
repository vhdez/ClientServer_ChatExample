public interface DataStructure {
    // Fields
    Object[] objects = new Object[100];

    // Methods
    boolean put(Object obj);
    Object get();
}