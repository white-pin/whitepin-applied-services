package com.github.airbnb.common;

import com.github.airbnb.dto.ProductDTO;
import com.github.airbnb.dto.ResponseDTO;
import com.github.airbnb.dto.TradeDTO;
import com.github.airbnb.dto.UserDTO;

public class ResponseSetter {
	public static void setResponse(UserDTO userDto, String code, String message) {
		userDto.setResponseCode(code);
		userDto.setResponseMessage(message);
		userDto.setPassword("");
	}
	
	public static void setResponse(TradeDTO tradeDto, String code, String message) {
		tradeDto.setResponseCode(code);
		tradeDto.setResponseMessage(message);
	}
	
	public static void setResponse(ProductDTO productDto, String code, String message) {
		productDto.setResponseCode(code);
		productDto.setResponseMessage(message);
	}
	
	public static void setResponse(ResponseDTO responseDto, String code, String message) {
		responseDto.setCode(code);
		responseDto.setMessage(message);
	}
}
