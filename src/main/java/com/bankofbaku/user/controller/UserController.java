package com.bankofbaku.user.controller;

import com.bankofbaku.user.dto.UserDto;
import com.bankofbaku.user.services.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userServiceImpl;

    @GetMapping
    public List<UserDto> getAllUsers(){
        return userServiceImpl.getAllUsers();
    }

    @PostMapping
    public UserDto addUser(@RequestBody UserDto userDto) throws Exception {
        return userServiceImpl.addUser(userDto);
    }
    @PutMapping("/update/{id}")
    public UserDto updateUser(@PathVariable Long id, @RequestBody  UserDto userDto) throws Exception {
        return userServiceImpl.updateUser(id, userDto);
    }
    @PutMapping("/delete/{id}")
    public void deleteUser(@PathVariable Long id){
        userServiceImpl.deleteById(id);
    }
}
