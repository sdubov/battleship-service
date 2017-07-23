package com.battle.model;

import java.util.ArrayList;

public class Ship {

    private ShipType _type;

    private int _size;

    private ArrayList<Point> _coordinates;

    private int _hits;

    private boolean _isDead;

    public Ship(ShipType type, ArrayList<Point> coordinates) {
        _type = type;
        _size = getSizeByType(_type);
        _coordinates = coordinates;
        _hits = 0;
        _isDead = false;
    }

    public ShipType getType() {
        return _type;
    }

    public int getSize() {
        return _size;
    }

    public ArrayList<Point> getCoordinates() {
        return _coordinates;
    }

    public int getHits() {
        return _hits;
    }

    public boolean getIsDead() {
        return _isDead;
    }

    public void setIsDead() {
        _isDead = true;
    }

    // Return ship's size by it's type
    static int getSizeByType(ShipType type) {
        switch (type) {
            case DESTROYER:
                return 2;

            case SUBMARINE:
            case CRUISER:
                return 3;

            case BATTLESHIP:
                return 4;

            case CARRIER:
                return 5;
        }

        return 0;
    }

    // Count ship's hit to understand when it is killed
    public void hit() {
        _hits++;
    }
}
