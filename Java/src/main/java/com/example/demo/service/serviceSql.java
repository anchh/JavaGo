package com.example.demo.service;
import com.example.demo.models.Employee;
import com.example.demo.models.EmployeeSQL;
import com.example.demo.rep.EmployeeRepositorySQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

@Service
public class serviceSql implements service{

    @Autowired
    EmployeeRepositorySQL employeeRepositorySql;

    public List<Employee> get_all_employees() {
        List<Employee> employeeGeneric = new ArrayList<Employee>();
        List<EmployeeSQL> employees = employeeRepositorySql.findAll();
        //Convert to generic
        for(EmployeeSQL employeeSQL : employees){
            Employee temp = new Employee(employeeSQL);
            employeeGeneric.add(temp);
        }
        return employeeGeneric;
    }

    public Optional<Employee> get_employee_by_id(String id) {
        Optional<EmployeeSQL> employees = employeeRepositorySql.findById(id);
            if (employees.isPresent()){
                Employee temp = new Employee(employees.get());
                return Optional.of(temp);
            }
            return null;
    }

    public Employee add_employee(Employee employee) {
        EmployeeSQL _employee = employeeRepositorySql.save(new EmployeeSQL(employee.getId(), employee.getName(), employee.getEmail(), employee.getPhone(), employee.getAddress(), employee.getSalary()));
        return new Employee(_employee);
    }

    public Optional<Employee> update_employee(String id, Employee employee) {
        Optional<EmployeeSQL> _employeedetails = employeeRepositorySql.findById(id);
            if (_employeedetails.isPresent()){
                employeeRepositorySql.deleteById(id);
                EmployeeSQL _employee = _employeedetails.get();
                _employee.setName(employee.getName());
                _employee.setEmail(employee.getEmail());
                _employee.setPhone(employee.getPhone());
                _employee.setAddress(employee.getAddress());
                _employee.setSalary(employee.getSalary());
                employeeRepositorySql.save(_employee);
                return Optional.of(new Employee(_employee));
            }
            return null;
    }

    public String delete_employee(String id) {
        try{
            employeeRepositorySql.deleteById(id);
            return "Employee deleted";
        }
        catch(Exception e){
            return "Employee not found";
        }
    }

    public String delete_employee_by_name(String name) {
        // try{
            employeeRepositorySql.deleteByName(name);
            return "Employee deleted";
        // }
        // catch(Exception e){
        //     return(e.getMessage());
        // }

    }
}
