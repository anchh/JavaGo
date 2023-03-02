package com.example.demo.service;
import com.example.demo.models.Employee;
import java.util.List;
import java.util.Optional;

public interface service {

    List<Employee> get_all_employees();

    Optional<Employee> get_employee_by_id(String id);

    Employee add_employee(Employee employee);

    Optional<Employee> update_employee(String id, Employee employee);

    String delete_employee(String id);

    String delete_employee_by_name(String name);

}
