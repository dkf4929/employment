package com.project.employment;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EmploymentApplicationTests {

	@Test
	void contextLoads() {
		String s = "20200201";
		System.out.println("s1 = " + s.substring(0,4));
		System.out.println("s2 = " + s.substring(4,6));
		System.out.println("s3 = " + s.substring(6,8));
	}

}
