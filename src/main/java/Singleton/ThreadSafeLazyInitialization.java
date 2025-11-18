package Singleton;

public class ThreadSafeLazyInitialization {

    private ThreadSafeLazyInitialization(){}
    private static ThreadSafeLazyInitialization instance;

    public static synchronized ThreadSafeLazyInitialization getInstance(){
        if(instance == null)
            instance = new ThreadSafeLazyInitialization();
        return instance;
    }

    public static ThreadSafeLazyInitialization getInstanceWithBetterPerformance(){
        if(instance!=null)
         return instance;

        synchronized(ThreadSafeLazyInitialization.class){
            if(instance==null)
                instance = new ThreadSafeLazyInitialization();
            return instance;
        }
    }
}
