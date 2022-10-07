package com.postgresstsk.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.postgresstsk.entities.Employee;

public interface EmployeeRepo extends JpaRepository<Employee,String>{

}
