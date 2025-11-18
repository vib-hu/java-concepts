package CountdownLatch;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.CountDownLatch;

public class CountdownLatch {

    public static void main(String[] args){
        //using executor
//        CountdownLatchWithExecutor executor = new CountdownLatchWithExecutor();
//        executor.execute();

        //Using simple thread
        CountdownLatchWithThread  latchWithThread = new CountdownLatchWithThread();
        latchWithThread.execute();
    }
}

class CountdownLatchWithExecutor{
    public void execute(){
        ExecutorService service = Executors.newFixedThreadPool(3);
        DemoTask task1 = new DemoTask();
        DemoTask task2 = new DemoTask();
        DemoTask task3 = new DemoTask();
        service.submit(task1);
        service.submit(task2);
        service.submit(task3);

        service.shutdown();
        try{
            service.awaitTermination(500, TimeUnit.SECONDS);
        }
        catch (Exception ex){
            System.out.println("exception while await termination");
        }
    }
}

class CountdownLatchWithThread{
    public void execute(){
       CountDownLatch latch = new CountDownLatch(3);
       for(int i=0;i<3;i++){
           int id =i;
           new Thread(()->{
               try{
                   System.out.println("Service " + id + " initialized");
                   Thread.sleep(1000);
                   //main thread waits for all threads to complete
                   //One-time use (cannot be reset)
                   //Example analogy: Race start gun – wait until countdown reaches zero
                   // Used when one thread depends on others.
                   //Like: “Do not start until N things are done.”
                   //➡️ Main waits. Workers signal.
                   latch.countDown();
               }
               catch (InterruptedException ex){
                   ex.printStackTrace();
               }
           }).start();
       }
       try{
           latch.await();
           System.out.println("All threads are completed");
       }catch (InterruptedException ex){
           ex.printStackTrace();
       }

    }
}
