package com.example.demo.rep_mysql;
import com.example.demo.models.EmployeeSQL;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;

@Qualifier("SQLEmployeeRepository")
public interface EmployeeRepositoryMySQL extends JpaRepository<EmployeeSQL, String> {

    Optional<EmployeeSQL> findByNameIgnoreCase(String name);
    void deleteByName(String name);
}