package com.telepathylabs.plancombinationfinder.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class APIControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testPostWithFileAndString() throws Exception {
		MockMultipartFile file = new MockMultipartFile("file", "Example1.txt", MediaType.TEXT_PLAIN_VALUE,
				("PLAN7f,12,email,database,admin,voice\n" +
						"PLAN1,100,voice,email\n" +
						"PLAN2,150,email,database,admin\n" +
						"PLAN3,125,voice,admin\n" +
						"PLAN4,135,database,admin\n" +
						"PLAN13,100,voice,email\n" +
						"PLAN26,150,email,database,admin\n" +
						"PLAN3a,1265,voice,admin\n" +
						"PLAN4f,125,database,admin").getBytes());
		this.mockMvc.perform(multipart("/plan").file(file)
						.param("features_needed", "email,admin,voice"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("12,PLAN7f")));
	}
}
