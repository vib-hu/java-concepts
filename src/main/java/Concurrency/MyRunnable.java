package Concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyRunnable implements Runnable {

    @Override
    public void run(){
        for(int i=0;i<3;i++){
          System.out.println("Thread is running: "+ Thread.currentThread().getName()+" for counter "+i);
          try{
              Thread.sleep(500);
          }
          catch (InterruptedException ex){
              ex.printStackTrace();
          }
        }
    }

    public static void main(String[] args){

        ExecutorService executor = Executors.newFixedThreadPool(2);
        MyRunnable task = new MyRunnable();
        executor.submit(task);
        executor.submit(task);
        executor.shutdown();
    }
}
