package com.bankofbaku.user.repositories;

import com.bankofbaku.user.dto.UserDto;
import com.bankofbaku.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //calling by stored procedures
    @Procedure(name = "getAllUsers")
    List<User> getByStatusTrue();
    @Procedure(name="getUserById")
    User getUserByIdAndStatusTrue(Long id);
    @Procedure(name="getUserByName")
    List<User> getUserByNameAndStatusTrue(String userName);

    @Procedure(name="addUser")
    UserDto addUserProc(String userName,String password);

    @Procedure(name="updateName")
    UserDto updateName(Long id, String userName);

    @Procedure(name="updatePsw")
    UserDto updatePsw(Long id, String psw);
    @Procedure(name="deleteUserById")
    void deleteUserById(Long id);

    //call by function
    @Query(value = "SELECT searchUser(?)", nativeQuery = true)
    String searchUser(Long id);

    //call by spring itself
    List<User> findByStatusTrue();
    User findByUserIdAndStatusTrue(Long id);
    User findByUserName(String username);
    void deleteById(Long id);
}
