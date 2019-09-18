package com.github.airbnb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.airbnb.entity.TradeEntity;

@Repository
public interface TradeRepository extends JpaRepository<TradeEntity, Long> {
	List<TradeEntity> findByBuyerUserId(String buyerUserId);
	List<TradeEntity> findBySellerUserId(String sellerUserId);
}
