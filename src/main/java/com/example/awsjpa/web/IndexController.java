package com.example.awsjpa.web;

import com.example.awsjpa.config.auth.LoginUser;
import com.example.awsjpa.config.auth.dto.SessionUser;
import com.example.awsjpa.domain.user.UserRepository;
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
//    @GetMapping("/") // 개선되기 전
//    public String index(Model model){ // Model : 서버 템플릿 엔진에서 사용할 수 있는 객체를 저장할 수 있음
//                                    // 여기서는 postsService.findAllDesc()로 가져온 결과를 posts로 index.mustache에 전달
//        model.addAttribute("posts",postsService.findAllDesc());
//
//        SessionUser user = (SessionUser) httpSession.getAttribute("user"); 개선 사항에서 이 부분이 사라질 예정
//
//        if(user!=null) {
//            model.addAttribute("userName",user.getName());
//        }
//
//        return "index";
//    }
    @GetMapping("/") // 개선된 후
    public String index(Model model, @LoginUser SessionUser user){
        model.addAttribute("posts",postsService.findAllDesc());

        if(user!=null) {
            model.addAttribute("userName",user.getName());
            model.addAttribute("Name",user.getName());
            System.out.println(model);
        }

        return "index";
    }


    @GetMapping("/posts/save")
    public String posts(Model model){
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String update(@PathVariable Long id, Model model){
        PostsResponseDto prd = postsService.findById(id);
        model.addAttribute("post",prd);

        return "posts-update";
    }



}
