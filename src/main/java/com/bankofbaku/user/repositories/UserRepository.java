package com.bankofbaku.user.repositories;

import com.bankofbaku.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByStatusTrue();
    User findByUserIdAndStatusTrue(Long id);
    User findByUserName(String username);
}
