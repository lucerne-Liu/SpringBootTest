package com.thoughtworks.employees;

import com.thoughtworks.employees.web.EmployeeController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MockServletContext.class)
public class EmployeesApplicationTests {
	private MockMvc mvc;
	private RequestBuilder request;

	@Before
	public void setUp() throws Exception {
		mvc = MockMvcBuilders.standaloneSetup(new EmployeeController()).build();
		request = null;
	}

	@Test
	public void should_return_employeeList_when_get_employees() throws Exception {
		request = get("/employees");
		mvc.perform(request).andExpect(status().isOk()).andExpect(content().string(equalTo("[{\"id\":1,\"name\":\"小明\",\"age\":20,\"gender\":\"男\"}," +
				"{\"id\":2,\"name\":\"小红\",\"age\":19,\"gender\":\"女\"}," +
				"{\"id\":3,\"name\":\"小智\",\"age\":15,\"gender\":\"男\"}," +
				"{\"id\":4,\"name\":\"小刚\",\"age\":16,\"gender\":\"男\"}," +
				"{\"id\":5,\"name\":\"小霞\",\"age\":15,\"gender\":\"女\"}]")));
	}

    @Test
    public void should_return_success_when_add_employee() throws Exception {
        request = post("/employees").param("id","6").param("name","小闷").param("age","26").param("gender","男");
        mvc.perform(request).andExpect(status().isOk()).andExpect(content().string(equalTo("success")));
    }

    @Test
    public void should_include_new_employees_after_add_employee() throws Exception {
        request = post("/employees").param("id","6").param("name","小闷").param("age","26").param("gender","男");
        mvc.perform(request);
        request = get("/employees");
        mvc.perform(request).andExpect(status().isOk()).andExpect(content().string(equalTo("[{\"id\":1,\"name\":\"小明\",\"age\":20,\"gender\":\"男\"}," +
                "{\"id\":2,\"name\":\"小红\",\"age\":19,\"gender\":\"女\"}," +
                "{\"id\":3,\"name\":\"小智\",\"age\":15,\"gender\":\"男\"}," +
                "{\"id\":4,\"name\":\"小刚\",\"age\":16,\"gender\":\"男\"}," +
                "{\"id\":5,\"name\":\"小霞\",\"age\":15,\"gender\":\"女\"}," +
                "{\"id\":6,\"name\":\"小闷\",\"age\":26,\"gender\":\"男\"}]")));
    }

    @Test
    public void should_update_employeesList_after_update_employee() throws Exception {
        request = put("/employees/1").param("name","小明").param("age","11").param("gender","女");
        mvc.perform(request);
        request = get("/employees");
        mvc.perform(request).andExpect(status().isOk()).andExpect(content().string(equalTo("[{\"id\":1,\"name\":\"小明\",\"age\":11,\"gender\":\"女\"}," +
                "{\"id\":2,\"name\":\"小红\",\"age\":19,\"gender\":\"女\"}," +
                "{\"id\":3,\"name\":\"小智\",\"age\":15,\"gender\":\"男\"}," +
                "{\"id\":4,\"name\":\"小刚\",\"age\":16,\"gender\":\"男\"}," +
                "{\"id\":5,\"name\":\"小霞\",\"age\":15,\"gender\":\"女\"}]")));
    }

    @Test
    public void should_return_employee_when_search_by_id() throws Exception {
        request = get("/employees/1");
        mvc.perform(request).andExpect(status().isOk()).andExpect(content().string(equalTo("{\"id\":1,\"name\":\"小明\",\"age\":20,\"gender\":\"男\"}")));
    }

    @Test
    public void should_delete_employee_when_delete_by_id() throws Exception {
        request = delete("/employees/1");
        mvc.perform(request).andExpect(content().string(equalTo("success")));
        request = get("/employees");
        mvc.perform(request).andExpect(status().isOk()).andExpect(content().string(equalTo("[{\"id\":2,\"name\":\"小红\",\"age\":19,\"gender\":\"女\"}," +
                "{\"id\":3,\"name\":\"小智\",\"age\":15,\"gender\":\"男\"}," +
                "{\"id\":4,\"name\":\"小刚\",\"age\":16,\"gender\":\"男\"}," +
                "{\"id\":5,\"name\":\"小霞\",\"age\":15,\"gender\":\"女\"}]")));
    }
}
