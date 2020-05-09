/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *	  https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.banyan.poc.moduleA.controller;

import com.banyan.poc.moduleA.bean.MessageBean;
import com.banyan.poc.moduleA.controller.ModuleARestController;
import com.banyan.poc.moduleA.service.MessageService;
import com.github.javafaker.BackToTheFuture;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ModuleARestController.class)
@ActiveProfiles("local")
public class RestControllerUnitTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private MessageService messageService;

	@Test
	public void noParamGreetingShouldReturnDefaultMessage() throws Exception {

		Mockito.when(messageService.postMessage()).thenReturn(getFakeMessage());

		this.mockMvc
				.perform(post("/name/store"))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.backToTheFutureCharacter").exists());
	}

	private MessageBean getFakeMessage(){
		Faker faker = new Faker();
		BackToTheFuture backToTheFuture = faker.backToTheFuture();
		MessageBean messageBean = new MessageBean();
		messageBean.setBackToTheFutureCharacter(backToTheFuture.character());
		messageBean.setBackToTheFutureQuote(backToTheFuture.quote());
		return messageBean;
	}
}
