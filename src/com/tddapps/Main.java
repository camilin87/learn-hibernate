package com.tddapps;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting program");
        System.out.println(System.getProperty("user.dir"));

        SessionFactory factory = null;

        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }

        var repository = new EmployeeRepository(factory);

        var empID1 = repository.addEmployee("Zara", "Ali", 1000);
        var empID2 = repository.addEmployee("Daisy", "Das", 5000);
        repository.addEmployee("John", "Paul", 10000);


        repository.listEmployees();
        repository.updateEmployee(empID1, 5000);
        repository.deleteEmployee(empID2);
        repository.listEmployees();
    }
}
