package com.gwangmin.tdl.register;

import com.gwangmin.tdl.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RegisterTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;
    @Test
    public void GetTest() throws Exception {
        mockMvc.perform(get("/register")).andExpect(status().isOk());
    }
    @Test   //email 중복검사
    public void PostTest() throws Exception{
        mockMvc.perform(post("/register/confirm")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"havi@gmail.com\"}"))
                .andExpect(status().isBadRequest());
    }
    @Test   //email형식 검사
    public void PostTest2() throws Exception{
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"rhkd4560\"}"))
                .andExpect(status().isBadRequest());
    }
    @Test   //email 공백검사
    public void PostTest3() throws Exception{
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"\"}"))
                .andExpect(status().isBadRequest());
    }
    @Test   //password 공백 검사
    public void PostPassword() throws Exception{
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"password\": \"\"}"))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void Password() throws  Exception{
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"password\": \"123456789012345\"}"))
                .andExpect(status().isBadRequest());
    }
    @Test   //post확인후 디비 검사
    public void Post() throws Exception{
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"rhkd4560@naver.com\", \"password\": \"1234\"}"))
                .andExpect(status().isCreated());
    }
}
