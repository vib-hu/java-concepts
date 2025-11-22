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
/*
Why You MUST Keep 2 Lists
You might think: "Can I optimize to use just 1 list?"
Option A: Only CopyOnWriteArrayList (No Cache)
public Instance getInstance(){    // Two separate calls to getArray() - RACE CONDITION!    int size = instances.size();    // ← arrayV1    int idx = counter % size;    return instances.get(idx);      // ← might be arrayV2! 💥}
❌ Race condition → IndexOutOfBoundsException

Option B: Copy on Every Read
public Instance getInstance(){    List<Instance> copy = new ArrayList<>(instances); // O(n) EVERY TIME!    return strategy.getInstanceIndex(copy);}
❌ 1 million copies/second → Performance disaster!

Option C: Current Design (Optimal) ✅
public Instance getInstance(){    List<Instance> snapshot = cachedSnapshot.get(); // O(1) reference    return strategy.getInstanceIndex(snapshot);}

✅ Best of both worlds!
The "2 Lists" Are Actually Cheap!
Memory Cost:
CopyOnWriteArrayList: 100 instance references ≈ 800 bytes
ImmutableList: 100 instance references ≈ 800 bytes
Total: 1.6 KB for 100 instances
With 1000 instances: 16 KB
This is negligible compared to:
The Instance objects themselves (KBs to MBs)
The JVM heap (typically GBs)
The performance gain from O(1) reads */

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
