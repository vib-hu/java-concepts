package Practice;

import java.util.ArrayDeque;
import java.util.concurrent.Semaphore;

public class Practice {

    public static void main(String[] args){
        SemaphoreExample semaphoreExample = new SemaphoreExample();
        for(int i=0;i<100;i++){
            new Thread(()->{
                try{
                    semaphoreExample.increase();
                }catch (InterruptedException ex){
                    ex.printStackTrace();
                }
            }).start();
        }
        System.out.println("Final value:"+semaphoreExample.counter);
    }
}

class SemaphoreExample{
    Semaphore semaphore = new Semaphore(1);
    int counter = 0;

    public void increase() throws InterruptedException{
        semaphore.acquire();
        Thread.sleep(5000);
        System.out.println("Increasing to "+counter+1);
        counter++;
        semaphore.release();
    }
}