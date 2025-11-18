package ReentrantLock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockExample{
    public static void main(String[] args){
        ReadWriteLock readWriteLock = new ReadWriteLock();
        Thread t1 = new Thread(()->readWriteLock.write(5));
        Thread t2 = new Thread(()->readWriteLock.read());
        Thread t3 = new Thread(()->readWriteLock.read());
        Thread t4 = new Thread(()->readWriteLock.read());

        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
}

class ReadWriteLock {
    int data = 0;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public void write(int num){
        lock.writeLock().lock();
        try{
            System.out.println(Thread.currentThread().getName()+" thread started writing");
            Thread.sleep(3000);
            data = num;
            System.out.println(Thread.currentThread().getName()+" thread completed writing");
        }
        catch (InterruptedException ex){
            ex.printStackTrace();
        }
        finally {
            lock.writeLock().unlock();
        }
    }

    public void read(){
        //For reading shared data, Multiple readers, Blocks writers only
        //Readers can work in parallel, but writers must wait until readers are done.
        // Without readLock(), A reader might read half-written data and
        // No synchronization ensures visibility between threads (so one might see stale values)
        lock.readLock().lock();
        try{
            System.out.println(Thread.currentThread().getName()+" thread started reading");
            System.out.println("Data is:"+data);
            Thread.sleep(3000);
            System.out.println(Thread.currentThread().getName()+" thread completed reading");
        }
        catch (InterruptedException ex){
            ex.printStackTrace();
        }
        finally {
            lock.readLock().unlock();
        }
    }

}
