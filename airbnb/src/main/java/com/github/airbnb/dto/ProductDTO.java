package com.github.airbnb.dto;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(description = "상품")
public class ProductDTO {
    private String id;
    private String title;
    private String info;
    private String address;
    private String price;
    private String image;
    private String userId;
    
    @ApiModelProperty(notes = "결과 코드")
    String responseCode;
    
    @ApiModelProperty(notes = "결과 메세지")
    String responseMessage;
    
    private List<EvaluationDTO> evaluations; 
    
    @Getter
    @Setter
    @Builder
    @ToString
    public static class EvaluationDTO {
        private String date;
        private String review;
        private String score1;
        private String score2;
        private String score3;
        private String score4;
        private String score5;
        private String scoreTotal;
    }
}
