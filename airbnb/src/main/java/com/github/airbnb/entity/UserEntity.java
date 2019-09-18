package com.github.airbnb.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
@Table(name = "airbnb_user", indexes = { @Index(columnList = "email", unique = true) })
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(name = "first_name")
    String firstName;

    @Column(name = "last_name")
    String lastName;

    @Column(name = "sex")
    String sex;

    @Column(name = "birthday")
    String birthday;

    @Column(name = "email")
    String email;

    @Column(name = "telephone")
    String telephone;

    @Column(name = "identity_yn")
    String identityYn;

    @Column(name = "language")
    String language;

    @Column(name = "monetary_unit")
    String monetaryUnit;

    @Column(name = "country")
    String country;

    @Column(name = "self_info")
    String selfInfo;

    @Column(name = "join_date")
    String joinDate;

    @Column(name = "password")
    String password;

    @Column(name = "whitepin_token")
    String token;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private List<ProductEntity> products;
}
