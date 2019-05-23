package com.gwangmin.tdl.controller;

import com.gwangmin.tdl.domain.User;
import com.gwangmin.tdl.repository.UserRepository;
import com.gwangmin.tdl.service.TodoListService;
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
    @Autowired
    TodoListService todoListService;


    @GetMapping("/login")
    public String login() {
        return "/login";
    }


    @PostMapping("/login/post")
    public ResponseEntity<?> findUser(@RequestBody Map<String, String> user) {   //로그인한 유저값 받아오기
        /*currentuser = userService.loadUserByUsername(user.get("email"));*/
        System.out.println(user);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @GetMapping("/loginSuccess")
    public String loginComplete() {
        return "redirect:/tdl/list";
    }

//    @PostMapping("/login/success")
//    public ResponseEntity<?> postTDL(@RequestBody Map<String, String> user) {
//        String email = user.get("email");
//        String password = user.get("password");
//        User loginUser = userRepository.findByemail(email);
//        if (loginUser == null) {
//            return null;
//        }  else if(!loginUser.getPassword().equals(password)) {
//            return null;
//        }
//        return new ResponseEntity<>("{}", HttpStatus.OK);
//    }


}
