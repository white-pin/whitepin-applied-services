package com.github.airbnb.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "airbnb_role"
        , indexes = {
        @Index(columnList="role", unique = true)
}
)
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    long id;

    @Column(name = "role")
    String role;

}
