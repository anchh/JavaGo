package com.example.demo.models;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

@Document(collection = "employee")
public class EmployeeMongo {
    @Id
    private String id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private int salary;

    public EmployeeMongo(String name, String email, String phone, String address, int salary) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.salary = salary;
    }
    public EmployeeMongo(Employee employee) {
        this.id = employee.getId();
        this.name = employee.getName();
        this.email = employee.getEmail();
        this.phone = employee.getPhone();
        this.address = employee.getAddress();
        this.salary = employee.getSalary();
    }

    public EmployeeMongo() {
        this("", "", "", "", 0);
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
    @Override
    //printing the employee details
    public String toString(){
        return "Employee [name=" + name + ", email=" + email + ", phone=" + phone + ", address=" + address + ", salary=" + salary + "]";
    }

}
