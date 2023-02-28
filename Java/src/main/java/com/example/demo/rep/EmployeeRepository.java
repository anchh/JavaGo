package com.example.demo.rep;

import java.util.Optional;

import com.example.demo.models.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmployeeRepository extends MongoRepository<Employee, String> {

    Optional<Employee> findByName(String name);
    Optional<Employee> deleteByName(String name);
}
