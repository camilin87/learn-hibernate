package com.tddapps;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Iterator;

public class EmployeeRepository {
    private final SessionFactory factory;

    public EmployeeRepository(SessionFactory factory){
        this.factory = factory;
    }

    public Integer addEmployee(String fname, String lname, int salary){
        var session = factory.openSession();
        Transaction tx = null;
        Integer employeeID = null;

        try {
            tx = session.beginTransaction();
            var employee = new Employee(fname, lname, salary);
            employeeID = (Integer) session.save(employee);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return employeeID;
    }

    public void listEmployees( ){
        var session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            var employees = session.createQuery("FROM Employee").list();
            for (var obj : employees) {
                var employee = (Employee) obj;
                System.out.print("First Name: " + employee.getFirstName());
                System.out.print("  Last Name: " + employee.getLastName());
                System.out.println("  Salary: " + employee.getSalary());
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void updateEmployee(Integer EmployeeID, int salary ){
        var session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            var employee = session.get(Employee.class, EmployeeID);
            employee.setSalary( salary );
            session.update(employee);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public void deleteEmployee(Integer EmployeeID){
        var session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            var employee = session.get(Employee.class, EmployeeID);
            session.delete(employee);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
