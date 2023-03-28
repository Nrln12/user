package com.bankofbaku.user.controller;

import com.bankofbaku.user.dto.UserDto;
import com.bankofbaku.user.entity.User;
import com.bankofbaku.user.services.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserServiceImpl userServiceImpl;

    @GetMapping
    public List<UserDto> getAllUsers(){

        return userServiceImpl.getAllUsers();
    }
    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id){
        return userServiceImpl.getUserById(id);
    }
    @PostMapping("/new")
    public UserDto addUser(@RequestBody UserDto userDto, Authentication authentication) throws Exception {
        log.info("auth: {}", authentication);
        return userServiceImpl.addUser(userDto);
    }
//@PostMapping("/new")
//public UserDto addUser(@RequestBody UserDto userDto) throws Exception {
//
//    return userServiceImpl.addUser(userDto);
//}
    @PutMapping("/update/{id}")
    public UserDto updateUser(@PathVariable Long id, @RequestBody  UserDto userDto) throws Exception {
        return userServiceImpl.updateUser(id, userDto);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable Long id){
        userServiceImpl.deleteById(id);
    }
}
