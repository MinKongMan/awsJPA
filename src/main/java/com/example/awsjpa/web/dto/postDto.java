package com.example.awsjpa.web.dto;

import com.example.awsjpa.domain.posts.po;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class postDto {
    private String title;
    private String author;
    private String content;

    @Builder
    public postDto(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public po Entity(){
        po entity = po.builder().author(author)
                .content(content)
                .title(title)
                .build();
        return entity;
    }
}
