package com.example.demo.rep;

import com.example.demo.models.EmployeeSQL;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface EmployeeRepositorySQL extends JpaRepository<EmployeeSQL, String> {
    <S extends EmployeeSQL> S save(EmployeeSQL employeeSQL);

    @Modifying
    @Transactional
    @Query("delete from EmployeeSQL e where e.name = ?1")
    void deleteByName(String name);
}
