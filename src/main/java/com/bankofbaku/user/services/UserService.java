package com.bankofbaku.user.services;

import com.bankofbaku.user.dto.UserDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    List<UserDto> getAllUsers();
    UserDto addUser(UserDto userDto) throws Exception;
    UserDto updateUser(Long id, UserDto userDto) throws Exception;
    List<UserDto> findByPattern(String pattern);
    void deleteById(Long id);


}
