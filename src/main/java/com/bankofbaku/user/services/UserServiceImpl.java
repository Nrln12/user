package com.bankofbaku.user.services;

import com.bankofbaku.user.dto.UserDto;
import com.bankofbaku.user.entity.Role;
import com.bankofbaku.user.entity.User;
import com.bankofbaku.user.exceptions.BadRequestException;
import com.bankofbaku.user.exceptions.IsNotValidException;
import com.bankofbaku.user.exceptions.NotFoundException;
import com.bankofbaku.user.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;


    public UserServiceImpl(UserRepository userRepository,ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }



    @Override
    public List<UserDto> getAllUsers() throws NotFoundException {
        List<UserDto> userDtos =  userRepository.findByStatusTrue().stream().map(user -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
        if(userDtos.size()==0){
            throw new NotFoundException("No data found");
        }else{
            return userDtos;
        }
    }

    @Override
    public UserDto addUser(UserDto userDto) throws Exception {
        Optional<User> currUser = Optional.ofNullable(userRepository.findByUserName(userDto.getUserName()));
        if(currUser.isPresent()){
            throw new BadRequestException("This username has already taken");
        }
        try{
            if(!isValidUserName(userDto.getUserName())){
                throw new IsNotValidException("Username is not valid");
            }
            if(!isValidPassword(userDto.getPassword())){
                throw new IsNotValidException("Password is not valid");
            }
            userDto.setPassword(encodePassword(userDto.getPassword()));
            userRepository.save(modelMapper.map(userDto, User.class));
        }catch (Exception ex){
            throw new Exception(ex);
        }
//        else {
//
//            userDto.setPassword(encodePassword(userDto.getPassword()));
//            userRepository.save(modelMapper.map(userDto,User.class));
//        }
        return userDto;
    }

    @Override
    public UserDto updateUser(Long id,UserDto userDto) throws Exception {
        Optional<User> recentUser= Optional.ofNullable(userRepository.findByUserIdAndStatusTrue(id));
        if(recentUser.isEmpty()){
            throw new NotFoundException("This user doesn't exist");
        }
        User user = recentUser.get();
        try{

            if(!isValidUserName(userDto.getUserName())){
                throw new IsNotValidException("Username is not valid");
            }
            if(!isValidPassword(userDto.getPassword())){
                throw new IsNotValidException("Password is not valid");
            }

        }catch (Exception ex){
            throw new Exception(ex);
        }
        user.setUserName(userDto.getUserName());
        user.setPassword(encodePassword(userDto.getPassword()));
        userRepository.save(user);
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public List<UserDto> findByPattern(String pattern) {

        return null;
    }

    @Override
    public void deleteById(Long id) {
        Optional<User> u = Optional.ofNullable(userRepository.findByUserIdAndStatusTrue(id));
        if(u.isEmpty()){
            throw new NotFoundException("User is not found");
        }
        User currUser=u.get();
        currUser.setStatus(false);
        userRepository.save(currUser);
    }


    public UserDto getUserById(Long id){
        Optional<User> u = Optional.ofNullable(userRepository.findByUserIdAndStatusTrue(id));
        if(u.isEmpty()){
            throw new NotFoundException("User is not found");
        }
        User currUser=u.get();
        return modelMapper.map(currUser, UserDto.class);

    }
    private boolean isValidUserName(String userName){
        String regex="^[a-zA-Z0-9._-]{3,}$";
        return userName.matches(regex) ? true : false;
    }
    private boolean isValidPassword(String password){
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$";
        return password.matches(regex) ? true : false;
    }
    private String encodePassword(String password){
        BCryptPasswordEncoder encoder= new BCryptPasswordEncoder();
        String hashedPassword=encoder.encode(password);
        return hashedPassword;
    }

}

//    private UserLocationDTO convertEntityToDto(User user){
//        modelMapper.getConfiguration()
//                .setMatchingStrategy(MatchingStrategies.LOOSE);
//        UserLocationDTO userLocationDTO = new UserLocationDTO();
//        userLocationDTO = modelMapper.map(user, UserLocationDTO.class);
//        return userLocationDTO;
//    }
