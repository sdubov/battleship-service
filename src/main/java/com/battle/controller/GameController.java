package com.battle.controller;

import com.battle.service.GameResponse;
import com.battle.service.GameService;
import com.battle.service.ShootResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class GameController {

    private SimpMessagingTemplate template;

    @Autowired
    public GameController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @RequestMapping("/")
    public GameResponse startGame() {
        return GameService.startGame();
    }

    @RequestMapping(value="/restart")
    public GameResponse restartGame() {
        return GameService.restartGame();
    }

    @RequestMapping(value="/join")
    public GameResponse join(@RequestParam(value="name") String playerName) {

        GameResponse response = GameService.joinGame(playerName);

        // Notify all subscribers about the game status change
        this.template.convertAndSend("/topic/game-status", GameService.getGameStatus());

        return response;
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

        // Notify all subscribers about step to make a turn
        this.template.convertAndSend("/topic/game-status", GameService.getGameStatus());

        return response;
    }
}
