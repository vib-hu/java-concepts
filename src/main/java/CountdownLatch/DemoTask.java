package CountdownLatch;

public class DemoTask implements Runnable {
    @Override
    public void run(){
        System.out.println("Name is :- "+Thread.currentThread().getName());
    }
}
