package com.battle.service;

import com.battle.model.Field;
import com.battle.model.ShipType;
import com.battle.service.enums.ShootStatus;

public class ShootResponse {

    private ShootStatus _status;

    private Field _field;

    private ShipType _shipType;

    public ShootResponse() {
    }

    public ShootStatus getShootStatus() {
        return _status;
    }

    public void setShootStatus(ShootStatus status) {
        _status = status;
    }

    public Field getField() {
        return _field;
    }

    public void setField(Field field) {
        _field = field;
    }

    public ShipType getShipType() {
        return _shipType;
    }

    public void setShipType(ShipType type) {
        _shipType = type;
    }
}
