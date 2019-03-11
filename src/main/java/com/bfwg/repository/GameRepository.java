package com.bfwg.repository;

import com.bfwg.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {
    List findAllByRunning(boolean running);
}
