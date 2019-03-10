package com.bfwg.service.impl;

import com.bfwg.model.*;
import com.bfwg.model.dto.Winner;
import com.bfwg.repository.GameDefinitionRepository;
import com.bfwg.repository.GameRepository;
import com.bfwg.repository.GameUserRepository;
import com.bfwg.repository.UserRepository;
import com.bfwg.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.transform.sax.SAXSource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by fan.jin on 2016-10-15.
 */

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private GameDefinitionRepository gameDefinitionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GameUserRepository gameUserRepository;


    public GameDefinition findById(Long id) throws AccessDeniedException {
        GameDefinition g = gameDefinitionRepository.findOne(id);
        return g;
    }

    @Override
    public List findAll() throws AccessDeniedException {
        return gameDefinitionRepository.findAll();
    }

    public void saveGameDefinition(GameDefinition gameDefinition) {
        gameDefinitionRepository.saveAndFlush(gameDefinition);

    }

    @Override
    public void updateWinner(Winner winner) {
        User user = userRepository.findByUsername(winner.getWinner());
        GameDefinition gameDefinition = gameDefinitionRepository.findOne(winner.getGameId());
        gameDefinition.setAnswer(user.getId());
        gameDefinitionRepository.saveAndFlush(gameDefinition);
    }

    @Override
    public void startGame(Long gameId) {
        List runnings = gameRepository.findAllByRunning(true);
        if (runnings.size() > 0) {
            throw new RuntimeException("there is a running game!");
        }
        GameDefinition gameDefinition = gameDefinitionRepository.findOne(gameId);
        start(gameDefinition);
    }

    @Transactional
    public void start(GameDefinition gameDefinition) {
        List<User> users = userRepository.findAllByDeterment(false);
        Game game = new Game();
        game.setGameDefinition(gameDefinition);
        game.setName(gameDefinition.getName());
        game.setRunning(true);
        List<User> players = choosePlayers(gameDefinition.getPlayersCount(), users);
        List<User> anticipators = calculateAnticipator(users, players);

        players.forEach(user -> game.addUserAsPlayer(user));
        anticipators.forEach(user -> game.addUserAsAnticipator(user));
        if(players.size()>0){
            gameDefinition.setChoices(new ArrayList<>());
            players.forEach(player-> gameDefinition.addChoice(new Choice(player.getId(),player.getFirstName() + "  " + player.getLastName())));
        }

        gameRepository.save(game);
        gameDefinitionRepository.save(gameDefinition);

        System.out.println();
//        excludePlayers

    }

    private List<User> calculateAnticipator(List<User> users, List<User> players) {
        List<User> anticipators = new ArrayList<>();
        for (User user : users) {
            boolean flag = false;
            for (User player : players) {
                if (player.getId().compareTo(user.getId()) == 0) {
                    flag = true;
                    break;
                }
            }
            if (!flag)
                anticipators.add(user);
        }
        return anticipators;
    }

    public List<User> choosePlayers(int count, List<User> users) {
        if (count == 0)
            return new ArrayList();
        List<Long> playedUserIds = gameUserRepository.findPlayers();
        int candidateCount = Math.min(count, users.size() - playedUserIds.size());
        List<User> notPlayers = new ArrayList<>();
        for (User user : users) {
            boolean flag = false;
            for (Long id : playedUserIds) {
                if (id.compareTo(user.getId()) == 0) {
                    flag = true;
                    break;
                }
            }
            if (!flag)
                notPlayers.add(user);
        }
        Collections.shuffle(notPlayers);
        return notPlayers.subList(0, candidateCount);

    }
//        List<User> notPlayers = users.stream().map(user -> )

}
