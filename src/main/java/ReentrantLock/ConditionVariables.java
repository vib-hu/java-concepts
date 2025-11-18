package ReentrantLock;

import java.util.ArrayDeque;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionVariables {
    private final ArrayDeque<Integer> queue = new ArrayDeque<>();
    private final int capacity = 3;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    public void produce() throws InterruptedException {
        lock.lock();
        try {
            if (queue.size() == capacity) {
                System.out.println("Queue is full");
                notFull.await(); //await() releases the lock, await() re-acquires the lock before returning
            }
            queue.add(1);
            notEmpty.signal();
        }finally {
            lock.unlock();
        }
    }

    public int consume() throws InterruptedException{
        lock.lock();
        try{
            while(queue.isEmpty()){
                notEmpty.await();
            }
            int val = queue.poll();
            notFull.signal(); //signal() wakes one waiter,signalAll() wakes all waiters
            return val;
        }finally {
            lock.unlock();
        }
    }
}
