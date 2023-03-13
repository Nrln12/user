package com.bankofbaku.user.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="role")
public class Role {
    @Id
    @Column(name="role_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long roleId;
    private String name;
    private boolean status;

}
