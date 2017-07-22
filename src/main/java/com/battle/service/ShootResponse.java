package com.battle.service;

import com.battle.model.ShipType;
import com.battle.service.enums.GameStatus;
import com.battle.service.enums.ShootStatus;

public class ShootResponse {

    private ShootStatus _shootStatus;
    private GameStatus _gameStatus;
    private ShipType _shipType;
    private Integer _shipHits;

    public ShootResponse() {
    }

    public ShootStatus getShootStatus() {
        return _shootStatus;
    }

    public void setShootStatus(ShootStatus status) {
        _shootStatus = status;
    }

    public GameStatus getGameStatus() {
        return _gameStatus;
    }

    public void setGameStatus(GameStatus status) {
        _gameStatus = status;
    }

    public ShipType getShipType() {
        return _shipType;
    }

    public void setShipType(ShipType type) {
        _shipType = type;
    }

    public Integer getShipHits() {
        return _shipHits;
    }

    public void setShipHits(Integer hits) {
        _shipHits = hits;
    }
}
