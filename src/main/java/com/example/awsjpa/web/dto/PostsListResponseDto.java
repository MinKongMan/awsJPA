package com.example.awsjpa.web.dto;


import com.example.awsjpa.domain.posts.Posts;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
public class PostsListResponseDto {
    private Long id;
    private String title;
    private String author;
    private String content;
    private LocalDateTime modifiedDate;

    public PostsListResponseDto(Posts p){
        id = p.getId();
        title = p.getTitle();
        author = p.getAuthor();
        content = p.getContent();
        modifiedDate = p.getModifiedDate();
    }
}
