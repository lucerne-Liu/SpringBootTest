package com.thoughtworks.employees.web;

import com.thoughtworks.employees.domain.Employee;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private static Map<Long, Employee> employees = Collections.synchronizedMap(new HashMap<>());
    private JSONArray jsonArray = JSONArray.fromObject(emplyeesList);
    public static final String emplyeesList = "[{'id': 1,'name': '小明','age': 20,'gender': '男'}," +
            "{'id': 2,'name': '小红','age': 19,'gender': '女'}," +
            "{'id': 3,'name': '小智','age': 15,'gender': '男'}," +
            "{'id': 4,'name': '小刚','age': 16,'gender': '男'}," +
            "{'id': 5,'name': '小霞','age': 15,'gender': '女'}]";

    public EmployeeController() {
        List<Employee> list = JSONArray.toList(jsonArray, new Employee() ,new JsonConfig());
        list.stream().forEach(item -> {
            employees.put(item.getId(), item);
        });
    }

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
