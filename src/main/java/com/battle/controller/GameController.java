package com.battle.controller;

import com.battle.model.Ship;
import com.battle.service.GameResponse;
import com.battle.service.GameService;
import com.battle.service.PlayerResponse;
import com.battle.service.ShootResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@CrossOrigin(origins = "http://aws-website-battleship-9vlme.s3-website-us-east-1.amazonaws.com")
public class GameController {

    private SimpMessagingTemplate template;

    @Autowired
    public GameController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @RequestMapping("/start")
    public GameResponse startGame() {
        return GameService.startGame();
    }

    @RequestMapping(value="/restart")
    public GameResponse restartGame() {
        GameResponse response = GameService.restartGame();
        this.template.convertAndSend("/topic/game-status", GameService.getGameStatus());
        return response;
    }

    @RequestMapping(value="/close")
    public GameResponse closeGame() { return GameService.closeGame(); }

    @RequestMapping(value="/join")
    public PlayerResponse join(@RequestParam(value="name") String playerName) {
        return GameService.joinGame(playerName);
    }

    @RequestMapping(value="/ready", method = POST)
    public GameResponse ready(@RequestParam(value="playerId") long playerId,
                              @RequestBody ArrayList<Ship> ships) {
        // Setup ships
        GameService.setupShips(playerId, ships);

        // Set player status
        // TODO: Update later to check if user is disconnected
        //PlayerService.setPlayerStatus(playerId, PlayerStatus.READY);

        // TODO: Maybe make method return void and rely on all users notifier.
        // Notify all subscribers about the game status change
        this.template.convertAndSend("/topic/game-status", GameService.getGameStatus());

        return new GameResponse(GameService.getActiveGame().getGameStatus());
    }

    @RequestMapping(value="/game-status")
    public GameResponse status() {
        return GameService.getGameStatus();
    }

    @RequestMapping(value="/shoot")
    public ShootResponse makeShoot(@RequestParam(value="playerId") Long playerId,
                                   @RequestParam(value="x") Integer x,
                                   @RequestParam(value="y") Integer y) {

        ShootResponse response = GameService.makeShoot(playerId, x, y);

        // Notify all subscribers about shoot to make a turn
        this.template.convertAndSend("/topic/game-status", GameService.getGameStatus());

        return response;
    }
}
