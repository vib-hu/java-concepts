package Practice.Loadbalancer;

public class TestLoadbalancer {
    public static void main(String[] args){
//        LoadbalancerStrategy strategy = new RandomStrategyImpl();
//        Loadbalancer loadbalancer = new Loadbalancer(strategy);
//
//        loadbalancer.addInstance(new Instance(1, "test-1"));
//        loadbalancer.addInstance(new Instance(2, "test-2"));
//        loadbalancer.addInstance(new Instance(3, "test-3"));
//
//        Instance instance = loadbalancer.getInstance();
//        System.out.println(instance.toString());

        LoadbalancerStrategy strategy1 = new RoundRobinStrategyImpl();
        Loadbalancer loadbalancer1 = new Loadbalancer(strategy1);

        loadbalancer1.addInstance(new Instance(1, "test-1"));
        loadbalancer1.addInstance(new Instance(2, "test-2"));
        loadbalancer1.addInstance(new Instance(3, "test-3"));
        loadbalancer1.addInstance(new Instance(4, "test-4"));
        loadbalancer1.addInstance(new Instance(5, "test-5"));

        Instance instance1 = loadbalancer1.getInstance();
        System.out.println(instance1.toString());

        instance1 = loadbalancer1.getInstance();
        System.out.println(instance1.toString());

        instance1 = loadbalancer1.getInstance();
        System.out.println(instance1.toString());

        instance1 = loadbalancer1.getInstance();
        System.out.println(instance1.toString());

        instance1 = loadbalancer1.getInstance();
        System.out.println(instance1.toString());

        instance1 = loadbalancer1.getInstance();
        System.out.println(instance1.toString());
    }
}
