package Singleton;

public class StaticBlock {
    private StaticBlock(){}
    private static StaticBlock instance;

    static{
        instance = new StaticBlock();
    }

    public static StaticBlock getInstance(){
        return instance;
    }
}
