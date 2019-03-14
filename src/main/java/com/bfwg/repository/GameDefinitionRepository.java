package com.bfwg.repository;

import com.bfwg.model.GameDefinition;
import com.bfwg.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface GameDefinitionRepository extends JpaRepository<GameDefinition, Long> {
}

