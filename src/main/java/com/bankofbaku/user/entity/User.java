package com.bankofbaku.user.entity;

import com.bankofbaku.user.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="user")
@NamedStoredProcedureQueries({
        @NamedStoredProcedureQuery(name="getAllUsers",procedureName = "getAllUsers", resultClasses = User.class),
        @NamedStoredProcedureQuery(name="getUserById", procedureName = "getUserById", resultClasses = User.class, parameters = {
                @StoredProcedureParameter(mode=ParameterMode.IN, name="id", type=Long.class)
        }),
        @NamedStoredProcedureQuery(name="getUserByName", procedureName = "getUserByName",resultClasses = User.class, parameters = {
                @StoredProcedureParameter(mode=ParameterMode.IN, name="userName", type=String.class)
        }),
        @NamedStoredProcedureQuery(name="addUser", procedureName = "addUser",resultClasses = User.class, parameters = {
                @StoredProcedureParameter(mode=ParameterMode.IN, name="userName", type=String.class),
                @StoredProcedureParameter(mode=ParameterMode.IN, name="password", type=String.class)
        }),
        @NamedStoredProcedureQuery(name="updateName",procedureName = "updateName", resultClasses = User.class, parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN,name="id",type=Long.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name="userName", type = String.class)
        }),
        @NamedStoredProcedureQuery(name="updatePsw", procedureName = "updatePsw", resultClasses = User.class, parameters = {
                @StoredProcedureParameter(mode =ParameterMode.IN, name="id", type=Long.class),
                @StoredProcedureParameter(mode=ParameterMode.IN, name ="psw", type=String.class)
        })
        ,
        @NamedStoredProcedureQuery(name="deleteUserById",procedureName = "deleteUserById", resultClasses = User.class, parameters = {
                @StoredProcedureParameter(mode=ParameterMode.IN, name="id", type = Long.class)
        })
})
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String userName;
    private String password;
    private boolean status=true;

    @ManyToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name="users_roles",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id")
    )
    private Set<Role> roles = new HashSet<>();

}
