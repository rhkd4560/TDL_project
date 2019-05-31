package com.gwangmin.tdl.todoList;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.gwangmin.tdl.domain.ToDoList;
import com.gwangmin.tdl.domain.UserDTO;
import com.gwangmin.tdl.repository.ToDoListRepository;
import com.gwangmin.tdl.service.TodoListService;
import com.gwangmin.tdl.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@SpringBootTest
public class todoTest {
    private MockMvc mockMvc;

    @Autowired
    private TodoListService todoListService;

    @Autowired
    private UserService userService;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    private ToDoListRepository toDoListRepository;

    @Autowired
    private ObjectMapper objectMapper;


    private UserDetails userDetails;

    @Before
    public void setup() throws Exception {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .alwaysDo(print())
                .build();

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("user1@naver.com");
        userDTO.setPassword("1234");

        mockMvc
                .perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)));

        userDetails = userService.loadUserByUsername("user1@naver.com");

        mockMvc.perform(get("/tdl/list").with(user(userDetails)))
                .andExpect(status().isOk());
    }

    @Test   //get todoList 요청
    public void getTest() throws Exception{

        mockMvc.perform(get("/tdl/list").with(user(userDetails)))
                .andExpect(view().name("/tdl/list"))
                .andExpect(status().isOk());
    }
    @Test
    public void created_Test() throws Exception{
        ToDoList toDoList = new ToDoList();
        toDoList.setDescription("test1");

        mockMvc.perform(post("/tdl").with(user(userDetails))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(toDoList)))
                .andExpect(status().isCreated());

        toDoList = toDoListRepository.findAll().get(0);
        assertThat(toDoList).isNotNull();
        assertThat(toDoList.getCompletedDate()).isNull();
        assertThat(toDoList.getStatus()).isFalse();
        assertThat(toDoList.getDescription()).isEqualTo("test1");
        assertThat(toDoList.getCreatedDate()).isNotNull();
        assertThat(toDoList.getUser().getIdx()).isEqualTo(userService.findUser(userDetails.getUsername()).getIdx());
    }

    @Test
    public void completeTest() throws Exception{

        ToDoList toDoList = new ToDoList();
        toDoList.setDescription("test1");

        mockMvc.perform(post("/tdl").with(user(userDetails))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(toDoList)))
                .andExpect(status().isCreated());

        mockMvc.perform(put("/tdl/complete/1").with(user(userDetails))
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());

        toDoList = toDoListRepository.findAll().get(0);
        assertThat(toDoList.getStatus()).isTrue();
    }

    @Test
    public void deleteTest() throws Exception{
        ToDoList toDoList = new ToDoList();
        toDoList.setDescription("test1");

        mockMvc.perform(post("/tdl").with(user(userDetails))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(toDoList)))
                .andExpect(status().isCreated());


        assertThat(toDoListRepository.findByIdx(1)).isNotNull();

        mockMvc.perform(delete("/tdl/delete/1").with(user(userDetails))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        assertThat(toDoListRepository.findByIdx(1)).isNull();
    }

    @Test
    public void updateTest() throws Exception{
        ToDoList toDoList = new ToDoList();
        toDoList.setDescription("test1");

        mockMvc.perform(post("/tdl").with(user(userDetails))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(toDoList)))
                .andExpect(status().isCreated());

        assertThat(toDoListRepository.findByIdx(1).getDescription()).isEqualTo("test1");

        mockMvc.perform(put("/tdl/update/1").with(user(userDetails))
                .contentType(MediaType.APPLICATION_JSON)
                .content("test2"))
                .andExpect(status().isOk());

        assertThat(toDoListRepository.findByIdx(1).getDescription()).isEqualTo("test2");
    }
}
