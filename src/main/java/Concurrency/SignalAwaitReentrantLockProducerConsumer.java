package Concurrency;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
import java.util.LinkedList;
import java.util.Queue;

//Signal and Await are part of ReentrantLock
// Alternative is Intrinsic Locks with wait() and notify()/notifyAll()

public class SignalAwaitReentrantLockProducerConsumer {
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition haveSpace = lock.newCondition();
    private final Condition haveItem = lock.newCondition();
    private final Queue<Integer> queue = new LinkedList<>();
    private final int MAX_SIZE = 1;

    public void produce(int value) {
        lock.lock();
        try{
            while(queue.size() == MAX_SIZE){
                System.out.println("Queue full, producer waiting .....");
                haveSpace.await();
            }
            queue.add(value);
            System.out.println("Produced: "+value);
            haveItem.signal();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        finally {
            lock.unlock();
        }
    }

    public void consume(){
        lock.lock();
        try{
            while(queue.isEmpty()){
                System.out.println("Queue is empty, consumer waiting...");
                haveItem.await();
            }
            int item = queue.poll();
            System.out.println("Consumed: "+item);
            haveSpace.signal();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException{
        SignalAwaitReentrantLockProducerConsumer pc = new SignalAwaitReentrantLockProducerConsumer();
        ExecutorService service = Executors.newFixedThreadPool(2);
        service.submit(()->{
            for(int i=0;i<7;i++){
                pc.produce(i);
            }
        });

        service.submit(()->{
            for(int i=0;i<7;i++){
                pc.consume();
            }
        });
        service.awaitTermination(5000, TimeUnit.SECONDS);
        service.shutdown();
    }
 }
