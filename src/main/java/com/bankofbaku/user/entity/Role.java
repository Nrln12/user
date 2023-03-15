package com.bankofbaku.user.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="role")
public class Role {
    @Id
    @Column(name="role_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long roleId;
    private String name;
    private boolean status=true;

}
