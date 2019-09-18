package com.github.airbnb.controller.api;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;

import com.github.airbnb.dto.ResponseDTO;
import com.github.airbnb.dto.UserDTO;
import com.github.airbnb.entity.UserEntity;
import com.github.airbnb.repository.UserRepository;
import com.github.whitepin.sdk.contruct.FabricContruct;
import com.github.whitepin.sdk.whitepin.invocation.ChaincodeInvocation;
import com.github.whitepin.sdk.whitepin.vo.UserVo;
import com.github.whitepin.sdk.whitepin.vo.UserVo.SellAvg;

@RestController
public class UserApiController {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private Converter<UserEntity, UserDTO> userConverter;
    
    @Autowired
    private FabricContruct fabricContruct;
    
    @Autowired
    private ChaincodeInvocation chaincodeInvocation;

    @GetMapping(value = "/users/{userId}")
    public ResponseEntity<UserDTO> userInfo(@PathVariable("userId") long userId) {
        UserEntity userEntity = userRepository.findById(userId).get();
        UserDTO user = userConverter.convert(userEntity);
        return ResponseEntity.ok().body(user);
    }

    @PutMapping(value = "/users/{userId}")
    public ResponseEntity<ResponseDTO> userUpdate(@PathVariable("userId") String id, @RequestBody UserDTO userDto) {
        HttpStatus httpStatus;
        ResponseDTO responseDTO = new ResponseDTO();
        if(StringUtils.isEmpty(id)) {
            httpStatus = HttpStatus.BAD_REQUEST;
            responseDTO.setMessage("회원 정보 변경에 실패하였습니다.");
        } else {
        	UserEntity userEntity = userRepository.findById(Long.valueOf(id)).get();
        	
        	userEntity.setFirstName(userDto.getFirstName());
        	userEntity.setLastName(userDto.getLastName());
        	userEntity.setSex(userDto.getSex());
        	userEntity.setBirthday(userDto.getBirthday());
        	userEntity.setEmail(userDto.getEmail());
        	userEntity.setTelephone(userDto.getTelephone());
        	userEntity.setIdentityYn(userDto.getIdentityYn());
        	userEntity.setLanguage(userDto.getLanguage());
        	userEntity.setMonetaryUnit(userDto.getMonetaryUnit());
        	userEntity.setCountry(userDto.getCountry());
        	userEntity.setSelfInfo(userDto.getSelfInfo());
        	userEntity.setToken(userDto.getToken());
        	
        	userRepository.save(userEntity);
            
            httpStatus = HttpStatus.OK;
            responseDTO.setMessage("회원 정보 변경에 성공하였습니다.");
        }
        return new ResponseEntity<ResponseDTO>(responseDTO, httpStatus);
    }

    @GetMapping(value = "/users/score/{userId}")
    public ResponseEntity<UserDTO> userScore(@PathVariable("userId") long id) throws Exception {
    	Function<Double, String> dsFunction = (d) -> String.valueOf(d);
        UserEntity userEntity = userRepository.findById(id).get();
        
        UserVo userVo = chaincodeInvocation.queryUser(fabricContruct.getChannel(), fabricContruct.getClient(), userEntity.getToken());
        SellAvg sellAvg = userVo.getSellAvg();
        UserDTO user = userConverter.convert(userEntity);
        
        user.setSellTotEvalAvg(dsFunction.apply(sellAvg.getTotAvg()));
        user.setSellEvalAvg1(dsFunction.apply(sellAvg.getEvalAvg1()));
        user.setSellEvalAvg2(dsFunction.apply(sellAvg.getEvalAvg2()));
        user.setSellEvalAvg3(dsFunction.apply(sellAvg.getEvalAvg3()));
        
        return ResponseEntity.ok().body(user);
    } 
}
