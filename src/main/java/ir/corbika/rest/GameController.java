package ir.corbika.rest;

import ir.corbika.model.Game;
import ir.corbika.model.GameDefinition;
import ir.corbika.model.dto.*;
import ir.corbika.model.dto.*;
import ir.corbika.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping( value = "/api", produces = MediaType.APPLICATION_JSON_VALUE )
public class GameController {

    @Autowired
    private GameService gameService;


    @RequestMapping(value = "/gamedef/admin/add", method = RequestMethod.POST, consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity < String > addGameDefinition(@RequestBody GameDefinition gameDefinition) {
        gameService.saveGameDefinition(gameDefinition);
        return ResponseEntity.accepted().body("ok");
    }

    @RequestMapping(value = "/gamedef/admin/finish", method = RequestMethod.POST, consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity < String > updateWinner(@RequestBody Winner winner) {
        gameService.finish(winner);
        return ResponseEntity.accepted().body("ok");
    }

    @RequestMapping(value = "/gamedef/admin/list", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List> getAllGameDefinitions() {
        return ResponseEntity.accepted().body((List) gameService.findAll());
    }

    @RequestMapping(value = "/game/admin/list", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List> getAllGame() {
        return ResponseEntity.accepted().body((List) gameService.findRunning());
    }


    @RequestMapping(value = "/game/admin/start", method = RequestMethod.POST, consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity < String > startGame(@RequestBody GameDefinition gameDefinition) {
        gameService.startGame(gameDefinition.getId());
        return ResponseEntity.accepted().body("ok");
    }
    @RequestMapping(value = "/game/admin/finish-voting", method = RequestMethod.POST, consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    public void finishVoting(@RequestBody Game game) {
        gameService.closeVoting(game.getId());
    }

    @RequestMapping(value = "/game/mine", method = RequestMethod.GET)
    @PreAuthorize("hasRole('USER')")
    public RunningGame user(Principal user) {
        return gameService.getUserGame(user.getName());

    }

    @RequestMapping(value = "/game/scores", method = RequestMethod.GET)
    @PreAuthorize("hasRole('USER')")
    public List<UserScore> getMyScores(Principal user) {
        return gameService.getUserScores(user.getName());

    }

    @RequestMapping(value = "/game/scoreboard", method = RequestMethod.GET)
    @PreAuthorize("hasRole('USER')")
    public List<UserScore> getScoreBoard() {
        return gameService.getScoreBoard();

    }

    @RequestMapping("/game/vote")
    @PreAuthorize("hasRole('USER')")
    public void vote(Principal user,@RequestBody Vote vote) {
        String username = user.getName();
        gameService.voteGame(username,vote.getGameId(),vote.getVote());
    }

}
