package Atomic;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicExample {
    public static void main(String[] args) throws  InterruptedException{
        Atomic atomic = new Atomic();
        Thread[] threads = new Thread[5000];
        for(int i=0;i<5000;i++){
            threads[i] = new Thread(atomic);
            threads[i].start();
        }
        for(Thread t : threads){
            t.join();  // Wait for all threads to finish
        }
        System.out.println("Number should be 5000");
        System.out.println(atomic.get()); // Sometimes it returns
    }
}

class Atomic implements Runnable{
    AtomicInteger number = new AtomicInteger();
    public void run(){
        number.incrementAndGet();
    }

    public int get(){
        return number.get();
    }
}
