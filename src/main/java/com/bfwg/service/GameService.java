package com.bfwg.service;

import com.bfwg.model.GameDefinition;
import com.bfwg.model.dto.RunningGame;
import com.bfwg.model.dto.UserScore;
import com.bfwg.model.dto.Winner;
import org.springframework.security.access.AccessDeniedException;

import java.util.List;

public interface GameService {
    GameDefinition findById(Long id);

    List findAll() throws AccessDeniedException;

    void saveGameDefinition(GameDefinition gameDefinition);

    List<UserScore> getUserScores(String username);

    void finish(Winner winner);

    void startGame(Long gameId);

    RunningGame getUserGame(String username);

    void voteGame(String username, Long gameId, Long choice);

    void closeVoting(Long gameId);

    List<UserScore> getScoreBoard();
}
