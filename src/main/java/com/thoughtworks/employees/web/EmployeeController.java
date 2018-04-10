package com.thoughtworks.employees.web;

import com.thoughtworks.employees.domain.Employee;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    static Map<Long, Employee> employees = Collections.synchronizedMap(new HashMap<>());

    @RequestMapping(method = RequestMethod.GET)
    public List<Employee> getEmployeeList() {
        List<Employee> list = new ArrayList<>(employees.values());
        return list;
    }

    @RequestMapping(method = RequestMethod.POST)
    public String saveEmployee(@ModelAttribute Employee employee) {
        employees.put(employee.getId(), employee);
        return "success";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Employee getEmployee(@PathVariable Long id) {
        return employees.get(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public String updateEmployee(@PathVariable Long id, @ModelAttribute Employee employee) {
        Employee newEmplyee = employees.get(id);
        newEmplyee.setName(employee.getName());
        newEmplyee.setAge(employee.getAge());
        newEmplyee.setGender(employee.getGender());
        employees.put(id, newEmplyee);
        return "success";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteEmployee(@PathVariable Long id) {
        employees.remove(id);
        return "success";
    }
}
