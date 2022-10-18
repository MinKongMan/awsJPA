package com.example.awsjpa.config.auth;

import com.example.awsjpa.config.auth.dto.OAuthAttributes;
import com.example.awsjpa.config.auth.dto.SessionUser;
import com.example.awsjpa.domain.user.Role;
import com.example.awsjpa.domain.user.User;
import com.example.awsjpa.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;


// 구글 로그인 이후 가져온 사용자의 정보(email, name, picture)들을 기반으로 가입 및 정보수정, 세션 저장 등의 기능을 지원
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;


    @Override
    // 유저를 불러오면 판단해야하기 때문에 재정의 필요
    // 로그인이 인증 실패되어도 동작하는 서비스에 영향을 주면 안되기 때문에 예외처리
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        // 현재 로그인 진행 중인 서비스를 구분하는 코드
        // 지금은 구글만 사용하는 불필요한 값이지만, 이후 네이버 로그인 연동 시에 네이버 로그인인지, 구글 로그인인지 구분하기 위해 사용

        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                                        .getUserInfoEndpoint().getUserNameAttributeName();
        // OAuth2 로그인 진행 시 키가 되는 필드값을 이야기 함.
        // Primary Key와 같은 의미
        // 구글의 경우 기본적으로 코드를 지원하지만, 네이버 카카오 등은 기본 지원하지 않음. 구글의 기본 코드는 "sub"
        // 이후 네이버 로그인과 구글 로그인을 동시 지원할 때 사용


        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
        // OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담을 클래스
        // 이후 네이버 등 다른 소셜 로그인도 이 클래스를 사용
        // 바로 아래에서 이 클래스의 코드가 나오니 차례로 생성

        User user = saveOrUpdate(attributes);

        httpSession.setAttribute("user",new SessionUser(user));
        // 세션에 사용자 정보를 저장하기 위한 Dto클래스
        // User클래스를 쓰지 않고 새로 만들어서 쓰는지?
        // -> User 클래스에 직렬화를 구현하지 않았기 때문에 에러가 발생함.
        // User 클래스는 엔티티기 때문에 직렬화 대상에 자식들까지 포함되니 성능이슈, 부수효과가 발생할 확률이 높다.
        // 따라서 따로 Dto를 만드는 것이 운영 및 유지보수에 도움이 된다.


        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(), attributes.getNameAttributeKey());
    }

    private User saveOrUpdate(OAuthAttributes attributes){
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());
        return userRepository.save(user);
    }
}

