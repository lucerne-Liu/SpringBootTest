package com.thoughtworks.employees.service;

import com.thoughtworks.employees.model.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> getEmployeeList();
    String saveEmployee(Employee employee);
    Employee getEmployee(Long id);
    String updateEmployee(Long id, Employee employee);
    String deleteEmployee(Long id);
}
