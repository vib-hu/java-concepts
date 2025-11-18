package ReentrantLock;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockExample{
    public static void main(String[] args){
        BankAccount account = new BankAccount();
        Thread t1 = new Thread(()-> account.withdraw(100));
        Thread t2 = new Thread(()-> account.withdraw(20));

        t1.start();
        t2.start();
    }
}


class BankAccount {
    private int balance = 1000;
    private final Lock lock = new ReentrantLock();
    public void withdraw(int amount) {
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + " trying to withdraw amount: " + amount);
            Thread.sleep(5000);
            balance -= amount;
            System.out.println(Thread.currentThread().getName() + " completed withdraw: " + amount);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}


