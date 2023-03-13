package com.bankofbaku.user.repositories;

import com.bankofbaku.user.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    List<Role> findByStatusTrue();
    List<Role> findByRoleIdAndStatusTrue(Long id);

}
