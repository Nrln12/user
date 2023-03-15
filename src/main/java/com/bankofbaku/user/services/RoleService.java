package com.bankofbaku.user.services;

import com.bankofbaku.user.entity.Role;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleService {
    List<Role> getAllRoles();
    Role addRole(Role role) throws Exception;

    Role updateRole(Long id, Role role) throws Exception;

    void deleteRole(Long id) throws Exception;
}
