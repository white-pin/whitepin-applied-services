package com.github.airbnb.component;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

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
                    .scoreTotal(trade.getEvaluationScoreTotal())
                    .review(trade.getEvaluationReview())
                    .date(trade.getEvaluationDate())
                    .build();
    }

}
