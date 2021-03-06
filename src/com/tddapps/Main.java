package com.tddapps;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.time.LocalTime;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting program");
        System.out.println(System.getProperty("user.dir"));

        var factory = buildSessionFactory();

        PrintSeparator();
        AddEmployees(factory);
        PrintSeparator();
        AddSuperEmployees(factory);
        PrintSeparator();

        System.exit(0);
    }

    private static SessionFactory buildSessionFactory() {
        try {
            var configuration = new Configuration();
            configuration.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/test_sandbox");

            configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
            configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
            configuration.setProperty("hibernate.show_sql", "true");

            configuration.setProperty("hibernate.c3p0.min_size", "5");
            configuration.setProperty("hibernate.c3p0.max_size", "20");
            configuration.setProperty("hibernate.c3p0.timeout", "300");
            configuration.setProperty("hibernate.c3p0.max_statements", "50");
            configuration.setProperty("hibernate.c3p0.idle_test_period", "3000");

            configuration.addPackage("com.tddapps");
            configuration.addAnnotatedClass(Employee.class);
            configuration.addAnnotatedClass(SuperEmployee.class);

            return configuration.buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    private static void PrintSeparator() {
        System.out.println();
        System.out.println("----------------------------------------");
        System.out.println();
    }

    private static void AddSuperEmployees(SessionFactory factory) {
        var keyPrefix = LocalTime.now().toString();

        var repository = new SuperEmployeeRepository(factory);

        var empID1 = repository.addEmployee(new SuperEmployee(
                new SuperEmployee.SuperEmployeePK(
                        "1000001",
                        String.format("%s-pro", keyPrefix)
                ),
                "SZarai",
                "SAli",
                1000
        ));
        var empID2 = repository.addEmployee(new SuperEmployee(
                new SuperEmployee.SuperEmployeePK(
                        "1000001",
                        String.format("%s-dev", keyPrefix)
                ),
                "SDaisy",
                "SDas",
                5000
        ));
        repository.addEmployee(new SuperEmployee(
                new SuperEmployee.SuperEmployeePK(
                        "1000002",
                        String.format("%s-dev", keyPrefix)
                ),
                "SJohns",
                "SPau",
                10000
        ));

        repository.listEmployees();
        repository.updateEmployee(empID1, 5000);
        repository.deleteEmployee(empID2);
        repository.listEmployees();
    }

    private static void AddEmployees(SessionFactory factory) {
        var repository = new EmployeeRepository(factory);

        var empID1 = repository.addEmployee("Zarai", "Ali", 1000);
        var empID2 = repository.addEmployee("Daisy", "Das", 5000);
        repository.addEmployee("Johnn", "Pau", 10000);


        repository.listEmployees();
        repository.updateEmployee(empID1, 5000);
        repository.deleteEmployee(empID2);
        repository.listEmployees();
    }
}
