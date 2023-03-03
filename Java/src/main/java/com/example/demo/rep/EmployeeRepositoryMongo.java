package com.example.demo.rep;

import java.util.Optional;

import com.example.demo.models.EmployeeMongo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmployeeRepositoryMongo extends MongoRepository<EmployeeMongo, String> {

    Optional<EmployeeMongo> findByName(String name);
    Optional<EmployeeMongo> deleteByName(String name);
}
