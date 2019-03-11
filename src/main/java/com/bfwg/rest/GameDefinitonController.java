package com.bfwg.rest;

import com.bfwg.model.GameDefinition;
import com.bfwg.model.User;
import com.bfwg.model.dto.Winner;
import com.bfwg.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;
import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping( value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE )
public class GameDefinitonController {

    @Autowired
    private GameService gameService;


    @RequestMapping(value = "/gamedef/add", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity < String > addGameDefinition(@RequestBody GameDefinition gameDefinition) {
        gameService.saveGameDefinition(gameDefinition);
        return ResponseEntity.accepted().body("ok");
    }

    @RequestMapping(value = "/gamedef/winner", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity < String > updateWinner(@RequestBody Winner winner) {
        gameService.updateWinner(winner);
        return ResponseEntity.accepted().body("ok");
    }

    @RequestMapping(value = "/gamedef/list", method = RequestMethod.GET)
    public ResponseEntity<List> getAllGameDefinitions() {
        return ResponseEntity.accepted().body((List) gameService.findAll());
    }


    @RequestMapping(value = "/game/start", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity < String > startGame(@RequestBody GameDefinition gameDefinition) {
        gameService.startGame(gameDefinition.getId());
        return ResponseEntity.accepted().body("ok");
    }

    @RequestMapping("/game/mine")
    public Object user(Principal user) {
        return gameService.getUserGame(user.getName());

    }
}
