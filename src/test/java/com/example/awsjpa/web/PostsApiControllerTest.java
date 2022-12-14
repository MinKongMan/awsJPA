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

    @Before // ?????? ???????????? ???????????? ??? MockMvc ??????????????? ??????
    public void setup(){
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }
    // -@

    @Test
    @WithMockUser(roles="USER")
    // ????????? ?????? ???????????? ???????????? ??????
    // ??? ????????????????????? ?????? ROLE_USER ????????? ?????? ????????????
    // API??? ???????????? ?????? ????????? ????????? ???
    // ????????? ??????????????? ?????? ??? ???????????? ??????
    // ??? ????????? @WithMockUser??? MockMvc????????? ???????????? ??????
    // ????????? @SpringBootTest?????? MockMvc??? ???????????? ????????? ?????? --> +@
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
        // ????????? MockMvc??? ?????? API??? ???????????????.
        // Body????????? ???????????? ???????????? ?????? ObjectMapper??? ?????? ????????? JSON?????? ??????
        //-@
        List<Posts> all = postsRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo(title);
        assertThat(all.get(0).getContent()).isEqualTo(content);


    }

    @Test
    @WithMockUser(roles = "USER")
    public void update() throws Exception{

        Posts posts = postsRepository.save(Posts.builder().title("?????????")
                .content("?????????")
                .author("asl")
                .build());

        Long id = posts.getId();
        String title = "?????????";
        String content = "?????????";

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
