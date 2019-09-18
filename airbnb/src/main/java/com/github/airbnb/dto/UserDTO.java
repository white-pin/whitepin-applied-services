package com.github.airbnb.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.Column;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@XmlRootElement(name="user")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel(description = "회원")
public class UserDTO {
    
    @ApiModelProperty(notes = "회원 Key ID")
    String id;
    
    @ApiModelProperty(notes = "이름")
    String firstName;
    
    @ApiModelProperty(notes = "성")
    String lastName;

    @ApiModelProperty(notes = "성별")
    String sex;
    
    @ApiModelProperty(notes = "생년월일")
    String birthday;
    
    @ApiModelProperty(notes = "이메일")
    String email;
    
    @ApiModelProperty(notes = "전화번호")
    String telephone;
    
    @ApiModelProperty(notes = "신분증 확인여부")
    String identityYn;

    @ApiModelProperty(notes = "선호하는 언어")
    String language;
    
    @ApiModelProperty(notes = "선호하는 통화 단위")
    String monetaryUnit;
    
    @ApiModelProperty(notes = "거주 국가")
    String country;
    
    @ApiModelProperty(notes = "자기소개")
    String selfInfo;
    
    @ApiModelProperty(notes = "회원가입 날짜")
    String joinDate;
    
    @ApiModelProperty(notes = "비밀번호")
    String password;

    @ApiModelProperty(notes = "유저 토큰")
    String token;
    
    @ApiModelProperty(notes = "판매 점수 평균")
    String sellTotEvalAvg;
    
    @ApiModelProperty(notes = "거래안정성 평균")
    String sellEvalAvg1;
    
    @ApiModelProperty(notes = "서비스/태도 평균")
    String sellEvalAvg2;
    
    @ApiModelProperty(notes = "지속가능성 평균")
    String sellEvalAvg3;
    
}
