package com.gwangmin.tdl.controller;

import com.gwangmin.tdl.domain.ToDoList;
import com.gwangmin.tdl.domain.User;
import com.gwangmin.tdl.repository.ToDoListRepository;
import com.gwangmin.tdl.repository.UserRepository;
import com.gwangmin.tdl.service.TodoListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/tdl")
public class ToDoListController {
    @Autowired
    TodoListService todoListService;
    private UserRepository userRepository;
    private ToDoListRepository toDoListRepository;

    private User useridx;


    public ToDoListController(ToDoListRepository toDoListRepository, UserRepository userRepository){
        this.toDoListRepository = toDoListRepository;
        this.userRepository = userRepository;
    }
    @GetMapping("/logout")
    public String logout(){
        this.useridx = null;
        return "redirect:/login";
    }

    @GetMapping("/list")
    public String list(Model model) {
        if(useridx == null) {
            return "redirect:/login";
        } else {
            model.addAttribute("todoList", todoListService.findList(this.useridx));
            return "/tdl/list";
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getList(){
        List<ToDoList> toDoLists = toDoListRepository.findAll();
        return ResponseEntity.ok(toDoLists);
    }

    @PostMapping("/login/create")
    public ResponseEntity<?> UserCreate(@RequestBody User user) {   //로그인 성공
        userRepository.save(user);
        return new ResponseEntity<>("{}", HttpStatus.CREATED);
    }

    @PostMapping("/post")
    public ResponseEntity<?> findUser(@RequestBody Map<String, String> user){   //로그인한 유저값 받아오기
        useridx = todoListService.findUser(user.get("email"));
        System.out.println(useridx);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<?> postList(@RequestBody ToDoList toDoList){      //생성
        toDoList.setCreatedDateNow();
        useridx.add(toDoList);
        toDoListRepository.save(toDoList);
        System.out.println(toDoList.getDescription());
        System.out.println(useridx.getToDoLists());
        return new ResponseEntity<>("{}", HttpStatus.CREATED);
    }



    @PutMapping("/update/{idx}")
    public ResponseEntity<?> putList(@PathVariable("idx")Integer idx, @RequestBody String description) {    //수정
        ToDoList persistList = toDoListRepository.getOne(idx);
        persistList.update(description);
        toDoListRepository.save(persistList);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @PutMapping("/complete/{idx}")
    public ResponseEntity<?> completeList(@PathVariable("idx")Integer idx) {    //게시물 완료
        ToDoList persistList = toDoListRepository.getOne(idx);
        persistList.complete();
        toDoListRepository.save(persistList);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @DeleteMapping("/delete/{idx}")
    public ResponseEntity<?> deleteList(@PathVariable("idx")Integer idx) {  //삭제
        toDoListRepository.deleteById(idx);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

}
