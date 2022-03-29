package com.kmmoon.assignment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.kmmoon.assignment.dto.PostDTO;
import com.kmmoon.assignment.entity.Post;
import com.kmmoon.assignment.entity.User;
import com.kmmoon.assignment.repository.PostRepository;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class PostControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

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

    @Test
    @DisplayName("게시글 리스트 검색")
    public void getPostList() throws Exception{
        mockMvc.perform(
                        get("/post/list").header("Authorization", token)
                                .queryParam("cursorId","2")
                                .queryParam("size", "10")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    @DisplayName("게시글 상세조회")
    public void getPost() throws Exception{
        mockMvc.perform(get("/post/1")
                        .header("Authorization", token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }


    @Test
    @DisplayName("게시글 생성")
    public void createPost() throws Exception{
        PostDTO postDTO = new PostDTO("title","content");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

        String requestJson = ow.writeValueAsString(postDTO);

        mockMvc.perform(post("/post")
                        .header("Authorization", token)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }



    @Test
    @DisplayName("게시글 삭제")
    public void deletePost() throws Exception{
        mockMvc.perform(delete("/post/1")
                        .header("Authorization", token)
                )
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("게시글 수정")
    public void putPost() throws Exception{
        PostDTO postDTO = new PostDTO("modify_title","modify_content");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

        String requestJson = ow.writeValueAsString(postDTO);

        mockMvc.perform(put("/post/1")
                        .header("Authorization", token)
                        .content(requestJson)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    @DisplayName("게시글 좋아요")
    public void likePost() throws Exception{
        mockMvc.perform(post("/post/2/like")
                        .header("Authorization", token)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }


}
