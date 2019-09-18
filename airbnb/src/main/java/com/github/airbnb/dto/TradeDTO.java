package com.github.airbnb.dto;

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
@ApiModel(description = "거래")
public class TradeDTO {
	@ApiModelProperty(notes = "상품 ID")
    private String productId;
	
	@ApiModelProperty(notes = "구매자 ID")
    private String buyerUserId;
	
	@ApiModelProperty(notes = "주문일")
	private String tradeDate;
	
	@ApiModelProperty(notes = "주문번호")
	private String tradeId;
	
	@ApiModelProperty(notes = "주문자")
	private String buyerUserName;
	
	@ApiModelProperty(notes = "상품명")
	private String productName;
	
	@ApiModelProperty(notes = "가격")
	private String productPrice;
	
	@ApiModelProperty(notes = "결제상태")
	private String productBuyStatus;
	
	@ApiModelProperty(notes = "평가여부")
	private String evaluationYn;
}
