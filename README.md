# Java Concepts Practice

A comprehensive collection of Java concurrency and design pattern examples, including a production-ready URL shortener implementation.

## Projects

### URL Shortener (`Practice/UrlShortner`)

A thread-safe, distributed URL shortening service with support for concurrent request handling and collision-resistant short code generation.

#### Features

- ✅ **Thread-Safe**: Uses `ConcurrentHashMap` and atomic operations
- ✅ **Distributed-Friendly**: Random code generation works across multiple servers
- ✅ **Collision Handling**: Automatic retry mechanism with configurable limits
- ✅ **Secure**: Uses `SecureRandom` for cryptographically secure code generation
- ✅ **Base62 Encoding**: Generates URL-safe short codes (0-9, A-Z, a-z)
- ✅ **Analytics-Ready**: Generates unique short URLs per request for tracking

#### Usage

```java
UrlShorter shortener = new UrlShorter();

// Shorten a URL
String longUrl = "https://www.example.com/very/long/path";
String shortUrl = shortener.getShortUrl(longUrl);
System.out.println(shortUrl);  // https://www.example.com/aB3xY7z

// Retrieve original URL
String original = shortener.getLongUrl(shortUrl);
System.out.println(original);  // https://www.example.com/very/long/path
```

#### Design Decisions

1. **Per-Request Short URLs**: Each shortening request generates a unique URL, enabling:
   - Individual click tracking
   - User attribution
   - A/B testing
   - Campaign-specific analytics

2. **Random vs Sequential IDs**:
   - ✅ Distributed system friendly (no coordination needed)
   - ✅ Unpredictable (security)
   - ✅ No overflow issues
   - ⚠️ Requires collision handling

3. **Base62 Encoding**: URL-safe character set without encoding overhead

#### Code Length vs Capacity

| Length | Combinations | Collision Probability at 1M URLs |
|--------|--------------|----------------------------------|
| 6 chars | 56 billion | ~0.09% |
| 7 chars | 3.5 trillion | ~0.003% |
| 8 chars | 218 trillion | ~0.00005% |

#### Thread Safety

```java
// ✅ GOOD - Atomic operation
if (map.putIfAbsent(shortUrl, longUrl) == null) {
    return shortUrl;  // Successfully inserted
}
```

The system uses `putIfAbsent()` for atomic check-and-insert operations, preventing race conditions.

## Other Concepts Covered

### Concurrency Patterns

#### Atomic Operations (`Atomic/`)
- `AtomicIntegerExample.java` - Lock-free integer operations
- `AtomicReferenceExample.java` - Lock-free reference updates

#### Synchronization Primitives (`Concurrency/`)
- `BankAccount.java` - synchronized methods
- `MyCallable.java` - Callable interface
- `MyRunnable.java` - Runnable interface
- `SignalAwaitReentrantLockProducerConsumer.java` - Condition variables
- `SynchronizedCounter.java` - Thread-safe counter
- `WaitNotifyIntrinsicLockProducerConsumer.java` - Classic producer-consumer

#### Advanced Synchronizers

**CountDownLatch** (`CountdownLatch/`)
- Coordination mechanism for thread synchronization
- Wait for multiple threads to complete

**CyclicBarrier** (`CyclicBarrier/`)
- Reusable synchronization point
- All threads wait until everyone arrives

**Phaser** (`Phaser/`)
- Flexible multi-phase synchronization
- Dynamic thread registration

**Semaphore** (`Semaphore/`)
- Resource access control
- Connection pool implementation

#### Locks (`ReentrantLock/`)
- `LockExample.java` - ReentrantLock basics
- `ReadWriteLockExample.java` - Read-write lock optimization
- `ConditionVariables.java` - Advanced condition handling

#### Memory Visibility (`Volatile/`)
- `VolatileExample.java` - Volatile keyword demonstration
- Happens-before relationships

### Design Patterns

#### Singleton Pattern (`Singleton/`)
- `EagerInitialization.java` - Thread-safe eager loading
- `StaticBlock.java` - Static block initialization
- `ThreadSafeLazyInitialization.java` - Double-checked locking

#### Comparator & Comparable (`Comparable/`, `Comparator/`)
- Custom sorting implementations
- Comparator chaining

### Practice Problems

#### Load Balancer (`Practice/Loadbalancer/`)
Strategy pattern implementation with:
- Random strategy
- Round-robin strategy
- Pluggable algorithm design

## Running the Examples

### Prerequisites

- Java 8 or higher
- Maven

### Build

```bash
mvn clean compile
```

### Run Specific Examples

```bash
# URL Shortener
java -cp target/classes Practice.UrlShortner.UrlShorter

# Atomic Integer Example
java -cp target/classes Atomic.AtomicIntegerExample

# CountDownLatch Example
java -cp target/classes CountdownLatch.CountdownLatch

# And so on...
```

## Key Learning Objectives

### Concurrency Fundamentals
- Thread safety and race conditions
- Atomic operations and CAS (Compare-And-Swap)
- Lock-free programming
- Memory visibility and volatile

### Synchronization Mechanisms
- Intrinsic locks (synchronized)
- Explicit locks (ReentrantLock)
- Read-write locks
- Lock-free data structures (ConcurrentHashMap)

### Coordination Tools
- CountDownLatch - One-time coordination
- CyclicBarrier - Reusable barriers
- Phaser - Multi-phase coordination
- Semaphore - Resource management

### Design Patterns
- Singleton (thread-safe variations)
- Strategy pattern
- Producer-Consumer

## Production Considerations

### URL Shortener Enhancements

For production use, consider adding:

1. **Database Persistence**
   - PostgreSQL/MySQL for durable storage
   - Redis for caching hot URLs

2. **Distributed ID Generation**
   - Twitter Snowflake algorithm
   - Coordinated ID ranges across servers

3. **URL Expiration**
   - TTL support
   - Automatic cleanup of expired URLs

4. **Analytics**
   - Click tracking
   - Geolocation data
   - Referrer tracking

5. **Security**
   - URL validation
   - Malware/spam blacklist
   - Rate limiting
   - Custom alias validation

6. **Monitoring**
   - Collision rate metrics
   - Latency tracking
   - Capacity alerts

## Interview Discussion Points

When discussing these implementations:

1. **Concurrency**: Explain thread-safety mechanisms and trade-offs
2. **Performance**: Lock-free vs locked approaches
3. **Scalability**: Horizontal scaling considerations
4. **Trade-offs**: Sequential vs random IDs, memory vs disk, etc.
5. **Production**: What's missing for real-world deployment

## Common Pitfalls Demonstrated

- ❌ Check-then-act race conditions
- ❌ Integer overflow with counters
- ❌ Inappropriate use of synchronized
- ❌ Missing volatile for visibility
- ❌ Unbounded retry loops

## Best Practices Shown

- ✅ Use `ConcurrentHashMap` for concurrent maps
- ✅ Use atomic operations (`putIfAbsent`, `compareAndSet`)
- ✅ Prefer `AtomicLong` over `synchronized` counters
- ✅ Use `SecureRandom` for security-sensitive code
- ✅ Always add retry limits to loops
- ✅ Validate inputs
- ✅ Add observability (metrics, logging)

## Resources

- [Java Concurrency in Practice](https://jcip.net/)
- [Java Memory Model](https://docs.oracle.com/javase/specs/jls/se8/html/jls-17.html)
- [Doug Lea's Concurrent Programming](http://gee.cs.oswego.edu/dl/cpj/)

## Project Structure

```
java-concepts/
├── src/main/java/
│   ├── Atomic/              # Atomic operations examples
│   ├── Comparable/          # Comparable interface
│   ├── Comparator/          # Comparator pattern
│   ├── Concurrency/         # Basic concurrency primitives
│   ├── CountdownLatch/      # CountDownLatch examples
│   ├── CyclicBarrier/       # CyclicBarrier examples
│   ├── Phaser/              # Phaser examples
│   ├── Practice/
│   │   ├── Loadbalancer/    # Strategy pattern implementation
│   │   └── UrlShortner/     # URL shortener service
│   ├── ReentrantLock/       # Lock examples
│   ├── Semaphore/           # Semaphore examples
│   ├── Singleton/           # Singleton patterns
│   └── Volatile/            # Memory visibility
├── pom.xml
└── README.md
```

## Contributing

This is a personal learning repository. Feel free to fork and adapt for your own learning.

## License

Educational project for demonstrating Java concurrency concepts.

## Author

Vibhu Mishra

