package com.bankofbaku.user.services;

import com.bankofbaku.user.entity.Role;
import com.bankofbaku.user.repositories.RoleRepository;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    public List<Role> getAllRoles(){
        return roleRepository.findByStatusTrue();
    }

    public Role addRole(Role role) throws Exception {
        Optional<Role> currRole= Optional.ofNullable(roleRepository.findByStatusTrueAndName(role.getName()));
        if(currRole.isPresent()){
            throw new Exception("Already exists");
        }else{
            roleRepository.save(role);
        }
        return role;
    }

    public Role updateRole(Long id, Role newRole) throws Exception{
        Optional<Role> currRole= Optional.ofNullable(roleRepository.findByRoleIdAndStatusTrue(id));
        if(currRole.isEmpty()){
           throw new Exception("No data found");
        }
        Role role=currRole.get();
        role.setName(newRole.getName());
        roleRepository.save(role);

       return role;
    }
    public void deleteRole(Long id) throws Exception{
        Optional<Role> role = Optional.ofNullable(roleRepository.findByRoleIdAndStatusTrue(id));
        if(role.isEmpty()){
            throw new Exception("Doesn't exist");
        }
        Role currRole=role.get();
        currRole.setStatus(false);
        roleRepository.save(currRole);
    }
}
