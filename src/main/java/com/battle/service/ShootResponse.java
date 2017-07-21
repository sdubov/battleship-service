package com.battle.service;

import com.battle.model.ShipType;
import com.battle.service.enums.ShootStatus;

public class ShootResponse {

    private ShootStatus _status;
    private ShipType _shipType;
    private Integer _shipHits;

    public ShootResponse() {
    }

    public ShootStatus getStatus() {
        return _status;
    }

    public void setStatus(ShootStatus status) {
        _status = status;
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
