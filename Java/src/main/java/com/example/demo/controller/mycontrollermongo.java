package com.example.demo.controller;

import com.example.demo.rep.EmployeeRepository;
import com.example.demo.models.Employee;
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


    @GetMapping("/all")
    public ResponseEntity<?> all_employee_handler(@RequestParam(value = "db", required=false, defaultValue="all") String db) {
        if (db.equals("mongo")) {
            List <Employee> employees = employeeRepository.findAll();
            return ResponseEntity.ok(employees);
        } else {
            return ResponseEntity.ok("No data");
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> employee_handler(@PathVariable("id") String id, @RequestParam(value = "db", required=false, defaultValue="all") String db) {
        if (db.equals("mongo")) {
            Optional<Employee> employees = employeeRepository.findById(id);
            return ResponseEntity.ok(employees);
        } else {
            return ResponseEntity.ok(null);
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
        } else {
            return ResponseEntity.ok(null);
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
        else {
            return ResponseEntity.ok(null);
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
        else {
            return ResponseEntity.ok("sql del");
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
        else {
            return ResponseEntity.ok("sql del");
        }
    }
}
