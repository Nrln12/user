//package com.bankofbaku.user.security;
//
//import com.bankofbaku.user.entity.User;
//import com.bankofbaku.user.repositories.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
//public class UserDetailsServiceImpl implements UserDetailsService {
//    @Autowired
//    private UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user =userRepository.findByUserName(username);
//        if(user==null){
//            throw new UsernameNotFoundException("User is not found");
//        }
//        return new MyUserDetails(user);
//    }
//}
