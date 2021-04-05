package com.andresogc.apialmacenspringboot.service;

import com.andresogc.apialmacenspringboot.model.User;

public interface IUser {
    Boolean getUser(Integer userId);
    void saveUser(User user);
}
