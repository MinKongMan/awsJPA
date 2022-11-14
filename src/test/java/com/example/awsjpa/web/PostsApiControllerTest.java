package com.example.awsjpa.web;

import com.example.awsjpa.domain.posts.Posts;
import com.example.awsjpa.domain.posts.PostsRepository;
import com.example.awsjpa.web.dto.PostsSaveRequestDto;
import com.example.awsjpa.web.dto.PostsUpdateRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.contentOf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

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

    // +@
    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;

    @Before // 매번 테스트가 시작되기 전 MockMvc 인스턴스를 생성
    public void setup(){
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }
    // -@

    @Test
    @WithMockUser(roles="USER")
    // 인증된 모의 사용자를 만들어서 사용
    // 이 어노테이션으로 인해 ROLE_USER 권한을 가진 사용자가
    // API를 요청하는 것과 동일한 효과를 냄
    // 하지만 여기까지만 했을 때 실행되지 않음
    // 그 이유는 @WithMockUser가 MockMvc에서만 동작하기 때문
    // 따라서 @SpringBootTest에서 MockMvc를 사용하는 코드를 작성 --> +@
    public void Posts_regist() throws Exception{
        String title = "title";
        String content = "content";
        PostsSaveRequestDto postsSaveRequestDto = PostsSaveRequestDto.builder()
                .title(title)
                .content(content)
                .author("author")
                .build();

        String url = "http://localhost:"+port+"/api/v1/posts";

//        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url,postsSaveRequestDto,Long.class);
//
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        //+@
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(postsSaveRequestDto)))
                .andExpect(status().isOk());
        // 생성된 MockMvc를 통해 API를 테스트한다.
        // Body영역은 문자열로 표현하기 위해 ObjectMapper를 통해 문자열 JSON으로 변환
        //-@
        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);


    }

    @Test
    @WithMockUser(roles = "USER")
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

//        HttpEntity<PostsUpdateRequestDto> requestDtoHttpEntity = new HttpEntity<>(rDto);
//
//        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestDtoHttpEntity, Long.class);
//
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(rDto)))
                .andExpect(status().isOk());

        List<Posts> array = postsRepository.findAll();

        assertThat(array.get(0).getContent()).isEqualTo(content);
        assertThat(array.get(0).getTitle()).isEqualTo(title);

    }

    @Test
    public void select() throws Exception{

    }
}
