package com.gwangmin.tdl.domain;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Table
@Getter
@NoArgsConstructor
public class ToDoList implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer idx;

    @Column
    private String description;

    @Column
    private Boolean status;

    @Column
    private LocalDateTime createdDate;

    @Column
    private LocalDateTime completedDate;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "toDoList", fetch = FetchType.EAGER)
    private List<Comment> comments = new ArrayList<>();

    public ToDoList(String description, Boolean status, LocalDateTime createdDate, LocalDateTime completedDate, User user, List<Comment> comments) {
        this.description = description;
        this.status = status;
        this.createdDate = createdDate;
        this.completedDate = completedDate;
        this.user = user;
        this.comments = comments;
    }

    public void setCreatedDateNow() {
        this.createdDate = LocalDateTime.now();
    }

    public void update(String description) {
        this.createdDate = LocalDateTime.now();
        this.description = description;
    }
    public void complete(){
        if(status==null || status == false){
            this.status = true;
            this.completedDate = LocalDateTime.now();
        } else {
            this.status = false;
            this.completedDate = null;
        }
    }
    public void add(Comment comment){
        comment.setToDoList(this); //owner
        this.comments.add(comment);
    }
}
