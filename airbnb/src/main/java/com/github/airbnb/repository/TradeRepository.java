package com.github.airbnb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.airbnb.entity.TradeEntity;
import com.github.airbnb.entity.UserEntity;

@Repository
public interface TradeRepository extends JpaRepository<TradeEntity, Long> {
}
