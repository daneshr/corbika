package ir.corbika.service.impl;

import ir.corbika.model.User;
import ir.corbika.repository.UserRepository;
import ir.corbika.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByUsername(String username ) throws UsernameNotFoundException {
        User u = userRepository.findByUsername( username );
        return u;
    }

    public User findById( Long id ) throws AccessDeniedException {
        User u = userRepository.findOne( id );
        return u;
    }

    public List<User> findAll() throws AccessDeniedException {
        List<User> result = userRepository.findAll();
        return result;
    }

    @Override
    public void resign(String username) {
        User user = userRepository.findByUsername(username);
        if (user.isDeterment()){
            throw new RuntimeException("user already resigned!");
        }
        user.setDeterment(true);
        userRepository.saveAndFlush(user);
    }

    @Override
    public void unResign(String username) {
        User user = userRepository.findByUsername(username);
        if (!user.isDeterment()){
            throw new RuntimeException("user already involved!");
        }
        user.setDeterment(false);
        userRepository.saveAndFlush(user);
    }
}
