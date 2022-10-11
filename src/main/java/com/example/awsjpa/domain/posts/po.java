package com.example.awsjpa.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class po {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long id;

    @Column(length = 500, nullable = false, columnDefinition = "TEXT")
    private String title;

    @Column(length = 500, nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column
    private String author;

    @Builder
    public po(String title, String content, String author){
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }

}
