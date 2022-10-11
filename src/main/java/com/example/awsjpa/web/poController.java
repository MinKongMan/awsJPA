package com.example.awsjpa.web;

import com.example.awsjpa.domain.posts.poRe;
import com.example.awsjpa.service.posts.poService;
import com.example.awsjpa.service.posts.pracService;
import com.example.awsjpa.web.dto.poRequestDto;
import com.example.awsjpa.web.dto.poResponseDto;
import com.example.awsjpa.web.dto.postDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class poController {

    private final poService poService;
    private final pracService pore;

    @PostMapping("/posts/v3/save")
    public Long save(@RequestBody postDto p){
        return pore.save(p);
    }

    @PostMapping("/api/v2/posts")
    public Long post(@RequestBody poRequestDto p){

        System.out.println("ㅋㅋ?");

        return poService.save(p);
    }

    @GetMapping("/api/v2/gets/{id}")
    public String get(@PathVariable long id){
        return poService.select(id);
    }

}
