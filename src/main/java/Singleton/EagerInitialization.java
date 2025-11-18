package Singleton;

public class EagerInitialization {
    private EagerInitialization(){}

    private static final EagerInitialization instance = new EagerInitialization();

    public static EagerInitialization getInstance(){
        return instance;
    }

    public void print(){
        System.out.println("singleton class print method");
    }

}
