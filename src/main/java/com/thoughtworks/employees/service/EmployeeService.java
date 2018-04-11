package com.thoughtworks.employees.service;

import com.thoughtworks.employees.model.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> getEmployeeList();
    String saveEmployee(Employee employee) throws Exception;
    Employee getEmployee(Long id);
    String updateEmployee(Long id, Employee employee) throws Exception ;
    String deleteEmployee(Long id) throws Exception ;
}
