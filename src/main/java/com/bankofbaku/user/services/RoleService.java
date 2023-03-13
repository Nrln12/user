package com.bankofbaku.user.services;

import com.bankofbaku.user.entity.Role;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleService {
    List<Role> getAllRoles();
    Role addRole(Role role);
}
