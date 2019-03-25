package com.gwangmin.tdl.controller;

import com.gwangmin.tdl.domain.User;
import com.gwangmin.tdl.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Controller
public class LoginController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/login")
    public String login(){
        return "/tdl/login";
    }

    @GetMapping("/register")
    public String register(){
        return "/tdl/register";
    }

    @PostMapping("/login/success")
    public ResponseEntity<?> postTDL(@RequestBody Map<String, String> user) {
        String email = user.get("email");
        String password = user.get("password");
        User loginUser = userRepository.findByemail(email);
        if (loginUser == null) {
            return null;
        }  else if(!loginUser.getPassword().equals(password)) {
            return null;
        }
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }


}
