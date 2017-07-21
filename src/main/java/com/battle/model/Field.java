package com.battle.model;

import com.battle.service.enums.CellStatus;

import java.util.ArrayList;

public class Field {
    private ArrayList<ArrayList<Cell>> _shoots;

    public Field() {
        // Default initialize all coordinates with '0' value (closed cell);
        _shoots = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            ArrayList<Cell> row = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                row.add(new Cell(new Point(i, j), CellStatus.CLOSED));
            }
            _shoots.add(row);
        }
    }

    public ArrayList<ArrayList<Cell>> getShoots() {
        return _shoots;
    }

    public void setStatus(int x, int y, CellStatus status) {
        _shoots.get(x).get(y).setStatus(status);
    }

    public CellStatus getStatus(int x, int y) {
        return _shoots.get(x).get(y).getStatus();
    }
}
