package com.bfwg.rest;

import com.bfwg.model.Game;
import com.bfwg.model.GameDefinition;
import com.bfwg.model.dto.*;
import com.bfwg.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping( value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE )
public class GameDefinitionController {

    @Autowired
    private GameService gameService;


    @RequestMapping(value = "/gamedef/admin/add", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity < String > addGameDefinition(@RequestBody GameDefinition gameDefinition) {
        gameService.saveGameDefinition(gameDefinition);
        return ResponseEntity.accepted().body("ok");
    }

    @RequestMapping(value = "/gamedef/admin/finish", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity < String > updateWinner(@RequestBody Winner winner) {
        gameService.finish(winner);
        return ResponseEntity.accepted().body("ok");
    }

    @RequestMapping(value = "/gamedef/admin/list", method = RequestMethod.GET)
    public ResponseEntity<List> getAllGameDefinitions() {
        return ResponseEntity.accepted().body((List) gameService.findAll());
    }


    @RequestMapping(value = "/game/admin/start", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity < String > startGame(@RequestBody GameDefinition gameDefinition) {
        gameService.startGame(gameDefinition.getId());
        return ResponseEntity.accepted().body("ok");
    }
    @RequestMapping(value = "/game/admin/finish-voting", method = RequestMethod.POST, consumes = "application/json")
    public void finishVoting(@RequestBody Game game) {
        gameService.closeVoting(game.getId());
    }

    @RequestMapping(value = "/game/mine", method = RequestMethod.GET)
    public RunningGame user(Principal user) {
        return gameService.getUserGame("admin");
//        return gameService.getUserGame(user.getName());

    }

    @RequestMapping(value = "/game/scores", method = RequestMethod.GET)
    public List<UserScore> getMyScores(Principal user) {
        return gameService.getUserScores("admin");
//        return gameService.getUserGame(user.getName());

    }

    @RequestMapping(value = "/game/scoreboard", method = RequestMethod.GET)
    public List<UserScore> getScoreBoard() {
        return gameService.getScoreBoard();

    }

    @RequestMapping("/game/vote")
    public void vote(Principal user,@RequestBody Vote vote) {
        String username = "admin";
//        String username = user.getName();
        gameService.voteGame(username,vote.getGameId(),vote.getVote());
    }

    @RequestMapping("/game/svote")
    public void sampleVote(@RequestBody SVote vote) {
//        String username = vote.getUserName();
        gameService.voteGame(vote.getUsername(),vote.getGameId(),vote.getVote());
    }
}