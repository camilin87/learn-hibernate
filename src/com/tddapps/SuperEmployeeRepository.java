package com.tddapps;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class SuperEmployeeRepository {
    private final SessionFactory factory;

    public SuperEmployeeRepository(SessionFactory factory) {
        this.factory = factory;
    }

    public SuperEmployee.SuperEmployeePK addEmployee(SuperEmployee employee){
        var session = factory.openSession();
        Transaction tx = null;
        SuperEmployee.SuperEmployeePK employeeID = null;

        try {
            tx = session.beginTransaction();
            employeeID = (SuperEmployee.SuperEmployeePK) session.save(employee);
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
            var employees = session.createQuery("FROM SuperEmployee").list();
            for (var obj : employees) {
                var employee = (SuperEmployee) obj;
                System.out.print("  id: " + employee.getId());
                System.out.print("  First Name: " + employee.getFirstName());
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

    public void updateEmployee(SuperEmployee.SuperEmployeePK EmployeeID, int salary){
        var session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            var employee = session.get(SuperEmployee.class, EmployeeID);
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

    public void deleteEmployee(SuperEmployee.SuperEmployeePK EmployeeID){
        var session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            var employee = session.get(SuperEmployee.class, EmployeeID);
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
