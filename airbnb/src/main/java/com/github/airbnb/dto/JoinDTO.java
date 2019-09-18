package com.github.airbnb.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@ApiModel(description = "가입")
public class JoinDTO {
    // 이메일
    private String email;

    // 성
    private String lastName;
    
    // 이름
    private String firstName;
    
    // 성
    private String sex;
    
    // 생일
    private String birthday;
    
    // 비밀번호
    private String password;
    
    // 화이트 핀 토큰
    private String whitepinToken;
    
    // 전화번호
    private String phone;
    
    // 선호하는 언어
    private String language;
    
    // 선호하는 통화 단위
    private String monetaryUnit;
    
    // 거주 도시
    private String county;
    
    // 자기소개
    private String info;
}
