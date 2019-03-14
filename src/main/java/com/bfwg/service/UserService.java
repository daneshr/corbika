package com.bfwg.service;

import com.bfwg.model.User;

import java.util.List;


public interface UserService {
    User findById(Long id);
    User findByUsername(String username);
    List<User> findAll ();
}
