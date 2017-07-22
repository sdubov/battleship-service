package com.battle.service;

import com.battle.model.Player;
import com.battle.service.enums.GameStatus;

public class GameResponse {

    private GameStatus _status;

    // TODO: Send only Player ID!!!
    private Player _activePlayer;

    public GameResponse(GameStatus status) {
        _status = status;
    }

    public GameStatus getStatus() {
        return _status;
    }

    public void setStatus(GameStatus status) {
        _status = status;
    }

    public Player getActivePlayer() {
        return _activePlayer;
    }

    public void setActivePlayer(Player activePlayer) {
        _activePlayer = activePlayer;
    }
}
