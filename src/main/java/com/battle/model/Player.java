package com.battle.model;

import java.util.ArrayList;
import java.util.Random;

public class Player {
    private int _score;
    private Field _field;
    private ArrayList<Ship> _ships;

    public Player() {
        _score = 0;
        _field = new Field();
//        _ships = randomShipsGeneration(
//                ShipType.CARRIER,
//                ShipType.BATTLESHIP,
//                ShipType.CRUISER,
//                ShipType.SUBMARINE,
//                ShipType.DESTROYER);
        // TODO: Replace with random ships generator/user defined ships array
        _ships = fakeShipsGeneration();
    }

    public int getScore() {
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

    public void win() {
        _score++;
    }

    public void recreateShips() {
        _ships = fakeShipsGeneration();
    }

    // TODO: Fake ship generator (both players will have same ship coordinates)
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

    // TODO: Replace method with ability to make initial ships setup by user
    private ArrayList<Ship> randomShipsGeneration(ShipType... types) {

        ArrayList<Ship> ships = new ArrayList<>();

        int count = types.length;
        Random random = new Random();

        while (count != 0) {
            ShipType type = types[count - 1];
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
