package com.gwangmin.tdl.repository;

import com.gwangmin.tdl.domain.ToDoList;
import com.gwangmin.tdl.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ToDoListRepository extends JpaRepository<ToDoList, Integer> {
     List<ToDoList> findAllByUserOrderByIdxAsc(User user);
     ToDoList findByIdx(Integer idx);
}
