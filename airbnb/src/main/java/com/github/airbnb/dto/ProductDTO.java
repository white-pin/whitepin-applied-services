package com.github.airbnb.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

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
@XmlRootElement(name="product")
@XmlAccessorType(XmlAccessType.FIELD)
@ApiModel(description = "상품")
public class ProductDTO {
    private String productId;
    private String productName;
    private String productInfo;
    private String userId;
    private List<Evaluation> evaluations; 
    
    @Getter
    @Setter
    @Builder
    @ToString
    public static class Evaluation {
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
