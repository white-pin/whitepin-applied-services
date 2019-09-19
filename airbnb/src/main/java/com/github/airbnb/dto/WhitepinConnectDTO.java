package com.github.airbnb.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@ApiModel(description = "whitepin 연동")
public class WhitepinConnectDTO {

    @ApiModelProperty(notes = "서비스 회원 아이디(DB KEY)")
    long id;

    @ApiModelProperty(notes = "whitepin 로그인 아이디")
    String whitepinId;

    @ApiModelProperty(notes = "whitepin 로그인 비밀번호")
    String whitepinPassword;

}
