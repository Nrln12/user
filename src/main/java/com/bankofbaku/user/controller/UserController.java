package com.bankofbaku.user.controller;

import com.bankofbaku.user.dto.UserDto;
import com.bankofbaku.user.entity.User;
import com.bankofbaku.user.services.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserServiceImpl userServiceImpl;

    @GetMapping
    public List<UserDto> getAllUsers(){
       // return userServiceImpl.getAllUsers();
        return userServiceImpl.getAllUsersProc();
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id){

       // return userServiceImpl.getUserById(id);
       return  userServiceImpl.getUserByIdProc(id);
    }

    @GetMapping("/search/{name}")
    public List<UserDto> getUserByName(@PathVariable String name){
        return userServiceImpl.getUserByName(name);
    }
//    @PostMapping("/new")
//    public UserDto addUser(@RequestBody UserDto userDto, Authentication authentication) throws Exception {
//        log.info("auth: {}", authentication);
//        return userServiceImpl.addUser(userDto);
//    }

    @GetMapping("/searchUser/{id}")
    public String searchUser(@PathVariable Long id) {
        return userServiceImpl.searchUser(id);
    }
@PostMapping("/new")
public Optional<UserDto> addUser(@RequestParam String userName, @RequestParam String password, Authentication authentication) throws Exception {
    log.info("auth: {}", authentication);
    return userServiceImpl.addUserProc(userName, password);
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

    @PutMapping("/updateName/{id}")
    public Optional<UserDto> updateName(@PathVariable Long id, @RequestParam  String userName) throws Exception {
        return userServiceImpl.updateName(id, userName);
    }
    @PutMapping("/updatePsw/{id}")
    public Optional<UserDto> updatePsw(@PathVariable Long id, @RequestParam String psw) throws Exception{
        return userServiceImpl.updatePsw(id, psw);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable Long id) throws Exception {
      //userServiceImpl.deleteById(id);
        userServiceImpl.deleteUserById(id);
    }
}
