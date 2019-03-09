package com.bfwg.repository;

import com.bfwg.model.Game;
import com.bfwg.model.GameUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GameUserRepository extends JpaRepository<GameUser, Long> {

    @Query("SELECT DISTINCT user FROM GameUser gu WHERE gu.asPlayer = true")
    List<Long> findPlayers();

}

