package Concurrency;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class BankAccount {
    private double balance;

    public BankAccount(double initialBalance){
        this.balance = initialBalance;
    }

    public void deposit(double amount){
        synchronized (this){
            balance = balance + amount;
            System.out.println("Balance on deposit "+balance);
        }
    }

    public void withdraw(double amount){
        synchronized (this){
            if(balance >= amount){
                balance = balance - amount;
                System.out.println("Balance on withdraw "+balance);
            }
        }
    }

    public double getBalance(){
        synchronized (this){
            return balance;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        BankAccount account = new BankAccount(100);
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(()->{
            for(int i=0;i<50;i++){
                account.deposit(10);
            }
        });

        executor.submit(()->{
            for(int i=0;i<50;i++){
                account.withdraw(5);
            }
        });

        executor.shutdown();
        executor.awaitTermination(5000, TimeUnit.SECONDS);

        // giving different outputs on multiple run due to
        // out of sequence run of submitted tasks
        System.out.println(account.getBalance());
    }
}
