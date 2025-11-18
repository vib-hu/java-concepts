package Comparable;

public class Employee implements Comparable<Employee> {
    private int id;
    private String name;
    private int age;
    private float salary;

    public Employee(int id, String name, int age, float salary){
        this.id = id;
        this.name = name;
        this.age = age;
        this.salary = salary;
    }

    public int compareTo(Employee obj){
        return this.id - obj.id;
    }

    public String getName(){
        return name;
    }

    @Override
    public String toString(){
        return id+" "+name;
    }
}
