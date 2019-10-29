package com.fsd.program.application;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { ProjectPlannerServiceApp.class })
public class ProjectPlannerServiceAppTest {

	@Test
	public void contextLoads() {
	}

	@Test
	public void testMain() {
		ProjectPlannerServiceApp.main(new String[] { "--spring.main.web-environment=false" });
	}

}
