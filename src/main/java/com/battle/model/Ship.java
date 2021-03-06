package com.battle.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

//@JsonIgnoreProperties(value = { "coordinates" })
public class Ship {

    @JsonProperty(value="type")
    private ShipType _type;

    @JsonProperty(value="size")
    private int _size;

    @JsonProperty(value="coordinates",
                  access = JsonProperty.Access.WRITE_ONLY)
    private ArrayList<Point> _coordinates;

    @JsonProperty(value="hits")
    private int _hits;

    @JsonProperty(value="isDead")
    private boolean _isDead;

    public Ship() {
    }

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
