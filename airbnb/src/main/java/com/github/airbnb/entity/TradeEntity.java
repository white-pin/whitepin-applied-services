package com.github.airbnb.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "airbnb_trade")
public class TradeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(name = "buyer_user_id")
    String buyerUserId;

    @Column(name = "seller_user_id")
    String sellerUserId;

    @Column(name = "product_buy_status")
    String productBuyStatus;

    @Column(name = "whitepin_trade_id")
    String whitepinTradeId;

    @Column(name = "trade_date")
    String tradeDate;
    
    @Column(name = "seller_evaluation_yn")
    String sellerEvalYn;
    
    @Column(name = "buyer_evaluation_yn")
    String buyerEvalYn;

    @Column(name = "evaluation_score1")
    String evaluationScore1;

    @Column(name = "evaluation_score2")
    String evaluationScore2;

    @Column(name = "evaluation_score3")
    String evaluationScore3;

    @Column(name = "evaluation_score4")
    String evaluationScore4;

    @Column(name = "evaluation_score5")
    String evaluationScore5;

    @Column(name = "evaluation_review")
    String evaluationReview;

    @Column(name = "whitepin_score_key")
    String whitepinScoreKey;

    @Column(name = "evaluation_date")
    String evaluationDate;
    
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private ProductEntity productEntity;
}
