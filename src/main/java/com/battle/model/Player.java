package com.battle.model;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class Player {

    private final Long _id;

    private final String _name;

    private Integer _score;

    // Player shoots
    private Field _field;

    // Player ships array that should be hit by opponent
    private ArrayList<Ship> _ships;

    public Player(String name) {
        _id = UUID.randomUUID().getLeastSignificantBits() / 1000;
        _name = name;
        _score = 0;
        _field = new Field();
        _ships = randomShipsGeneration();
    }

    public Long getId() { return _id; }

    public String getName() { return _name; }

    public Integer getScore() {
        return _score;
    }

    public Field getField() {
        return _field;
    }

    public void setField(Field field) {
        _field = field;
    }

    public ArrayList<Ship> getShips() {
        return _ships;
    }

    public void setShips(ArrayList<Ship> ships) {
        _ships = ships;
    }

    // Player win the game
    public void win() {
        _score++;
    }

    // Regenerate player's ships set
    public void recreateShips() {
        _ships = randomShipsGeneration();
    }

    // Method generate the fake ships setup that is used for testing
    private ArrayList<Ship> fakeShipsGeneration() {
        ArrayList<Ship> ships = new ArrayList<>();

        ArrayList<Point> carrierCoordinates = new ArrayList<>();
        carrierCoordinates.add(new Point(2, 9));
        carrierCoordinates.add(new Point(3, 9));
        carrierCoordinates.add(new Point(4, 9));
        carrierCoordinates.add(new Point(5, 9));
        carrierCoordinates.add(new Point(6, 9));
        ships.add(new Ship(ShipType.CARRIER, carrierCoordinates));

        ArrayList<Point> battleshipCoordinates = new ArrayList<>();
        battleshipCoordinates.add(new Point(5, 2));
        battleshipCoordinates.add(new Point(5, 3));
        battleshipCoordinates.add(new Point(5, 4));
        battleshipCoordinates.add(new Point(5, 5));
        ships.add(new Ship(ShipType.BATTLESHIP, battleshipCoordinates));

        ArrayList<Point> cruiserCoordinates = new ArrayList<>();
        cruiserCoordinates.add(new Point(8, 1));
        cruiserCoordinates.add(new Point(8, 2));
        cruiserCoordinates.add(new Point(8, 3));
        ships.add(new Ship(ShipType.CRUISER, cruiserCoordinates));

        ArrayList<Point> submarineCoordinates = new ArrayList<>();
        submarineCoordinates.add(new Point(3, 0));
        submarineCoordinates.add(new Point(3, 1));
        submarineCoordinates.add(new Point(3, 2));
        ships.add(new Ship(ShipType.SUBMARINE, submarineCoordinates));

        ArrayList<Point> destroyerCoordinates = new ArrayList<>();
        destroyerCoordinates.add(new Point(0, 0));
        destroyerCoordinates.add(new Point(1, 0));
        ships.add(new Ship(ShipType.DESTROYER, destroyerCoordinates));

        return ships;
    }

    // Generate a set of ships with random positions
    // TODO: Replace method with ability to make initial ships setup by user
    private ArrayList<Ship> randomShipsGeneration() {

        ArrayList<ShipType> types = new ArrayList<ShipType>() {{
            add(ShipType.CARRIER);
            add(ShipType.BATTLESHIP);
            add(ShipType.CRUISER);
            add(ShipType.SUBMARINE);
            add(ShipType.DESTROYER);
        }};

        ArrayList<Ship> ships = new ArrayList<>();

        int count = types.size();
        Random random = new Random();

        while (count != 0) {
            ShipType type = types.get(count - 1);
            int size = Ship.getSizeByType(type);

            boolean isVertical = random.nextBoolean();
            int randomX = random.nextInt(9);
            int randomY = random.nextInt(9);

            if (randomX + size > 10 && isVertical || randomY + size > 10 && !isVertical) {
                continue;
            }

            // Check every ship for intersection
            boolean isIntersected = false;

            for (int i = 0; i < ships.size(); i++) {
                Ship currentShip = ships.get(i);
                int minX = currentShip.getCoordinates().get(0).getX();
                int minY = currentShip.getCoordinates().get(0).getY();
                int maxX = currentShip.getCoordinates().get(currentShip.getCoordinates().size() - 1).getX();
                int maxY = currentShip.getCoordinates().get(currentShip.getCoordinates().size() - 1).getY();

                if ((isVertical && randomY <= maxY + 1 && randomY >= minY - 1 && randomX + size - 1 >= minX - 1 && randomX <= maxX + 1) ||
                        (!isVertical && randomY <= maxY + 1 && randomY + size - 1 >= minY - 1 && randomX >= minX - 1 && randomX <= maxX + 1)) {
                    isIntersected = true;
                    break;
                }
            }

            if (isIntersected) {
                continue;
            }

            ArrayList<Point> coordinates = new ArrayList<>();

            if (isVertical) {
                for (int i = randomX; i < randomX + size; i++) {
                    coordinates.add(new Point(i, randomY));
                }
            } else {
                for (int i = randomY; i < randomY + size; i++) {
                    coordinates.add(new Point(randomX, i));
                }
            }

            ships.add(new Ship(type, coordinates));
            count--;
        }

        return ships;
    }
}
