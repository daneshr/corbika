package com.bfwg.repository;

import com.bfwg.model.GamePlayer;
import com.bfwg.model.GameUserId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GamePlayerRepository extends JpaRepository<GamePlayer, GameUserId> {

//    @Query("SELECT DISTINCT user FROM GameUser ")
//    List<Long> findPlayers();
//
//    @Query("UPDATE GameUser gu SET gu.vote = ?3 WHERE gu.game=?1 and gu.user=?2")
//    void vote(Game game, User user, Long vote);

}

