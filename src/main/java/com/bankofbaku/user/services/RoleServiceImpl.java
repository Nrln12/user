package com.bankofbaku.user.services;

import com.bankofbaku.user.entity.Role;
import com.bankofbaku.user.repositories.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    public List<Role> getAllRoles(){
        return roleRepository.findByStatusTrue();
    }
}
