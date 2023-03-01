package com.example.demo.rep_mongo;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Qualifier;
import com.example.demo.models.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

@Qualifier("MongoEmployeeRepository")
public interface EmployeeRepository extends MongoRepository<Employee, String> {

    Optional<Employee> findByName(String name);
    Optional<Employee> deleteByName(String name);
}
