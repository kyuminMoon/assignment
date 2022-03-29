package com.kmmoon.assignment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.kmmoon.assignment.entity.User;
import com.kmmoon.assignment.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private UserRepository userRepository;

	private String token = "";

	private User user;

	private void join(Long id, String accountId, String nickname, User.AccountType accountType, boolean quit) throws Exception{
		user = User.builder()
				.id(id)
				.accountId(accountId)
				.nickname(nickname)
				.accountType(accountType)
				.quit(quit)
				.build();

//		userRepository.save(user);
	}

	@BeforeEach
	public void setUp() throws Exception{
		Long id = 1L;
		String accountId = "LESSEE_1";
		String nickname = "LESSEE";
		User.AccountType accountType = User.AccountType.LESSEE;
		boolean quit = false;

		join(id, accountId, nickname, accountType, quit);
		token = accountType.name() + " " + id;
	}

	@AfterEach
	public void tearDown() throws Exception{
		SecurityContextHolder.clearContext();
	}

	@Test
	@DisplayName("유저 리스트 검색")
	public void getUserList() throws Exception{
		mockMvc.perform(
					get("/user/list").header("Authorization", token)
				)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
	}

	@Test
	@DisplayName("유저 좋아요 리스트")
	public void getUserLikeList() throws Exception{
		mockMvc.perform(get("/user/" + user.getId() + "/like")
						.header("Authorization", token))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
	}

}
