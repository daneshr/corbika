package ir.corbika.service;

import ir.corbika.model.User;

import java.util.List;


public interface UserService {
    User findById(Long id);
    User findByUsername(String username);
    List<User> findAll ();

    void resign(String username);

    void unResign(String username);
}
