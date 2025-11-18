package Practice.Loadbalancer;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RoundRobinStrategyImpl implements LoadbalancerStrategy {
    private final AtomicInteger index = new AtomicInteger(-1);

    @Override
    public Instance getInstanceIndex(List<Instance> instances) {
        if(instances.isEmpty())
            throw new RuntimeException("No instances");
        return instances.get(index.updateAndGet(val->(val+1)%instances.size()));
    }
}
