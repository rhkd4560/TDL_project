package com.gwangmin.tdl.service;

import com.gwangmin.tdl.domain.Comment;
import com.gwangmin.tdl.domain.ToDoList;
import com.gwangmin.tdl.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public List<Comment> findList(ToDoList toDoList){
        return commentRepository.findAllByToDoListOrderByIdxAsc(toDoList);
    }
}
