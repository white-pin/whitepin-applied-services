package com.github.airbnb.controller.api;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bouncycastle.jcajce.provider.digest.Keccak;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.codec.Hex;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.airbnb.common.ResponseCode;
import com.github.airbnb.dto.ResponseDTO;
import com.github.airbnb.dto.TradeDTO;
import com.github.airbnb.dto.TradeDTO.TradeDTOBuilder;
import com.github.airbnb.entity.ProductEntity;
import com.github.airbnb.entity.TradeEntity;
import com.github.airbnb.entity.TradeEntity.TradeEntityBuilder;
import com.github.airbnb.entity.UserEntity;
import com.github.airbnb.repository.ProductRepository;
import com.github.airbnb.repository.TradeRepository;
import com.github.airbnb.repository.UserRepository;
import com.github.whitepin.sdk.contruct.FabricContruct;
import com.github.whitepin.sdk.whitepin.invocation.ChaincodeInvocation;

@RestController
public class TradeApiController {

	@Autowired
	private FabricContruct fabricContruct;

	@Autowired
	private ChaincodeInvocation chaincodeInvocation;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TradeRepository tradeRepository;

	@Value("whitepin.service-code")
	private String serviceCode;

	@Autowired
	Keccak.Digest256 sha3;

	private final String CONDISION_BUY = "buy";
	private final String CONDISION_SELL = "sell";

	@GetMapping(value = "/trades/{userId}/condition/{type}")
	public ResponseEntity<List<TradeDTO>> getTrade(@PathVariable("userId") String id, @PathVariable("type") String type,
			@RequestBody TradeDTO tradeDto) throws Exception {
		List<TradeDTO> tradeDtos = new ArrayList<TradeDTO>();
		
		if (type.equals(CONDISION_BUY)) 
			tradeDtos = getTradeDTOs(tradeRepository.findByBuyerUserId(id));
		else if(type.equals(CONDISION_SELL))
			tradeDtos = getTradeDTOs(tradeRepository.findBySellerUserId(id));
		
		return ResponseEntity.ok().body(tradeDtos);
	}
	
	private List<TradeDTO> getTradeDTOs(List<TradeEntity> tradeEntitys) {
		List<TradeDTO> tradeDtos = new ArrayList<TradeDTO>();
		for (TradeEntity tradeEntity : tradeEntitys) {
			
			ProductEntity productEntity = tradeEntity.getProductEntity();
			UserEntity userEntity = userRepository.findById(Long.valueOf(tradeEntity.getBuyerUserId())).get();
			
			TradeDTOBuilder tradeDTOBuilder = TradeDTO.builder();
			tradeDTOBuilder.tradeDate(tradeEntity.getTradeDate());
			tradeDTOBuilder.tradeId(String.valueOf(tradeEntity.getId()));
			tradeDTOBuilder.buyerUserName(userEntity.getLastName() + userEntity.getFirstName());
			tradeDTOBuilder.productName(productEntity.getTitle());
			tradeDTOBuilder.productPrice(productEntity.getPrice());
			tradeDTOBuilder.productBuyStatus(tradeEntity.getProductBuyStatus());
			tradeDTOBuilder.evaluationYn(tradeEntity.getBuyerEvalYn());
			tradeDtos.add(tradeDTOBuilder.build());
		}
		return tradeDtos;
	}

	@PostMapping(value = "/trades/create")
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

		TradeEntityBuilder tradeEntitybuilder = TradeEntity.builder();
		tradeEntitybuilder.buyerUserId(buyerUserId);
		tradeEntitybuilder.sellerUserId(sellerUserId);
		tradeEntitybuilder.productBuyStatus("구매완료");
		tradeEntitybuilder.tradeDate(nowDate);
		tradeEntitybuilder.whitepinTradeId(whitepinTradeId);
		tradeEntitybuilder.buyerEvalYn("N");
		tradeEntitybuilder.sellerEvalYn("N");
		TradeEntity tradeEntity = tradeEntitybuilder.build();

		productEntity.addTrade(tradeEntity);
		productRepository.save(productEntity);

		boolean createTrade = chaincodeInvocation.createTrade(fabricContruct.getChannel(), fabricContruct.getClient(),
				whitepinTradeId, serviceCode, sellerTkn, buyerTkn);

		if (createTrade) {
			responseDTO.setCode(ResponseCode.SUCCESSFUL);
			responseDTO.setMessage("구매하였습니다.");
		} else {
			responseDTO.setCode(ResponseCode.FAILED);
			responseDTO.setMessage("구매에 실패하였습니다.");
		}

		return ResponseEntity.ok().body(new ResponseDTO());
	}

	@PostMapping(value = "/trades/close")
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
