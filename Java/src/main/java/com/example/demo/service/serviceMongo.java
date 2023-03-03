package com.example.demo.service;
import com.example.demo.models.Employee;
import com.example.demo.models.EmployeeMongo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.rep.EmployeeRepositoryMongo;

@Service
public class serviceMongo implements service{

    @Autowired
    EmployeeRepositoryMongo employeeRepositoryMongo;

    public List<Employee> get_all_employees() {
        List<Employee> employeeGeneric = new ArrayList<Employee>();
        List<EmployeeMongo> employees = employeeRepositoryMongo.findAll();
        //Convert to generic
        for(EmployeeMongo employeeMongo : employees){
            Employee temp = new Employee(employeeMongo);
            employeeGeneric.add(temp);
        }
        return employeeGeneric;
    }

    public Optional<Employee> get_employee_by_id(String id) {
        Optional<EmployeeMongo> employees = employeeRepositoryMongo.findById(id);
        if(employees.isPresent()){
            Employee temp = new Employee(employees.get());
            return Optional.of(temp);
        }
        return null;
    }

    public Employee add_employee(Employee employee) {
        EmployeeMongo _employee = employeeRepositoryMongo.save(new EmployeeMongo(employee.getId(), employee.getName(), employee.getEmail(), employee.getPhone(), employee.getSalary()));
        return new Employee(_employee);

    }

    public Optional<Employee> update_employee(String id, Employee employee) {

        Optional<EmployeeMongo> _employeedetails = employeeRepositoryMongo.findById(id);
            if (_employeedetails.isPresent()){
                employeeRepositoryMongo.deleteById(id);
                EmployeeMongo _employee = _employeedetails.get();
                _employee.setName(employee.getName());
                _employee.setEmail(employee.getEmail());
                _employee.setPhone(employee.getPhone());
                _employee.setAddress(employee.getAddress());
                _employee.setSalary(employee.getSalary());
                employeeRepositoryMongo.save(_employee);
                return Optional.of(new Employee(_employee));
            }
            return null;

    }

    public String delete_employee(String id) {
        try{
            employeeRepositoryMongo.deleteById(id);
            return "Employee deleted";
        }
        catch(Exception e){
            return "Employee not found";
        }
    }

    public String delete_employee_by_name(String name) {
        try{
            employeeRepositoryMongo.deleteByName(name);
            return "Employee deleted";
        }
        catch(Exception e){
            return "Employee not found";
        }
    }
}
