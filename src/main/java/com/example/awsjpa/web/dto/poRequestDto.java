package com.example.awsjpa.web.dto;

import com.example.awsjpa.domain.posts.po;
import lombok.Builder;
import lombok.Getter;

@Getter
public class poRequestDto {
    private Long id;
    private String content;
    private String title;
    private String author;

    @Builder
    public poRequestDto(String content, String title, String author){
        this.content = content;
        this.title = title;
        this.author = author;
    }

    public po toEntity(){
        return po.builder().title(title)
                .content(content)
                .author(author)
                .build();
    }

}
