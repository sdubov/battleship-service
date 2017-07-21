package com.battle.controller;

import com.battle.service.GameResponse;
import com.battle.service.GameService;
import com.battle.service.ShootResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class GameController {

    @RequestMapping("/")
    public GameResponse startGame() {
        return GameService.startGame();
    }

    @RequestMapping(value="/restart")
    public GameResponse restartGame() {
        return GameService.restartGame();
    }

    @RequestMapping(value="/shoot")
    public ShootResponse makeShoot(@RequestParam(value="x") Integer x,
                                   @RequestParam(value="y") Integer y) {
        return GameService.makeShoot(x, y);
    }
}
