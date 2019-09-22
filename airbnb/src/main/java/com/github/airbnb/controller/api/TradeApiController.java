package com.github.airbnb.controller.api;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.airbnb.common.ProductBuyStatusCode;
import com.github.airbnb.common.ResponseCode;
import com.github.airbnb.common.ResponseSetter;
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

import io.swagger.annotations.ApiOperation;

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

	@Value("${whitepin.service-code}")
	private String serviceCode;

	@Autowired
	Keccak.Digest256 sha3;

	private final String CONDISION_BUY = "buy";
	private final String CONDISION_SELL = "sell";

	@GetMapping(value = "/trades/{userId}/condition/{type}")
	@ApiOperation(value = "trades", notes = "검색 조건에 맞는 거래 리스트 조회 ( userId :: 사용자 ID, type :: 'buy' or 'sell'")
	public ResponseEntity<List<TradeDTO>> trades(@PathVariable("userId") String id, @PathVariable("type") String type) throws Exception {
		List<TradeDTO> tradeDtos = new ArrayList<TradeDTO>();
		tradeDtos = getTradeDTOs(id, type);
		return ResponseEntity.ok().body(tradeDtos);
	}
	
	private List<TradeDTO> getTradeDTOs(String id, String type) {
		List<TradeDTO> tradeDtos = new ArrayList<TradeDTO>();
		List<TradeEntity> tradeEntitys = new ArrayList<TradeEntity>();
		if(CONDISION_BUY.equals(type)) 
			tradeEntitys = tradeRepository.findByBuyerUserId(id);
		else if(CONDISION_SELL.equals(type))
			tradeEntitys = tradeRepository.findBySellerUserId(id);
		
		for (TradeEntity tradeEntity : tradeEntitys) {
			
			ProductEntity productEntity = tradeEntity.getProductEntity();
			UserEntity userEntity = userRepository.findById(Long.valueOf(tradeEntity.getBuyerUserId())).get();
			
			TradeDTOBuilder tradeDTOBuilder = TradeDTO.builder();
			tradeDTOBuilder.tradeDate(tradeEntity.getTradeDate());
			tradeDTOBuilder.tradeId(String.valueOf(tradeEntity.getId()));
			tradeDTOBuilder.buyerUserId(String.valueOf(userEntity.getId()));
			tradeDTOBuilder.buyerUserName(userEntity.getLastName() + userEntity.getFirstName());
			tradeDTOBuilder.productName(productEntity.getTitle());
			tradeDTOBuilder.productPrice(productEntity.getPrice());
			tradeDTOBuilder.productBuyStatus(tradeEntity.getProductBuyStatus());
			if(CONDISION_BUY.equals(type)) 
				tradeDTOBuilder.evaluationYn(tradeEntity.getBuyerEvalYn());
			else if(CONDISION_SELL.equals(type))
				tradeDTOBuilder.evaluationYn(tradeEntity.getSellerEvalYn());
			
			tradeDtos.add(tradeDTOBuilder.build());
		}
		return tradeDtos;
	}

	@PostMapping(value = "/trades/create" , consumes = {"application/json"})
	@ApiOperation(value = "createTrade", notes = "거래 생성")
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
		tradeEntitybuilder.productBuyStatus(ProductBuyStatusCode.BUY_COMPLETE);
		tradeEntitybuilder.tradeDate(nowDate);
		tradeEntitybuilder.whitepinTradeId(whitepinTradeId);
		tradeEntitybuilder.buyerEvalYn("N");
		tradeEntitybuilder.sellerEvalYn("N");
		TradeEntity tradeEntity = tradeEntitybuilder.build();

		productEntity.addTrade(tradeEntity);
		productRepository.save(productEntity);

		boolean createTrade = chaincodeInvocation.createTrade(fabricContruct.getChannel(), fabricContruct.getClient(),
				whitepinTradeId, serviceCode, sellerTkn, buyerTkn);

		if (createTrade) 
			ResponseSetter.setResponse(responseDTO, ResponseCode.SUCCESSFUL, "구매하였습니다.");
		else
			ResponseSetter.setResponse(responseDTO, ResponseCode.FAILED, "구매에 실패하였습니다.");

		return ResponseEntity.ok().body(responseDTO);
	}

	@PutMapping(value = "/trades/close/{tradeId}/users/{userId}")
	@ApiOperation(value = "closeTrade", notes = "조건에 맞는 거래 완료 ( 판매자 또는 구매자 )")
	public ResponseEntity<ResponseDTO> closeTrade(@PathVariable("tradeId") String tradeId, @PathVariable("userId") String userId) throws Exception {
		ResponseDTO responseDTO = new ResponseDTO();
		
		TradeEntity tradeEntity = tradeRepository.findById(Long.valueOf(tradeId)).get();
		tradeEntity.setProductBuyStatus(ProductBuyStatusCode.BUY_CONFIRM);
		tradeRepository.save(tradeEntity);
		
		UserEntity userEntity = userRepository.findById(Long.valueOf(userId)).get();
		
		boolean closeTrade = chaincodeInvocation.closeTrade(fabricContruct.getChannel(), fabricContruct.getClient(), tradeEntity.getWhitepinTradeId(), userEntity.getToken());
		
		if (closeTrade) 
			ResponseSetter.setResponse(responseDTO, ResponseCode.SUCCESSFUL, "구매확정 성공!!!");
		else
			ResponseSetter.setResponse(responseDTO, ResponseCode.FAILED, "구매 확정 실패!!!");
		
		return ResponseEntity.ok().body(responseDTO);
	}
	
	@PostMapping(value = "/trades/evaluation")
	@ApiOperation(value = "tradeEvaluation", notes = "거래 임시 평가 등록 ( 판매자 또는 구마자 )")
	public ResponseEntity<ResponseDTO> tradeEvaluation(@RequestBody TradeDTO tradeDto) throws Exception {
		ResponseDTO responseDTO = new ResponseDTO();
		TradeEntity tradeEntity = tradeRepository.findById(Long.valueOf(tradeDto.getTradeId())).get();
		String scoreOrigin = Arrays.asList(tradeDto.getWhitepinEvaluationScore1(), tradeDto.getWhitepinEvaluationScore2(), tradeDto.getWhitepinEvaluationScore3()).toString().replaceAll("\\p{Z}", "");
		String userId = "";
		
		//구매 평가 남길때에만 DB에 airbnb 평가 등록
		if(CONDISION_BUY.equals(tradeDto.getCondisionType())) {
			userId = tradeEntity.getBuyerUserId();
			tradeEntity.setBuyerEvalYn("Y");
			tradeEntity.setEvaluationDate(getNowDate());
			tradeEntity.setEvaluationScore1(tradeDto.getEvaluationScore1());
			tradeEntity.setEvaluationScore2(tradeDto.getEvaluationScore2());
			tradeEntity.setEvaluationScore3(tradeDto.getEvaluationScore3());
			tradeEntity.setEvaluationScore4(tradeDto.getEvaluationScore4());
			tradeEntity.setEvaluationScore5(tradeDto.getEvaluationScore5());
			tradeEntity.setEvaluationReview(tradeDto.getEvaluationReview());
		} else if(CONDISION_SELL.equals(tradeDto.getCondisionType())) {
			userId = tradeEntity.getSellerUserId();
			tradeEntity.setSellerEvalYn("Y");
		}
		
		String userTkn = userRepository.findById(Long.valueOf(userId)).get().getToken();
		boolean enrollTempScore = chaincodeInvocation.enrollTempScore(fabricContruct.getChannel(), fabricContruct.getClient(), tradeEntity.getWhitepinTradeId(), scoreOrigin, userTkn);
		
		if(!enrollTempScore) {
			ResponseSetter.setResponse(responseDTO, ResponseCode.FAILED, "임시 평가 점수 등록 실패!!");
		} else {
			tradeRepository.save(tradeEntity);
			ResponseSetter.setResponse(responseDTO, ResponseCode.SUCCESSFUL, "임시 평가 점수 등록 성공!!");
		}
		return ResponseEntity.ok().body(responseDTO);
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
