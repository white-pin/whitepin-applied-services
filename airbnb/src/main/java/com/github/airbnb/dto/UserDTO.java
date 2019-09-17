package com.github.airbnb.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

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

    @ApiModelProperty(notes = "이름")
    String name;

    @ApiModelProperty(notes = "이메일")
    String email;
    
    @ApiModelProperty(notes = "비밀번호")
    String password;

    @ApiModelProperty(notes = "사용자 토큰")
    String userToken;
}
