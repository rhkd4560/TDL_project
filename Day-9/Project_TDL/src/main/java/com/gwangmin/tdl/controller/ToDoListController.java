package com.gwangmin.tdl.controller;

import com.gwangmin.tdl.domain.ToDoList;
import com.gwangmin.tdl.domain.User;
import com.gwangmin.tdl.repository.CommentRepository;
import com.gwangmin.tdl.repository.ToDoListRepository;
import com.gwangmin.tdl.service.CommentService;
import com.gwangmin.tdl.service.TodoListService;
import com.gwangmin.tdl.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/tdl")
public class ToDoListController {

    private final TodoListService todoListService;

    private final UserService userService;

    private final ToDoListRepository toDoListRepository;

    private final CommentRepository commentRepository;
    private User useridx;


    @GetMapping("/logout")
    public String logout(){
        return "redirect:/login";
    }

    @GetMapping("/list")
    public String list(Model model) {
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        useridx = userService.findUser(user.getUsername());
        model.addAttribute("todoList", todoListService.findList(useridx));
        return "/tdl/list";
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getList(){
        List<ToDoList> toDoLists = toDoListRepository.findAll();
        return ResponseEntity.ok(toDoLists);
    }

    @PostMapping
    public ResponseEntity<?> postList(@RequestBody ToDoList toDoList){  //생성
        toDoList.setCreatedDateNow();
        useridx.add(toDoList);
        toDoListRepository.save(toDoList);
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
