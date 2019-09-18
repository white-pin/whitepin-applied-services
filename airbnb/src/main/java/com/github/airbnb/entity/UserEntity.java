package com.github.airbnb.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.CollectionUtils;

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
    @Column(name = "user_id")
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

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "airbnb_user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id")
            , indexes = {
            @Index(columnList="user_id,role_id", unique = true)
    }
    )
    private List<RoleEntity> roles = new ArrayList<>();

    public void addRole(RoleEntity roleEntity){
        if(CollectionUtils.isEmpty(this.roles)){
            this.roles = new ArrayList<>();
        }
        this.roles.add(roleEntity);
    }
}
