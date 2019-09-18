package com.github.airbnb.controller.api;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bouncycastle.jcajce.provider.digest.Keccak;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.airbnb.common.ResponseCode;
import com.github.airbnb.dto.ResponseDTO;
import com.github.airbnb.dto.TradeDTO;
import com.github.airbnb.entity.ProductEntity;
import com.github.airbnb.entity.TradeEntity;
import com.github.airbnb.entity.UserEntity;
import com.github.airbnb.repository.ProductRepository;
import com.github.airbnb.repository.UserRepository;
import com.github.whitepin.sdk.contruct.FabricContruct;
import com.github.whitepin.sdk.whitepin.invocation.ChaincodeInvocation;

@RestController
@RequestMapping(value = "/trades")
public class TradeApiController {

	@Autowired
	private FabricContruct fabricContruct;

	@Autowired
	private ChaincodeInvocation chaincodeInvocation;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private UserRepository userRepository;

	@Value("whitepin.service-code")
	private String serviceCode;

	@Autowired
	Keccak.Digest256 sha3;

	@PostMapping(value = "/create")
	public ResponseEntity<ResponseDTO> createTrade(@RequestBody TradeDTO tradeDto) throws Exception {
		ResponseDTO responseDTO = new ResponseDTO();
		ProductEntity productEntity = productRepository.findById(Long.valueOf(tradeDto.getProductId())).get();
		UserEntity sellerUserEntity = productEntity.getUserEntity();

		String sellerTkn = sellerUserEntity.getToken();
		String sellerUserId = String.valueOf(sellerUserEntity.getId());

		String buyerUserId = tradeDto.getBuyerUserId();

		UserEntity buyerUserEntity = userRepository.findById(Long.valueOf(buyerUserId)).get();
		String buyerTkn = buyerUserEntity.getToken();

		String nowDate = getNowDate();

		String tradeString = nowDate + sellerUserId + buyerUserId;
		byte[] tradeHashbytes = sha3.digest(tradeString.getBytes(StandardCharsets.UTF_8));
		String whitepinTradeId = bytesToString(tradeHashbytes);

		TradeEntity tradeEntity = TradeEntity.builder().buyerUserId(buyerUserId).sellerUserId(sellerUserId)
				.productBuyStatus("구매완료").tradeDate(nowDate).whitepinTradeId(whitepinTradeId).build();

		productEntity.addTrade(tradeEntity);
		productRepository.save(productEntity);

		boolean createTrade = chaincodeInvocation.createTrade(fabricContruct.getChannel(), fabricContruct.getClient(),
				whitepinTradeId, serviceCode, sellerTkn, buyerTkn);

		if(createTrade) {
			responseDTO.setCode(ResponseCode.SUCCESSFUL);
			responseDTO.setMessage("구매하였습니다.");
		} else {
			responseDTO.setCode(ResponseCode.FAILED);
			responseDTO.setMessage("구매에 실패하였습니다.");
		}
		
		return ResponseEntity.ok().body(new ResponseDTO());
	}

	@PostMapping(value = "/close")
	public ResponseEntity<ResponseDTO> closeTrade() {
		return ResponseEntity.ok().body(new ResponseDTO());
	}

	private String bytesToString(byte[] a) {
		return new String(Hex.encode(a));
	}

	private String getNowDate() {
		Date now = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		return format.format(now);
	}
}
