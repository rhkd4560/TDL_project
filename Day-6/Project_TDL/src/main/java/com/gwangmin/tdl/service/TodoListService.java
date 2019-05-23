package com.gwangmin.tdl.service;

import com.gwangmin.tdl.domain.ToDoList;
import com.gwangmin.tdl.domain.User;
import com.gwangmin.tdl.repository.ToDoListRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoListService {
    private final ToDoListRepository toDoListRepository;

    public TodoListService(ToDoListRepository toDoListRepository) {
        this.toDoListRepository = toDoListRepository;
    }

    public List<ToDoList> findList(User user) {
        return toDoListRepository.findAllByUserOrderByIdxAsc(user);
    }


}