package Atomic;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceExample {
    public static void main(String[] args) {
        AtomicReference<User> atomicUser = new AtomicReference<>(new User("Alice"));
        System.out.println("Initial user: " + atomicUser.get());

        User oldUser = atomicUser.get();
        User newUser = new User("Bob");

        boolean updated = atomicUser.compareAndSet(oldUser, newUser);
        if(updated) {
            System.out.println("User updated to: " + atomicUser.get());
        } else {
            System.out.println("Update failed!");
        }
    }
}

class User{
    String name;

    User(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{name='" + name + "'}";
    }
}
