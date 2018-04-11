package com.thoughtworks.employees.controller;

import com.thoughtworks.employees.model.Employee;
import com.thoughtworks.employees.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
   @Autowired
   private EmployeeService employeeService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Employee> getEmployeeList() {
        return employeeService.getEmployeeList();
    }

    @RequestMapping(method = RequestMethod.POST)
    public String saveEmployee(@ModelAttribute Employee employee) throws Exception {
        return employeeService.saveEmployee(employee);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Employee getEmployee(@PathVariable Long id) {
        return employeeService.getEmployee(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public String updateEmployee(@PathVariable Long id, @ModelAttribute Employee employee) throws Exception {
        return employeeService.updateEmployee(id, employee);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteEmployee(@PathVariable Long id) throws Exception {
        return employeeService.deleteEmployee(id);
    }
}
