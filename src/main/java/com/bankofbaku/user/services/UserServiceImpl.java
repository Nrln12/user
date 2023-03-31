package com.bankofbaku.user.services;

import com.bankofbaku.user.dto.UserDto;
import com.bankofbaku.user.entity.User;
import com.bankofbaku.user.exceptions.BadRequestException;
import com.bankofbaku.user.exceptions.IsNotValidException;
import com.bankofbaku.user.exceptions.NotFoundException;
import com.bankofbaku.user.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;


@Service
public class UserServiceImpl implements UserService{
    @PersistenceContext
    @Autowired
    private EntityManager em;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    public UserServiceImpl(UserRepository userRepository,ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    // Calling by stored procedure
    @Override
    public List<UserDto> getAllUsersProc() throws NotFoundException {
        Object users =  em.createNamedStoredProcedureQuery("getAllUsers").getResultList().stream().map(user-> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
        return (List<UserDto>) users;
    }
    public UserDto getUserByIdProc(Long id) throws NoResultException {
       Optional<Object> u = Optional.ofNullable(em.createNamedStoredProcedureQuery("getUserById").setParameter("id",id).getSingleResult());
        if(u.isEmpty()){
            throw new NoResultException("User doesn't exist");
        }
        Object user=  u.get();
        return modelMapper.map(user, UserDto.class);
    }
    public List<UserDto> getUserByName(String name) throws NotFoundException{
        List<UserDto> userDtos = (List<UserDto>) em.createNamedStoredProcedureQuery("getUserByName").setParameter("userName",name).getResultList().stream().map(user-> modelMapper.map(user,UserDto.class)).collect(Collectors.toList());
        if(userDtos.isEmpty()){
            throw new NotFoundException("User doesn't exist");
        }
        return userDtos;
    }
    @Override
    public Optional<UserDto> addUserProc(String userName, String password) throws Exception {
       List<User> u= em.createNamedStoredProcedureQuery("getUserByName").setParameter("userName",userName).getResultList().stream().toList();
       if(u.size()!=0){
           throw new BadRequestException("This username has already taken");
       }
       try{
           if(!isValidUserName(userName)){
               throw new IsNotValidException("Not valid username");
           }
           if(!isValidPassword(password)){
               throw new IsNotValidException("Not valid password");
           }
           password=encodePassword(password);

       }catch (Exception ex){
           throw new Exception(ex);
       }
       userRepository.addUserProc(userName,password);
       Optional<UserDto> userDto=getUserByName(userName).stream().findFirst();
       return userDto;
    }

    @Override
    public Optional<UserDto> updateName(Long id, String userName) throws Exception {
        Optional<UserDto> u= Optional.ofNullable(getUserByIdProc(id));
       if(u.isEmpty()) {
           throw new NotFoundException("User is not found");
       }
       try{
           if(!isValidUserName(userName)){
               throw new IsNotValidException("User name is not valid");
           }
       }catch (Exception ex){
           throw new Exception(ex);
       }
       userRepository.updateName(id, userName);
       return u;
    }
    @Override
    public Optional<UserDto> updatePsw(Long id, String psw){
       Optional<UserDto> u= Optional.ofNullable(getUserByIdProc(id));
       if(u.isEmpty()){
           throw new NotFoundException("User not found");
       }
        if(!isValidPassword(psw))
            throw new IsNotValidException("Password is not valid");
        userRepository.updatePsw(id, encodePassword(psw));
        return u;
    }
    public void deleteUserById(Long id) throws Exception{
        Optional<UserDto> userDto = Optional.ofNullable(getUserByIdProc(id));
        if(userDto.isEmpty()){
            throw new NotFoundException("User not found");
        }
        em.createNamedStoredProcedureQuery("deleteUserById").setParameter("id", id).execute();
    }

    // call by function
    public String searchUser(Long id){
       return userRepository.searchUser(id);
    }
    // Calling by methods
    @Override
    public List<UserDto> getAllUsers() throws NotFoundException {
        List<UserDto> userDtos =  userRepository.findByStatusTrue().stream().map(user -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
        if(userDtos.size()==0){
            throw new NotFoundException("No data found");
        }else{
            return userDtos;
        }
    }
    public UserDto getUserById(Long id){
        Optional<User> u = Optional.ofNullable(userRepository.findByUserIdAndStatusTrue(id));
        if(u.isEmpty()){
            throw new NotFoundException("User is not found");
        }
        User currUser=u.get();
        return modelMapper.map(currUser, UserDto.class);

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

    public void deleteById(Long id) {
        Optional<User> u = Optional.ofNullable(userRepository.findByUserIdAndStatusTrue(id));
        if(u.isEmpty()){
            throw new NotFoundException("User is not found");
        }
        userRepository.deleteById(id);
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
