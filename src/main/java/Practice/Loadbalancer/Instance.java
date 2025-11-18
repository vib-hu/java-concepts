package Practice.Loadbalancer;

import java.util.Objects;

public class Instance {
    private long id;
    private String name;

    public Instance(long id, String name){
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(!(o instanceof Instance)) return false;
        Instance instance = (Instance) o;
        return id == instance.id;
    }

    @Override
    public int hashCode(){
        return Objects.hashCode(id);
    }
}

//✅ Why equals() alone is NOT enough
//If you use an object as a key in a HashMap (and ConcurrentHashMap.newKeySet() stores keys internally), then Java
// requires both equals() and hashCode() to be consistent.
//👉 Hash-based collections NEVER call equals() first
//They work like this:
//Compute hashCode() → decide which bucket to store in
//Inside the bucket, compare with existing keys using equals()
//So if you override only equals() and NOT hashCode(), then:
//Two objects that are logically equal will produce different hashCode values
//They will land in different buckets
//Therefore the Set/Map will treat them as different keys
//So duplicates will NOT be removed