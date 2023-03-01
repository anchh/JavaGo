package com.example.demo.controller;

import com.example.demo.models.Employee;
import com.example.demo.models.EmployeeSQL;
import com.example.demo.rep_mongo.EmployeeRepository;
import com.example.demo.rep_mysql.EmployeeRepositoryMySQL;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/employee")
public class mycontrollermongo {

    @Autowired
    private EmployeeRepository employeeRepository;
    private EmployeeRepositoryMySQL employeeRepositoryMySQL;


    @GetMapping("/all")
    public ResponseEntity<?> all_employee_handler(@RequestParam(value = "db", required=false, defaultValue="all") String db) {
        if (db.equals("mongo")) {
            List <Employee> employees = employeeRepository.findAll();
            return ResponseEntity.ok(employees);
        }
        else if (db.equals("mysql")) {
            // functionality for mysql
            List <EmployeeSQL> employees = employeeRepositoryMySQL.findAll();
            return ResponseEntity.ok(employees);
        }
        else {
            return ResponseEntity.ok("not a valid db");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> employee_handler(@PathVariable("id") String id, @RequestParam(value = "db", required=false, defaultValue="all") String db) {
        if (db.equals("mongo")) {
            Optional<Employee> employees = employeeRepository.findById(id);
            if (employees.isPresent()) {
                return ResponseEntity.ok(employees);
            } else {
                return ResponseEntity.ok("not a valid id");
            }
        } else if (db.equals("mysql")) {
            // functionality for mysql
            Optional<EmployeeSQL> employee = employeeRepositoryMySQL.findById(id);
            if (employee.isPresent()) {
                return ResponseEntity.ok(employee);
            } else {
                return ResponseEntity.ok("not a valid id");
            }
        }
        else {
            return ResponseEntity.ok("not a valid db");
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> add_employee_handler(@RequestParam(value = "db", required=false, defaultValue="all") String db, @RequestBody Employee employee) {
        if (db.equals("mongo")) {
            try {
            String id = UUID.randomUUID().toString();

            Employee _employee = employeeRepository.save(new Employee(id, employee.getName(), employee.getEmail(), employee.getPhone(), employee.getAddress(), employee.getSalary()));
            return ResponseEntity.ok(_employee);
            } catch (Exception e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else if (db.equals("mysql")) {
            // functionality for mysql
            try {
                String id = UUID.randomUUID().toString();
                EmployeeSQL _employee = employeeRepositoryMySQL.save(new EmployeeSQL(id, employee.getName(), employee.getEmail(), employee.getPhone(), employee.getAddress(), employee.getSalary()));
                return ResponseEntity.ok(_employee);
            } catch (Exception e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        else {
            return ResponseEntity.ok("not a valid db");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update_employee_handler(@PathVariable("id") String id, @RequestParam(value = "db", required=false, defaultValue="all") String db, @RequestBody Employee employee) {
        if (db.equals("mongo")){
            Optional<Employee> _employeedetails = employeeRepository.findById(id);
            if (_employeedetails.isPresent()){
                employeeRepository.deleteById(id);
                Employee _employee = _employeedetails.get();
                _employee.setName(employee.getName());
                _employee.setEmail(employee.getEmail());
                _employee.setPhone(employee.getPhone());
                _employee.setAddress(employee.getAddress());
                _employee.setSalary(employee.getSalary());
                return ResponseEntity.ok(employeeRepository.save(_employee));
            } else {
                return ResponseEntity.ok(null);
            }
        }
        else if (db.equals("mysql")) {
            // functionality for mysql
            Optional<EmployeeSQL> _employeedetails = employeeRepositoryMySQL.findById(id);
            if (_employeedetails.isPresent()){
                employeeRepositoryMySQL.deleteById(id);
                EmployeeSQL _employee = _employeedetails.get();
                _employee.setName(employee.getName());
                _employee.setEmail(employee.getEmail());
                _employee.setPhone(employee.getPhone());
                _employee.setAddress(employee.getAddress());
                _employee.setSalary(employee.getSalary());
                return ResponseEntity.ok(employeeRepositoryMySQL.save(_employee));
            } else {
                return ResponseEntity.ok(null);
            }
        }
        else {
            return ResponseEntity.ok("not a valid db");
        }

        }

    @DeleteMapping("remove/id/{id}")
    public ResponseEntity<?> delete_employeeID_handler(@PathVariable("id") String id, @RequestParam(value = "db", required=false, defaultValue="all") String db) {
        if (db.equals("mongo")) {
            try {
                Optional <Employee> _employee = employeeRepository.findById(id);
                if (_employee.isPresent()) {
                    employeeRepository.deleteById(id);
                    return ResponseEntity.ok("deleted");
                } else {
                    return ResponseEntity.ok("not found");
                }
            }
               catch (Exception e) {
                return ResponseEntity.ok("not found");
              }
            }
        else if (db.equals("mysql")) {
            // functionality for mysql
            try {
                Optional <EmployeeSQL> _employee = employeeRepositoryMySQL.findById(id);
                if (_employee.isPresent()) {
                    employeeRepository.deleteById(id);
                    return ResponseEntity.ok("deleted");
                } else {
                    return ResponseEntity.ok("not found");
                }
            }
               catch (Exception e) {
                return ResponseEntity.ok("not found");
              }
        }
        else {
            return ResponseEntity.ok("not a valid db");
        }
    }

    @DeleteMapping("remove/name/{name}")
    public ResponseEntity<?> delete_employeeName_handler(@PathVariable("name") String name, @RequestParam(value = "db", required=false, defaultValue="all") String db) {
        if (db.equals("mongo")) {
            try {
                Optional <Employee> _employee = employeeRepository.findByName(name);
                if (_employee.isPresent()) {
                    employeeRepository.deleteByName(name);
                    return ResponseEntity.ok("deleted");
                } else {
                    return ResponseEntity.ok("not found");
                }
            }
               catch (Exception e) {
                return ResponseEntity.ok("not found");
              }
            }
        else if (db.equals("mysql")) {
            // functionality for mysql
            try {
                Optional <EmployeeSQL> _employee = employeeRepositoryMySQL.findByNameIgnoreCase(name);
                if (_employee.isPresent()) {
                    employeeRepositoryMySQL.deleteByName(name);
                    return ResponseEntity.ok("deleted");
                } else {
                    return ResponseEntity.ok("not found");
                }
            }
               catch (Exception e) {
                return ResponseEntity.ok("not found");
              }
        }
        else {
            return ResponseEntity.ok("not a valid db");
        }
    }
}
