package com.gwangmin.tdl.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class CommentDTO implements Serializable {
    private Integer idx;

    private String content;

    private LocalDateTime createdDate;

    private LocalDateTime modifiedDate;

    public CommentDTO(Comment comment) {
        this.idx = comment.getIdx();
        this.content = comment.getContent();
        this.createdDate = comment.getCreatedDate();
        this.modifiedDate = comment.getModifiedDate();
    }
}
