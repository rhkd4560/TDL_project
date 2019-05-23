package com.gwangmin.tdl.login;

import com.gwangmin.tdl.domain.UserDTO;
import com.gwangmin.tdl.repository.UserRepository;
import com.gwangmin.tdl.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LoginTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;


    @Test
    public void UserTest(){
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("user1@naver.com");
        userDTO.setPassword("1234");
        userRepository.save(userDTO.user());
    }

}
