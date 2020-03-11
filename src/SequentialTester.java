public class SequentialTester {

    public static void main(String[] args) {
	// write your code here
        QueueFIFO myQ = new QueueFIFO();

        myQ.put("Jack");
        myQ.put("Sal");
        for (int i = 0; i < 95; i++) {
            myQ.put(i);
        }
        myQ.put("Hayden");
        myQ.put("Nicco");
        myQ.put("Ethan");


        System.out.println(myQ.get());
        System.out.println(myQ.get());
        System.out.println(myQ.get());
        System.out.println(myQ.get());
        System.out.println(myQ.get());

        for (int i = 0; i < 45; i++) {
            Object gotObject = myQ.get();
            if (gotObject != (Integer)QueueFIFO.NOTAVALUE) {
                System.out.println(gotObject);
            }
        }


        for (int i = 200; i < 250; i++) {
            myQ.put(i);
        }

        for (int i = 0; i < 100; i++) {
            Object gotObject = myQ.get();
            if (gotObject != (Integer)QueueFIFO.NOTAVALUE) {
                System.out.println(gotObject);
            }
        }

    }
}
