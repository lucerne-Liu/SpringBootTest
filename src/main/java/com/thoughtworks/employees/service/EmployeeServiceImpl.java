package com.thoughtworks.employees.service;

import com.thoughtworks.employees.model.Employee;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.springframework.stereotype.Component;


import java.util.*;

@Component
public class EmployeeServiceImpl implements EmployeeService {
    private static Map<Long, Employee> employees = Collections.synchronizedMap(new HashMap<>());
    private JSONArray jsonArray = JSONArray.fromObject(emplyeesList);
    public static final String emplyeesList = "[{'id': 1,'name': '小明','age': 20,'gender': '男'}," +
            "{'id': 2,'name': '小红','age': 19,'gender': '女'}," +
            "{'id': 3,'name': '小智','age': 15,'gender': '男'}," +
            "{'id': 4,'name': '小刚','age': 16,'gender': '男'}," +
            "{'id': 5,'name': '小霞','age': 15,'gender': '女'}]";

    public EmployeeServiceImpl() {
        List<Employee> list = JSONArray.toList(jsonArray, new Employee() ,new JsonConfig());
        list.stream().forEach(item -> {
            employees.put(item.getId(), item);
        });
    }

    @Override
    public List<Employee> getEmployeeList() {
        List<Employee> list = new ArrayList<>(employees.values());
        return list;
    }

    @Override
    public String saveEmployee(Employee employee) throws Exception{
        if (employee.getId() == null || employee.getAge() == null || employee.getName() == null || employee.getGender() == null) {
            throw new Exception("Invalid Employee!");
        }
        employees.put(employee.getId(), employee);
        return "success";
    }

    @Override
    public Employee getEmployee(Long id) {
        return employees.containsKey(id) ? employees.get(id) : null;
    }

    @Override
    public String updateEmployee(Long id, Employee employee) throws Exception {
        if (employee.getId() == null || employee.getAge() == null || employee.getName() == null || employee.getGender() == null) {
            throw new Exception("Invalid Employee!");
        }
        if (!employees.containsKey(id)) {
            throw new Exception("Employee not found by id: " + id);
        }
        Employee newEmplyee = employees.get(id);
        newEmplyee.setName(employee.getName());
        newEmplyee.setAge(employee.getAge());
        newEmplyee.setGender(employee.getGender());
        employees.put(id, newEmplyee);
        return "success";
    }

    @Override
    public String deleteEmployee(Long id) throws Exception {
        if (!employees.containsKey(id)) {
            throw new Exception("Employee not found by id: " + id);
        }
        employees.remove(id);
        return "success";
    }
}
