package com.example.awsjpa.domain.posts;

import com.example.awsjpa.web.dto.poRequestDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class potest {

    @Autowired
    poRe postsRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port_id;

    @Test
    public void test(){
        String title = "기뮤난입니다";
        String content = "혜로로 1승";
        String author = "아프리카";

        for(int i = 1; i<=10; i++) {
            postsRepository.save(
                    po.builder().title(title+" "+i)
                            .content(content)
                            .author(author)
                            .build());
        }

        List<po> linkedList = postsRepository.findAll();

        for(po a : linkedList){
            System.out.println(a.getId()+" "+a.getAuthor()+" "+a.getTitle()+" "+a.getContent());
        }
    }

    @Test
    public void save() throws Exception{
        String title = "기뮤난";
        String author = "혜로로";
        String content = "결혼";

        poRequestDto dto = null;
        ResponseEntity<Long> responseEntity = null;
        String url = "http://localhost:"+port_id+"/api/v2/posts";
        for(int i = 1; i<=10; i++) {
            dto = poRequestDto.builder().title(title+" "+i)
                            .content(content)
                            .author(author)
                            .build();
            responseEntity = restTemplate.postForEntity(url,dto,Long.class);
        }



        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(HttpStatus.OK).isEqualTo(responseEntity.getStatusCode());

        List<po> list = postsRepository.findAll();

        for(po hyo : list){
            System.out.println(hyo.getId()+" "+hyo.getTitle()+" "+responseEntity.getBody());
        }

    }


    @Test
    public void find() throws Exception{

    }
}
