package com.example.awsjpa.web;

import com.example.awsjpa.domain.posts.Posts;
import com.example.awsjpa.domain.posts.PostsRepository;
import com.example.awsjpa.web.dto.PostsSaveRequestDto;
import com.example.awsjpa.web.dto.PostsUpdateRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.contentOf;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostsApiControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private PostsRepository postsRepository;

    @After
    public void tearDown() throws Exception{
        postsRepository.deleteAll();
    }

    @Test
    public void Posts_regist() throws Exception{
        String title = "title";
        String content = "content";
        PostsSaveRequestDto postsSaveRequestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author("author")
                .build();

        String url = "http://localhost:"+port+"/api/v1/posts";

        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url,postsSaveRequestDto,Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> post = postsRepository.findAll();
        assertThat(post.get(0).getTitle()).isEqualTo(title);
        assertThat(post.get(0).getContent()).isEqualTo(content);
    }

    @Test
    public void update() throws Exception{

        Posts posts = postsRepository.save(Posts.builder().title("기뮤난")
                .content("기뮨지")
                .author("asl")
                .build());

        Long id = posts.getId();
        String title = "김윤환";
        String content = "화이팅";

        PostsUpdateRequestDto rDto = PostsUpdateRequestDto.builder()
                .title(title)
                .content(content)
                .build();

        String url = "http://localhost:"+port+"/api/v1/posts/"+id;

        HttpEntity<PostsUpdateRequestDto> requestDtoHttpEntity = new HttpEntity<>(rDto);

        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestDtoHttpEntity, Long.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Posts> array = postsRepository.findAll();

        System.out.println(array.size()+"zz");
        assertThat(array.get(0).getContent()).isEqualTo(content);
        assertThat(array.get(0).getTitle()).isEqualTo(title);

    }

    @Test
    public void select() throws Exception{

    }
}
