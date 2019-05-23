package com.gwangmin.tdl.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table
@Setter
public class User implements Serializable {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;

    @Column
    private String id;

    @Column
    private String password;

    @Column
    private String email;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<ToDoList> toDoLists = new ArrayList<>();

    public void add(ToDoList toDoList) {
        toDoList.setUser(this); //owner
        getToDoLists().add(toDoList);
    }

    @Builder
    public User(String id, String password, String email, List<ToDoList> toDoLists) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.toDoLists = toDoLists;
    }
}
