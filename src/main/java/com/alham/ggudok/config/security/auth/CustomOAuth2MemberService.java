package com.alham.ggudok.config.security.auth;

import com.alham.ggudok.entity.member.Member;
import com.alham.ggudok.repository.security.MemberSecurityRepository;
import com.alham.ggudok.service.member.MemberOAuth2Service;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2MemberService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final MemberOAuth2Service memberOAuth2Service;
    private final MemberSecurityRepository memberSecurityRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);


        //현재 로그인 진행중인 서비스를 구분하는 코드
        //어떤 서비스인지 구분하는코드
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Type type = checkType(registrationId);

        //유저 요청에서 가져온 유저이름?
        //OAuth2 로그인 진행시 키가 되는 필드값, Primary key와 같은의미
        //구글의 경우 기본코드를 지원하지만 네이버나 카카오등을 지원하지 않기에 동시지원시 사용
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(type, userNameAttributeName, oAuth2User.getAttributes());

        Member member = findMemberOAuth(attributes);


        return new CustomOAuth2User(Collections.singleton(new SimpleGrantedAuthority(member.getRole().getKey())),
                oAuth2User.getAttributes(),
                attributes.getNameAttributeKey(),
                member.getLoginId(),
                member.getRole());

    }

    private Member findMemberOAuth(OAuthAttributes attributes) {
        String loginId = attributes.getOAuthUserInfo().getloginId();

        //로그인아이디 생성후 -> 이름 , 나이, 성별 수정
        return memberOAuth2Service.findMemberOAuth(loginId);

    }

    private OAuth2Type checkType(String type) {
        if (type.equals("naver")) {
            return OAuth2Type.NAVER;
        }
        else if (type.equals("kakao")) {
            return OAuth2Type.KAKAO;
        }
        else{
            return OAuth2Type.GOOGLE;
        }
    }
}