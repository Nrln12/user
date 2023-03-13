package com.bankofbaku.user.controller;

import com.bankofbaku.user.entity.Role;
import com.bankofbaku.user.services.RoleServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class RoleController {
    private final RoleServiceImpl roleServiceImpl;
    @GetMapping
    public List<Role> getAllRoles(){
        return (List<Role>) roleServiceImpl.getAllRoles();
    }
}
