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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

}
