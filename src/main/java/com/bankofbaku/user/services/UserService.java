package com.bankofbaku.user.services;

import com.bankofbaku.user.dto.UserDto;
import com.bankofbaku.user.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public interface UserService {
    // calling by procedures
    List<UserDto> getAllUsersProc();
    Optional<UserDto> addUserProc(String userName, String password) throws Exception;
    Optional<UserDto> updateName(Long id, String userName) throws Exception;
    Optional<UserDto> updatePsw(Long id, String psw) throws Exception;

    //calling by methods
    List<UserDto> getAllUsers();
    UserDto addUser(UserDto userDto) throws Exception;
    UserDto updateUser(Long id, UserDto userDto) throws Exception;
    List<UserDto> findByPattern(String pattern);
//    void deleteById(Long id);

}
