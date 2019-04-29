package com.gwangmin.tdl.controller;

import com.gwangmin.tdl.domain.Comment;
import com.gwangmin.tdl.domain.ToDoList;
import com.gwangmin.tdl.repository.CommentRepository;
import com.gwangmin.tdl.repository.ToDoListRepository;
import com.gwangmin.tdl.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentRepository commentRepository;

    private final CommentService commentService;

    private final ToDoListRepository toDoListRepository;

    private ToDoList todo;


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getList(){
        List<Comment> comments = commentRepository.findAll();
        return ResponseEntity.ok(comments);
    }


    @PostMapping("/create/{idx}")
    public ResponseEntity<?> postList(@PathVariable("idx")Integer idx, @RequestBody Comment comment){  //생성
        todo = toDoListRepository.getOne(idx);
        comment.setCreatedDateNow();
        comment.setToDoList(todo);
        todo.add(comment);
        commentRepository.save(comment);
        System.out.println(commentService.findList(todo));
        return new ResponseEntity<>("{}", HttpStatus.CREATED);
    }

    @PutMapping("/update/{idx}")
    public ResponseEntity<?> putList(@PathVariable("idx")Integer idx, @RequestBody String content) {    //수정
        Comment persistList = commentRepository.getOne(idx);
        persistList.update(content);
        commentRepository.save(persistList);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @DeleteMapping("/delete/{idx}")
    public ResponseEntity<?> deleteList(@PathVariable("idx")Integer idx) {  //삭제
        commentRepository.deleteById(idx);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }
}
