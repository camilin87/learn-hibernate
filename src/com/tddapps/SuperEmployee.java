package com.tddapps;

import javax.persistence.*;
import java.io.Serializable;

/*
create table SUPER_EMPLOYEE(
  badge_number varchar(20),
  department varchar(20),

  first_name VARCHAR(20) default NULL,
  last_name  VARCHAR(20) default NULL,
  salary     INT  default NULL,
  PRIMARY KEY (badge_number, department)
);
 */
@Entity
@Table(name = "SUPER_EMPLOYEE")
public class SuperEmployee {
    @EmbeddedId
    private SuperEmployeePK id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "salary")
    private int salary;

    public SuperEmployee(){}
    public SuperEmployee(SuperEmployeePK id, String firstName, String lastName, int salary){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
    }

    public SuperEmployeePK getId() {
        return id;
    }
    public void setId(SuperEmployeePK id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getSalary() {
        return salary;
    }
    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Embeddable
    static class SuperEmployeePK implements Serializable {
        @Column(name = "badge_number")
        private String badgeNumber;
        @Column(name = "department")
        private String department;

        public SuperEmployeePK(){}

        public SuperEmployeePK(String badgeNumber, String department){
            this.badgeNumber = badgeNumber;
            this.department = department;
        }

        public String getBadgeNumber() {
            return badgeNumber;
        }
        public void setBadgeNumber(String badgeNumber) {
            this.badgeNumber = badgeNumber;
        }

        public String getDepartment() {
            return department;
        }
        public void setDepartment(String department) {
            this.department = department;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }

            if (!(obj instanceof SuperEmployeePK)){
                return false;
            }

            return toString().equals(obj.toString());
        }

        @Override
        public int hashCode() {
            return toString().hashCode();
        }

        @Override
        public String toString() {
            return String.format("SEPK-%s-%s", badgeNumber, department);
        }
    }
}
