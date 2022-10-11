package com.example.awsjpa.domain.posts;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostsRepositoryTest {
    @Autowired
    PostsRepository postsRepository;

    @After
    public void cleanup(){
        postsRepository.deleteAll();
    }

    @Test
    public void upload(){
        String title = "테스트 게시글";
        String content = "테스트 내용";
        postsRepository.save(
                Posts.builder().title(title)
                        .content(content)
                        .author("abcdefg@naver.com")
                        .build());

        List<Posts> list = postsRepository.findAll();

        Posts posts = list.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }

    @Test
    public void BaseTimeEntity_test(){
        LocalDateTime now = LocalDateTime.of(2022,3,8,12,12);
        postsRepository.save(Posts.builder()
                .title("김윤환")
                .content("ㅄ")
                .author("ASL")
                .build());

        List<Posts> list = postsRepository.findAll();

        Posts posts = list.get(0);

        System.out.println("생성일 : "+posts.getLocalDateTime()+" / 수정일 : "+posts.getModifiedDate());

        assertThat(posts.getLocalDateTime()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);
    }
}
