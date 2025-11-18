package Practice.Loadbalancer;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomStrategyImpl implements LoadbalancerStrategy {

    @Override
    public Instance getInstanceIndex(List<Instance> instances) {
        return instances.get(ThreadLocalRandom.current().nextInt(instances.size()));
    }
}
