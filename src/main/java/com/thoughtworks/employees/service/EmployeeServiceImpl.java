package com.thoughtworks.employees.service;

import com.thoughtworks.employees.model.Employee;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.springframework.stereotype.Component;


import java.util.*;


@Component
public class EmployeeServiceImpl implements EmployeeService {
    private static Map<Long, Employee> employees = Collections.synchronizedMap(new HashMap<>());
    private List<Employee> list;
    private static final String employeesList = "[{'id': 1,'name': '小明','age': 20,'gender': '男'}," +
            "{'id': 2,'name': '小红','age': 19,'gender': '女'}," +
            "{'id': 3,'name': '小智','age': 15,'gender': '男'}," +
            "{'id': 4,'name': '小刚','age': 16,'gender': '男'}," +
            "{'id': 5,'name': '小霞','age': 15,'gender': '女'}]";

    public EmployeeServiceImpl() {
        JSONArray jsonArray = JSONArray.fromObject(employeesList);
        list = JSONArray.toList(jsonArray, new Employee(), new JsonConfig());
        list.forEach(item -> {
            employees.put(item.getId(), item);
        });
    }

    @Override
    public List<Employee> getEmployeeList() {
        return new ArrayList<>(employees.values());
    }

    @Override
    public String saveEmployee(Employee employee) {
        Long id;
        if (!validateEmployeeInput(employee)) {
            return null;
        }
        if (employee.getId() == null) {
            id = getLastEmployeeId() + 1;
            employee.setId(id);
        } else {
            id = employee.getId();
        }
        employees.put(id, employee);
        return "success";
    }

    @Override
    public Employee getEmployee(Long id) {
        return employees.getOrDefault(id, null);
    }

    @Override
    public String updateEmployee(Long id, Employee employee) {
        if (!employees.containsKey(id)) {
            return null;
        }
        if (!validateEmployeeInput(employee)) {
            return "invalid";
        }
        Employee newEmployee = employees.get(id);
        newEmployee.setName(employee.getName());
        newEmployee.setAge(employee.getAge());
        newEmployee.setGender(employee.getGender());
        employees.put(id, newEmployee);
        return "success";
    }

    @Override
    public String deleteEmployee(Long id) {
        if (!employees.containsKey(id)) {
            return null;
        }
        employees.remove(id);
        return "success";
    }

    public long getLastEmployeeId() {
        Long id = 0L;
        for (Map.Entry<Long, Employee> entry : employees.entrySet()) {
            id = entry.getKey();
        }
        return id;
    }

    private boolean validateEmployeeInput(Employee employee) {
        List<String> genderList = Arrays.asList("男", "女");
        return employee.getName() != null && employee.getAge() != null && employee.getGender() != null && genderList.indexOf(employee.getGender()) != -1;
    }
}
