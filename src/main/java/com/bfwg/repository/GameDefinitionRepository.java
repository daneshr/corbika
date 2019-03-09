package com.bfwg.repository;

import com.bfwg.model.GameDefinition;
import com.bfwg.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by fan.jin on 2016-10-15.
 */
public interface GameDefinitionRepository extends JpaRepository<GameDefinition, Long> {
}

