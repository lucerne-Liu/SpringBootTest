package com.thoughtworks.employees.controller;

import com.thoughtworks.employees.model.Employee;
import com.thoughtworks.employees.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> saveEmployee(@RequestBody Employee employee) throws Exception {
        String result = employeeService.saveEmployee(employee);
        HttpStatus status = result != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(result == null ? "Input employee illegal!" : result, status);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getEmployee(@PathVariable Long id) {
        Employee employee = employeeService.getEmployee(id);
        if (employee == null) {
            return new ResponseEntity<String>("Cannot find such employee with input id.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Employee>(employee, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<String> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) throws Exception {
        String result = employeeService.updateEmployee(id, employee);;
        if (result == null) {
            return new ResponseEntity<>("Cannot find such employee with input id.", HttpStatus.NOT_FOUND);
        }else if(result.equals("invalid")){
            return new ResponseEntity<>("Input employee illegal!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) throws Exception {
        String result = employeeService.deleteEmployee(id);
        if (result == null){
            return new ResponseEntity<>("Cannot find such employee with input id.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
