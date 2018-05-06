package com.thoughtworks.employees;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.employees.controller.EmployeeController;
import com.thoughtworks.employees.model.Employee;
import com.thoughtworks.employees.service.EmployeeService;
import com.thoughtworks.employees.service.EmployeeServiceImpl;
import javafx.application.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(classes = MockServletContext.class)
@SpringBootTest(classes = EmployeesApplication.class)
@WebAppConfiguration
public class EmployeesApplicationTests {
    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;
    private EmployeeController employeeController;
    @MockBean
    EmployeeService employeeService;

    @Before
    public void setUp(){
//        mvc = MockMvcBuilders.standaloneSetup(new EmployeeController()).build();
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
        employeeController = new EmployeeController();
    }

    @Test
    public void should_return_json_when_given_Employees_then_get_employees() throws Exception {
        Employee employee = new Employee(1L,"小米",20,"女");
        List<Employee> allEmployees = Arrays.asList(employee);
        given(employeeService.getEmployeeList()).willReturn(allEmployees);

        mvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(employee.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(employee.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].age").value(employee.getAge()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].gender").value(employee.getGender()));
    }

    @Test
    public void should_add_employee_when_given_right_employee_then_save_employee() throws Exception {
        Employee employee = new Employee(1L,"小米",20,"女");
//        given(employeeController.saveEmployee(employee)).willReturn("success");
        ObjectMapper mapper = new ObjectMapper();
        mvc.perform(post("/employees").contentType(MediaType.APPLICATION_JSON_UTF8).content(mapper.writeValueAsString(employee)))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("success")));
    }

    @Test
    public void should_prompt_wrong_msg_when_given_wrong_employee_then_save_employee() throws Exception {
        Employee employee = new Employee(1L,"小米",20);
        ObjectMapper mapper = new ObjectMapper();
        mvc.perform(post("/employees").contentType(MediaType.APPLICATION_JSON_UTF8).content(mapper.writeValueAsString(employee)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(equalTo("Input employee illegal!")));
    }

}
