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


    public ToDoListController(ToDoListRepository toDoListRepository, UserRepository userRepository) {
        this.toDoListRepository = toDoListRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("todoList", todoListService.findList());
        if (useridx == null) useridx = todoListService.findUser();
        System.out.println(useridx);
        return "/tdl/list";
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getList() {
        List<ToDoList> toDoLists = toDoListRepository.findAll();
        return ResponseEntity.ok(toDoLists);
    }

    @PostMapping("/login/create")
    public ResponseEntity<?> UserCreate(@RequestBody User user) {
        userRepository.save(user);
        return new ResponseEntity<>("{}", HttpStatus.CREATED);
    }


    @PostMapping
    public ResponseEntity<?> postList(@RequestBody ToDoList toDoList) {
        toDoList.setUser(useridx);
        toDoList.setCreatedDateNow();
        toDoListRepository.save(toDoList);
        return new ResponseEntity<>("{}", HttpStatus.CREATED);
    }

    @PutMapping("/update/{idx}")
    public ResponseEntity<?> putList(@PathVariable("idx") Integer idx, @RequestBody String description) {
        ToDoList persistList = toDoListRepository.getOne(idx);
        persistList.update(description);
        toDoListRepository.save(persistList);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @PutMapping("/complete/{idx}")
    public ResponseEntity<?> completeList(@PathVariable("idx") Integer idx) {
        ToDoList persistList = toDoListRepository.getOne(idx);
        persistList.complete();
        toDoListRepository.save(persistList);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @DeleteMapping("/delete/{idx}")
    public ResponseEntity<?> deleteList(@PathVariable("idx") Integer idx) {
        toDoListRepository.deleteById(idx);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

}
