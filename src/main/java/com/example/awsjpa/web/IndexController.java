package com.example.awsjpa.web;

import com.example.awsjpa.config.auth.dto.SessionUser;
import com.example.awsjpa.service.posts.PostsService;
import com.example.awsjpa.service.posts.pracService;
import com.example.awsjpa.web.dto.PostsResponseDto;
import com.example.awsjpa.web.dto.postDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;
    @GetMapping("/")
    public String index(Model model){ // Model : 서버 템플릿 엔진에서 사용할 수 있는 객체를 저장할 수 있음
                                    // 여기서는 postsService.findAllDesc()로 가져온 결과를 posts로 index.mustache에 전달
        model.addAttribute("posts",postsService.findAllDesc());

        SessionUser user = (SessionUser) httpSession.getAttribute("user");

        if(user!=null) model.addAttribute("userName",user.getName());
        return "index";
    }

    @GetMapping("/posts/save")
    public String posts(){
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String update(@PathVariable Long id, Model model){
        PostsResponseDto prd = postsService.findById(id);
        model.addAttribute("post",prd);

        return "posts-update";
    }



}
