package com.example.awsjpa.service.posts;

import com.example.awsjpa.domain.posts.po;
import com.example.awsjpa.domain.posts.poRe;
import com.example.awsjpa.web.dto.postDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class pracService {

    private final poRe poRe;

    @Transactional
    public long save(postDto p){
        return poRe.save(p.Entity()).getId();
    }

}
