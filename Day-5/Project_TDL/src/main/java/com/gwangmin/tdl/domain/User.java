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
    private String email;

    @Column
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<ToDoList> toDoLists = new ArrayList<>();

    public void add(ToDoList toDoList) {
        toDoList.setUser(this); //owner
        this.toDoLists.add(toDoList);
    }

    @Builder
    public User(String password, String email, List<ToDoList> toDoLists) {
        this.password = password;
        this.email = email;
        this.toDoLists = toDoLists;
    }
}
