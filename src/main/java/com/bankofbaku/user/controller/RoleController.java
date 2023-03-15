package com.bankofbaku.user.controller;

import com.bankofbaku.user.entity.Role;
import com.bankofbaku.user.services.RoleServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleServiceImpl roleServiceImpl;
    @GetMapping
    public List<Role> getAllRoles(){
        return roleServiceImpl.getAllRoles();
    }

    @PostMapping("/add-role")
    public Role addRole(@RequestBody Role role) throws Exception {
        return roleServiceImpl.addRole(role);
    }

    @PutMapping("/update-role/{id}")
    public Role updateRole(@PathVariable Long id, @RequestBody Role newRole) throws Exception{
        return roleServiceImpl.updateRole(id, newRole);
    }

    @PutMapping("/delete-role/{id}")
    public void deleteRole(@PathVariable Long id) throws Exception{
        roleServiceImpl.deleteRole(id);
    }
}
