package Semaphore;

import java.util.ArrayDeque;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

public class SemaphoreExample {

    public static void main(String[] args){
        invokeParkingLot();
        invokeConnectionPool();
    }

    public static void invokeParkingLot(){
        ParkingLot parkingLot = new ParkingLot();
        for(int i=0;i<6;i++){
            String carName = "Car" + i;
            new Thread(() -> {
                parkingLot.parking(carName);
                try {
                    // Simulate car parked for some time
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                parkingLot.leaving(carName);
            }).start();
        }
    }

    public static void invokeConnectionPool() {
        ConnectionPool pool = new ConnectionPool(2);
        for(int i=0;i<10;i++){
            Thread t1 = new Thread(()->{
                try{
                   Connection connection = pool.acquire();
                   connection.execute("Select 1");
                   Thread.sleep(2000);
                   pool.release(connection);
                }catch (InterruptedException ex){
                    ex.printStackTrace();
                }
            });
            t1.start();
        }
    }
}

class ParkingLot{
    private final Semaphore slots = new Semaphore(2);

    public void parking(String car){
        try{
            slots.acquire();
            System.out.println("Parking Car "+car);
        }catch (InterruptedException ex){
            ex.printStackTrace();
        }
    }

    public void leaving(String car){
        try {
            System.out.println("Leaving Car " + car);
            //Never call release() before acquire() — otherwise
            // semaphore permits can exceed the initial limit.
            //Keep parking and leaving in the same thread (one logical operation).
            //Semaphore limits concurrent threads, but the logic must respect the lifecycle of the resource.
            slots.release();
            Thread.sleep(1000);
        }catch (InterruptedException ex){
            ex.printStackTrace();
        }
    }
}

class ConnectionPool {
    private Semaphore semaphore;
    private ArrayDeque<Connection> connections = new ArrayDeque<>();

    public ConnectionPool(int size) {
        for (int i = 0; i < size; i++) {
            connections.add(new Connection("Connection Id:" + i));
        }
        semaphore = new Semaphore(size);
    }

    public Connection acquire() throws InterruptedException {
        // This provides Waits until a slot is available
        //If no permits are available, the thread is blocked (goes to WAITING state)
        // until another thread calls semaphore.release().
        //it waits, but in a non-busy way (no CPU spinning).
        //acquire() - Waits, tryAcquire() - No waiting, tryAcquire(timeout, unit) - Only waits for set time
        semaphore.acquire();

        //Only one thread can execute a critical section at a time.
        //Race conditions can corrupt ArrayDeque
        synchronized (connections) {
            Connection connection = connections.poll();
            System.out.println("Connection acquired. Name: " + connection.name+" Thread Name: "+Thread.currentThread().getName());
            return connection;
        }
    }

    public void release(Connection connection) throws InterruptedException {
        synchronized (connections) {
            System.out.println("Connection released: " + connection.name);
            connections.add(connection);
        }
        semaphore.release();
    }
}

class Connection{
    public final String name;

    public Connection(String name){
        this.name = name;
    }

    public void execute(String query){
        System.out.println(name+" executing "+query);
    }
}
