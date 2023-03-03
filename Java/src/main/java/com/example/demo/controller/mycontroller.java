package com.example.demo.controller;

import com.example.demo.models.Employee;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

import com.example.demo.service.serviceMongo;
import com.example.demo.service.serviceSql;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/employee")
public class mycontroller {

    @Autowired
    serviceMongo serviceMongo = new serviceMongo();
    @Autowired
    serviceSql serviceSql = new serviceSql();

    @GetMapping("/all")
    public ResponseEntity<?> all_employee_handler(@RequestParam(value = "db", required=false, defaultValue="all") String db) {
        if (db.equals("mongo")) {
            try{
                return ResponseEntity.ok(serviceMongo.get_all_employees());
            }
            catch (Exception e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        else if (db.equals("sql")) {
            try {
                return ResponseEntity.ok(serviceSql.get_all_employees());
            }
            catch (Exception e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        else{
            return ResponseEntity.ok("No data");
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> employee_handler(@PathVariable("id") String id, @RequestParam(value = "db", required=false, defaultValue="all") String db) {
        if (db.equals("mongo")) {
            try {
                return ResponseEntity.ok(serviceMongo.get_employee_by_id(id));
            }
            catch (Exception e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else if (db.equals("sql")){
            try {
                return ResponseEntity.ok(serviceSql.get_employee_by_id(id));
            }
            catch (Exception e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return ResponseEntity.ok(null);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> add_employee_handler(@RequestParam(value = "db", required=false, defaultValue="all") String db, @RequestBody Employee employee) {
        String id = UUID.randomUUID().toString();
        employee.setId(id);
        if (db.equals("mongo")) {
            try{
                return ResponseEntity.ok(serviceMongo.add_employee(employee));
            }
            catch (Exception e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else if(db.equals("sql")) {
            try{
                return ResponseEntity.ok(serviceSql.add_employee(employee));
            }
            catch (Exception e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        else {
            return ResponseEntity.ok(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update_employee_handler(@PathVariable("id") String id, @RequestParam(value = "db", required=false, defaultValue="all") String db, @RequestBody Employee employee) {
        if (db.equals("mongo")) {
            try {
                return ResponseEntity.ok(serviceMongo.update_employee(id, employee));
            }
            catch (Exception e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else if (db.equals("sql")) {
            try {
                return ResponseEntity.ok(serviceSql.update_employee(id, employee));
            }
            catch (Exception e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return ResponseEntity.ok(null);
        }
    }

    @DeleteMapping("remove/id/{id}")
    public ResponseEntity<?> delete_employeeID_handler(@PathVariable("id") String id, @RequestParam(value = "db", required=false, defaultValue="all") String db) {
        if (db.equals("mongo")) {
            try{
                return ResponseEntity.ok(serviceMongo.delete_employee(id));
            }
            catch (Exception e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else if (db.equals("sql")) {
            try{
                return ResponseEntity.ok(serviceSql.delete_employee(id));
            }
            catch (Exception e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        else {
            return ResponseEntity.ok("sql del");
        }
    }

    @DeleteMapping("remove/name/{name}")
    public ResponseEntity<?> delete_employeeName_handler(@PathVariable("name") String name, @RequestParam(value = "db", required=false, defaultValue="all") String db) {
        if (db.equals("mongo")) {
            try{
                return ResponseEntity.ok(serviceMongo.delete_employee_by_name(name));
            }
            catch (Exception e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else if (db.equals("sql")) {
            try{
                return ResponseEntity.ok(serviceSql.delete_employee_by_name(name));
            }
            catch (Exception e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        else {
            return ResponseEntity.ok("sql del");
        }
    }
}
