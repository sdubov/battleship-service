package com.battle.model;

import com.battle.service.enums.CellStatus;

public class Cell {
    private Point _point;
    private CellStatus _status;

    public Cell(Point point, CellStatus status) {
        _point = point;
        _status = status;
    }

    public Point getPoint() {
        return _point;
    }

    public CellStatus getStatus() {
        return _status;
    }

    public void setStatus(CellStatus status) {
        _status = status;
    }
}
