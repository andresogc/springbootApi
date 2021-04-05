package com.andresogc.apialmacenspringboot.jpa;

import com.andresogc.apialmacenspringboot.model.User;
import com.andresogc.apialmacenspringboot.repository.UserRepository;
import com.andresogc.apialmacenspringboot.service.IUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements IUser {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Boolean getUser(Integer userId)  {
        Optional<User> optional = userRepository.findById(userId);
        if (!optional.isPresent()){
            return false;
        }else{
            return true;
        }
    }
}
