package com.gwangmin.tdl.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Setter
@Table
@Getter
@NoArgsConstructor
public class Comment implements Serializable {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;

    @Column
    String content;

    @Column
    LocalDateTime createdDate;

    @Column
    LocalDateTime modifiedDate;

    @ManyToOne
    ToDoList toDoList;

    public Comment(String content, LocalDateTime createdDate, LocalDateTime modifiedDate, ToDoList toDoList) {
        this.content = content;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.toDoList = toDoList;
    }

    public void setCreatedDateNow() {
        this.createdDate = LocalDateTime.now();
    }

    public void update(String content) {
        this.modifiedDate = LocalDateTime.now();
        this.content = content;
    }
}
