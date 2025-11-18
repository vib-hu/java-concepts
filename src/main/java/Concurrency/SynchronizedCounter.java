package Concurrency;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SynchronizedCounter {
    private int count =0;

    public synchronized void increment(){
        count = count + 1;
        System.out.println(Thread.currentThread().getName()+" - "+count);
    }

    public synchronized int getValue(){
        return count;
    }

    public static void main(String[] args) throws InterruptedException {
        SynchronizedCounter counter = new SynchronizedCounter();

        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.submit(()->{
           for(int i=0;i<10;i++){
               counter.increment();
           }
        });
        executor.submit(()->{
            for(int i=0;i<10;i++){
                counter.increment();
            }
        });
        executor.shutdown();
        executor.awaitTermination(5000, TimeUnit.SECONDS);
        System.out.println("Final value:- "+counter.getValue());
    }
}
