package com.bfwg.repository;

import com.bfwg.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by fan.jin on 2016-10-15.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername( String username );
    List findAllByDeterment(boolean determent);
}

