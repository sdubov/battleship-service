package com.battle.service;

import com.battle.model.Player;
import com.battle.service.enums.GameStatus;

public class PlayerResponse {

    private Player _player;

    private GameStatus _gameStatus;

    public PlayerResponse(Player player, GameStatus gameStatus) {
        _player = player;
        _gameStatus = gameStatus;
    }

    public Player getPlayer() {
        return _player;
    }

    public GameStatus getGameStatus() {
        return _gameStatus;
    }
}
