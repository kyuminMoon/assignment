package com.kmmoon.assignment.dto;

import com.kmmoon.assignment.entity.Post;
import com.kmmoon.assignment.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {

    @Size(max = 200)
    @NotNull(message = "내용을 입력해주세요")
    private String title;

    @Size(max = 20000)
    @NotNull(message = "내용을 입력해주세요")
    private String content;

    public Post of(User user){
        return Post.builder()
                .user(user)
                .title(this.getTitle())
                .content(this.getContent())
                .build();
    }
}

