package com.gwangmin.tdl.repository;

import com.gwangmin.tdl.domain.Comment;
import com.gwangmin.tdl.domain.ToDoList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findAllByToDoListOrderByIdxAsc(ToDoList toDoList);
}
