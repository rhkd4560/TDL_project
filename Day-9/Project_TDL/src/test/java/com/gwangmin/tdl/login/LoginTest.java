package com.gwangmin.tdl.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gwangmin.tdl.domain.UserDTO;
import com.gwangmin.tdl.repository.UserRepository;
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

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginTest {

    private MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    WebApplicationContext webApplicationContext;

    @Before
    public void basic() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .alwaysDo(print())
                .build();
    }
    @Test
    public void loginTest() throws Exception{

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("user1@naver.com");
        userDTO.setPassword("1234");

        mockMvc
                .perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userDTO)));

        // 이메일이 틀렸을시
        mockMvc.perform(formLogin()
                .user("user2@naver.com")
                .password("1234"))
                .andExpect(unauthenticated())
                .andExpect(redirectedUrl("/login?error"))
                .andExpect(status().is3xxRedirection());

        // 비밀번호가 틀렸을 시
        mockMvc.perform(formLogin()
                .user("user1@naver.com")
                .password("12345"))
                .andExpect(unauthenticated())
                .andExpect(redirectedUrl("/login?error"))
                .andExpect(status().is3xxRedirection());

        //로그인 성공 시
        mockMvc.perform(formLogin()
                .user("user1@naver.com")
                .password("1234"))
                .andExpect(redirectedUrl("/loginSuccess"))
                .andExpect(authenticated())
                .andExpect(status().is3xxRedirection());

        // 로그인 성공 시 /tdl/list로 이동
        UserDetails userDetails = userService.loadUserByUsername("user1@naver.com");

        mockMvc.perform(get("/tdl/list").with(user(userDetails)))
                .andExpect(authenticated())
                .andExpect(status().isOk());

    }
}
