package com.example.awsjpa.web.dto;

import com.example.awsjpa.domain.posts.po;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class poResponseDto {
    private Long id;
    private String content;
    private String author;
    private String title;

    public poResponseDto (po a){
        this.id = a.getId();
        this.content = a.getContent();
        this.author = a.getAuthor();
        this.title = a.getTitle();
    }
}
