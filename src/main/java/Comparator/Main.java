package Comparator;
import Comparable.Employee;
import java.util.*;

public class Main {

    public static void main(String[] args){
        List<Employee> employees = new ArrayList<>();

        employees.add(new Employee(1, "test1", 20, 21));
        employees.add(new Employee(2, "test2", 22, 21));
        employees.add(new Employee(3, "test3", 24, 21));
        employees.add(new Employee(4, "test4", 26, 21));

        Collections.sort(employees, new NameComparator());
        System.out.println(employees);
    }
}
