package com.battle.service;

import com.battle.model.Game;
import com.battle.model.Player;
import com.battle.service.enums.GameStatus;

public class GameResponse {

    private GameStatus _gameStatus;

    private Player _player1;

    private Player _player2;

    public GameResponse(GameStatus gameStatus) {
        _gameStatus = gameStatus;
    }

    public GameStatus getGameStatus() {
        return _gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        _gameStatus = gameStatus;
    }

    public Player getPlayer1() {
        Game activeGame = GameService.getActiveGame();
        return activeGame == null ? null : activeGame.getPlayer1();
    }

    public Player getPlayer2() {
        Game activeGame = GameService.getActiveGame();
        return activeGame == null ? null : activeGame.getPlayer2();
    }
}
