package com.battle.service;

import com.battle.model.ShipType;
import com.battle.service.enums.GameStatus;
import com.battle.service.enums.ShootStatus;

public class ShootResponse {

    private ShootStatus _shootStatus;
    private ShipType _shipType;

    public ShootResponse() {
    }

    public ShootStatus getShootStatus() {
        return _shootStatus;
    }

    public void setShootStatus(ShootStatus status) {
        _shootStatus = status;
    }

    public ShipType getShipType() {
        return _shipType;
    }

    public void setShipType(ShipType type) {
        _shipType = type;
    }
}
