package com.github.airbnb.component;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.github.airbnb.dto.ProductDTO.EvaluationDTO;
import com.github.airbnb.entity.TradeEntity;

@Component
public class TradeEntityToEvaluationDTO implements Converter<TradeEntity, EvaluationDTO> {

    @Override
    public EvaluationDTO convert(TradeEntity trade) {
        return EvaluationDTO.builder()
                    .score1(trade.getEvaluationScore1())
                    .score2(trade.getEvaluationScore2())
                    .score3(trade.getEvaluationScore3())
                    .score4(trade.getEvaluationScore4())
                    .score5(trade.getEvaluationScore5())
                    .scoreTotal(getTotal(trade))
                    .review(trade.getEvaluationReview())
                    .date(trade.getEvaluationDate())
                    .build();
    }
    
    private String getTotal(TradeEntity trade) {
    	int totalScore = chkEmpty(trade.getEvaluationScore1());
    	totalScore += chkEmpty(trade.getEvaluationScore2());
    	totalScore += chkEmpty(trade.getEvaluationScore3());
    	totalScore += chkEmpty(trade.getEvaluationScore4());
    	totalScore += chkEmpty(trade.getEvaluationScore5());
    	return String.valueOf(totalScore);
    }
    
    private int chkEmpty(String s) {
    	return StringUtils.isEmpty(s) ? 0 : Integer.parseInt(s);
    }
}
