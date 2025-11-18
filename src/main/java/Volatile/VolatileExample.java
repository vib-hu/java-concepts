package Volatile;

public class VolatileExample {
    public static void main(String[] args){
        try {
            Worker worker = new Worker();
            Thread t1 = new Thread(worker);
            t1.start();
            Thread.sleep(5000);
            worker.stop();
        }catch (InterruptedException ex){
            ex.printStackTrace();
        }
    }
}

class Worker implements Runnable{
   volatile boolean flag = true;
    public void stop(){
        flag = false;
    }

    public void run(){
        while(flag){
            try{
                //If we use println then it will accidentally force visibility
                //Because Printing to console - Performs I/O, Synchronizes internally, Flushes output
                //This forces the thread to refresh memory state from main memory
                //So println() accidentally makes flag visible.
                //Same with Thread.sleep(), When a thread sleeps,
                //The CPU often flushes local caches, When the thread wakes up, it reloads memory
                //So sleep() also accidentally helps visibility.
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
        if(!flag){
            System.out.println("Loop stopped");
        }
    }
}