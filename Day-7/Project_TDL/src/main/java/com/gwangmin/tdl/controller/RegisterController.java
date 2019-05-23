package com.gwangmin.tdl.controller;

import com.gwangmin.tdl.domain.User;
import com.gwangmin.tdl.domain.UserDTO;
import com.gwangmin.tdl.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String register() {
        return "/tdl/register";
    }

    @PostMapping
    public ResponseEntity<?> register(@Valid @RequestBody UserDTO userDTO) {
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userRepository.save(userDTO.user());
        return new ResponseEntity<>("{}", HttpStatus.CREATED);
    }

    @PostMapping("/confirm")
    public ResponseEntity<?> confirm(@RequestBody Map<String, String> user) {
        String email = user.get("email");
        User createdUser = userRepository.findByemail(email);
        if (createdUser == null) {
            return new ResponseEntity<>("{}", HttpStatus.OK);
        } else {
            return null;
        }
    }
}
