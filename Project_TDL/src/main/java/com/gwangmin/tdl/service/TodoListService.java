package com.gwangmin.tdl.service;

import com.gwangmin.tdl.domain.ToDoList;
import com.gwangmin.tdl.domain.User;
import com.gwangmin.tdl.repository.ToDoListRepository;
import com.gwangmin.tdl.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoListService {
    private final ToDoListRepository toDoListRepository;
    private final UserRepository userRepository;


    public TodoListService(ToDoListRepository toDoListRepository, UserRepository userRepository){
        this.toDoListRepository = toDoListRepository;
        this.userRepository = userRepository;
    }

    public List<ToDoList> findList() {
        return toDoListRepository.findAllByOrderByIdxAsc();
    }

    public User findUser(){
        return userRepository.getOne(1);
    }

}
