package com.example.awsjpa.service.posts;

import com.example.awsjpa.domain.posts.Posts;
import com.example.awsjpa.domain.posts.po;
import com.example.awsjpa.domain.posts.poRe;
import com.example.awsjpa.web.dto.PostsResponseDto;
import com.example.awsjpa.web.dto.poRequestDto;
import com.example.awsjpa.web.dto.poResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class poService {

    final private poRe pore;

    @Transactional
    public Long save(poRequestDto p){
        return pore.save(p.toEntity()).getId();
    }


    public String select(Long id){
        po answer = pore.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("해당 게시글이 없습니다. id : "+id)
        );

        return new poResponseDto(answer).getTitle();
    }

}
