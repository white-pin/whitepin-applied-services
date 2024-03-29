package com.github.airbnb.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.util.CollectionUtils;

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
@Table(name = "airbnb_product")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    long id;

    @Column(name = "title")
    String title;
    
    @Column(name = "info")
    String info;
    
    @Column(name = "address")
    String address;
    
    @Column(name = "price")
    String price;
    
    @Column(name = "image")
    String image;
    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private List<TradeEntity> trades = new ArrayList<TradeEntity>();
    
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;
    
    public void addTrade(TradeEntity tradeEntity){
        if(CollectionUtils.isEmpty(this.trades)){
            this.trades = new ArrayList<>();
        }
        this.trades.add(tradeEntity);
    }
}
