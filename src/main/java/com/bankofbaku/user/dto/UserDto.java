package com.bankofbaku.user.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class UserDto {
    private String userName;
    private String password;
}
