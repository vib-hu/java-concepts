package Practice.Loadbalancer;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicReference;

/// If your requirement is:
/// index access
/// thread-safe “check-then-add” using addIfAbsent
/// occasional removal
/// many reads, fewer writes
/// then CopyOnWriteArrayList is the perfect choice.
/// But one warning: removal is expensive:✔ a new array copy - ✔ O(n) time operation

public class Loadbalancer {

    private CopyOnWriteArrayList<Instance> instances = new CopyOnWriteArrayList<>();
    private final AtomicReference<List<Instance>> cachedSnapshot = new AtomicReference<>(List.of());
    private LoadbalancerStrategy strategy;

    public Loadbalancer(LoadbalancerStrategy strategy){
        this.strategy = strategy;
    }

    public void addInstance(Instance instance){
        if(instances.addIfAbsent(instance)){ // this is thread safe
            cachedSnapshot.set(List.copyOf(instances));
        }
    }

    public void removeInstance(Instance instance){
        if(instances.remove(instance)) // this is thread safe
        {
            cachedSnapshot.set(List.copyOf(instances));
        }
    }

    public Instance getInstance(){
        // Get immutable snapshot - this reference won't change even if cache is updated
        List<Instance> snapShotReference =  cachedSnapshot.get();
        return strategy.getInstanceIndex(snapShotReference);
    }
}
