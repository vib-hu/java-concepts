package CyclicBarrier;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
public class CyclicBarrierExample {

    public static void main(String[] args){
        //CyclicBarrier is reusable
        CyclicBarrier barrier = new CyclicBarrier(5, ()->finalTask());
        for(int i=0;i<5;i++){
            Thread t1 = new Thread(new Worker(barrier));
            t1.start();
        }
    }

    private static void finalTask() {
        System.out.println("Final Task after finishing all workers");
    }
}

 //Suppose you have a large file split into 3 chunks.
 //Three worker threads process their chunk in parallel.
 //Once all chunks are processed, a final merge operation happens.
class Worker implements Runnable{
    private CyclicBarrier barrier;
    public Worker(CyclicBarrier barrier){
        this.barrier = barrier;
    }

    @Override
    public void run(){
        System.out.println("Running from thread: "+Thread.currentThread().getName());
        try{
            Thread.sleep(5000);
            //How it works?
            //When the last thread reaches the barrier (i.e., calls barrier.await()):
            //The barrier is considered broken (reached).
            //finalMergeTask runs immediately (on the thread that reached the barrier last).
            //Only after the barrier action finishes, all waiting threads are released.
            //Then each worker continues with the statements after await().
            barrier.await(); // First barrier - Waiting for all threads to reach at this point
            System.out.println("Barrier completed once. Continuing from Thread: "+Thread.currentThread().getName());

            Thread.sleep(3000);
            barrier.await();  // Second barrier - Waiting for all threads to reach at this point
            System.out.println("Barrier completed twice. Stopping from Thread: "+Thread.currentThread().getName());
        }catch (BrokenBarrierException | InterruptedException ex){
            ex.printStackTrace();
        }
    }
}
