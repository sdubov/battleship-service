package com.battle.service;

import com.battle.service.enums.GameStatus;

public class GameResponse {

    private GameStatus _status;

    public GameResponse(GameStatus status) {
        _status = status;
    }

    public GameStatus getStatus() {
        return _status;
    }

    public void setStatus(GameStatus status) {
        _status = status;
    }
}
