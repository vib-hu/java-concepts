package Practice.Loadbalancer;

import java.util.List;

public interface LoadbalancerStrategy {
    Instance getInstanceIndex(List<Instance> instances);
}
