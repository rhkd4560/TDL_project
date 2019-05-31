package com.gwangmin.tdl.comment;

import com.gwangmin.tdl.repository.CommentRepository;
import com.gwangmin.tdl.repository.ToDoListRepository;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
public class commentTest{
    private MockMvc mockMvc;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    ToDoListRepository toDoListRepository;

}
