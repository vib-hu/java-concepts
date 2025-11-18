package Concurrency;

import java.beans.IntrospectionException;
import java.util.concurrent.*;

public class MyCallable implements Callable<Integer> {

    @Override
    public Integer call(){
        for(int i=0;i<3;i++){
            System.out.println("Current Thread is "+Thread.currentThread().getName() +" for counter "+i);
            try{
                Thread.sleep(500);
            }catch (InterruptedException ex){
                ex.printStackTrace();
            }
        }
        return 2;
    }

    public static void main(String[] args){
        ExecutorService executor = Executors.newFixedThreadPool(2);
        MyCallable task = new MyCallable();
        Future<Integer> future1 = executor.submit(task);
        Future<Integer> future2 = executor.submit(task);
        executor.shutdown();

        try{
            Integer result1 = future1.get();
            Integer result2 = future2.get();

            System.out.println(result1);
            System.out.println(result2);
        }
        catch (InterruptedException | ExecutionException ex){
            ex.printStackTrace();
        }
    }
}
