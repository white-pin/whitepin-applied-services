package com.github.airbnb.controller.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.airbnb.common.ResponseCode;
import com.github.airbnb.common.ResponseSetter;
import com.github.airbnb.dto.UserDTO;
import com.github.airbnb.entity.UserEntity;
import com.github.airbnb.repository.UserRepository;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value = "/auth")
public class AuthApiController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	private Converter<UserEntity, UserDTO> userConverter;

	@PostMapping(value = "/login", consumes = { "application/json" })
	@ApiOperation(value = "login", notes = "airbnb login")
	public ResponseEntity<UserDTO> login(@RequestBody UserDTO userDto) {
		String email = userDto.getEmail();
		String password = userDto.getPassword();

		Optional<UserEntity> userEntityOptional = userRepository.findByEmailAndPassword(email, password);
		if (!userEntityOptional.isPresent()) {
			ResponseSetter.setResponse(userDto, ResponseCode.FAILED, "ID/PASSWORD가 잘못되었습니다.");
		} else {
			UserEntity userEntity = userEntityOptional.get();
			userDto = userConverter.convert(userEntity);
			ResponseSetter.setResponse(userDto, ResponseCode.SUCCESSFUL, "로그인에 성공하였습니다.");
		}

		return ResponseEntity.ok().body(userDto);
	}

	@PostMapping(value = "/logout")
	public ResponseEntity<UserDTO> logout(@RequestBody UserDTO userDto) {
		// TODO:: 로그아웃 로직
		// joinService.
		return ResponseEntity.ok().body(new UserDTO());
	}
}
