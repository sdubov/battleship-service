package com.battle.service;

import com.battle.model.*;
import com.battle.service.enums.CellStatus;
import com.battle.service.enums.GameStatus;
import com.battle.service.enums.PlayerStatus;
import com.battle.service.enums.ShootStatus;

import java.util.ArrayList;
import java.util.Collections;

public class GameService {

    private static Game _game;

    public static Game getActiveGame() {
        return _game;
    }

    // Get game status
    public static GameResponse getGameStatus() {
        GameStatus status = _game == null ? GameStatus.NONE : _game.getGameStatus();
        return new GameResponse(status);
    }

    // Start the game
    // TODO: Handle the case that everybody can interrupt the game by calling the /start method.
    public static GameResponse startGame() {
        _game = new Game();
        return new GameResponse(_game.getGameStatus());
    }

    // Restart after one player win a game
    public static GameResponse restartGame() {
        _game.restart();
        return new GameResponse(_game.getGameStatus());
    }

    // Close the game
    public static GameResponse closeGame() {
        // TODO: Finalize the game before closing
        // TODO: Store statistic, etc.
        _game = null;
        return new GameResponse(GameStatus.NONE);
    }

    // Join an existing game
    public static PlayerResponse joinGame(String playerName) {

        if (_game == null) {
            return new PlayerResponse(null, GameStatus.NONE);
            // TODO: Throw
        }

        if (_game.getPlayer1() != null && _game.getPlayer2() != null) {
            // TODO: throw - All available players are joined
            return new PlayerResponse(null, GameStatus.BOOKED);
        }

        Player player = addPlayer(playerName);

        _game.setGameStatus(GameStatus.WAITING_FOR_OPPONENT);

        if (_game.getPlayer2() != null) {
            _game.setGameStatus(GameStatus.SET_UP);
        }

        return new PlayerResponse(player, _game.getGameStatus());
    }

    // Setup ships for player by player ID
    public static void setupShips(long playerId, ArrayList<Ship> ships) {

        Player player = _game.getPlayerById(playerId);
        player.setShips(ships);
        player.setStatus(PlayerStatus.READY);

        Player player1 = _game.getPlayer1();
        Player player2 = _game.getPlayer2();

        if (player2 == null || player1 == null) {
            _game.setGameStatus(GameStatus.WAITING_FOR_OPPONENT);
            return;
        }

        if (player1.getStatus() == PlayerStatus.READY && player2.getStatus() == PlayerStatus.READY) {
            _game.setGameStatus(GameStatus.IN_PROGRESS);
        } else {
            _game.setGameStatus(GameStatus.SET_UP);
        }
    }

    // Make a shoot
    public static ShootResponse makeShoot(long playerId, int x, int y) {

        ShootResponse response = new ShootResponse();

        Player activePlayer = _game.getActivePlayer();
        Player opponent = _game.getOpponent();

        if (_game.getGameStatus() != GameStatus.IN_PROGRESS) {
            response.setField(opponent.getField());
            response.setShipType(ShipType.NONE);
            response.setShootStatus(ShootStatus.NONE);
            return response;
        }

        if (activePlayer.getId() != playerId) {
            response.setField(opponent.getField());
            response.setShipType(ShipType.NONE);
            response.setShootStatus(ShootStatus.WAITING_FOR_OPPONENT);
            return response;
        }

        // Check if the cell is already processed
        if (activePlayer.getField().getStatus(x, y) != CellStatus.CLOSED) {
            response.setField(activePlayer.getField());
            response.setShipType(ShipType.NONE);
            response.setShootStatus(ShootStatus.ALREADY_OPENED);
            return response;
        }

        // Check if player shoot any opponent ship
        ShootStatus status = ShootStatus.MISS;
        Ship shipToHit = null;

        for (Ship ship : opponent.getShips()) {
            for (Point point : ship.getCoordinates()) {
                if (point.getX() == x && point.getY() == y) {
                    status = ShootStatus.HIT;
                    ship.hit();
                    response.setShipType(ship.getType());
                    shipToHit = ship;
                    break;
                }
            }
            if (status == ShootStatus.HIT) { break; }
        }

        // Check if kill the ship
        if (shipToHit != null && shipToHit.getHits() == shipToHit.getSize()) {
            shipToHit.setIsDead();
            frameKilledShip(shipToHit);
        }

        // Check if a player kill all opponent's ships and make him a winner
        if (status == ShootStatus.HIT) {
            boolean isWin = true;
            for (Ship ship : opponent.getShips()) {
                if (!ship.getIsDead()) {
                    isWin = false;
                    break;
                }
            }

            if (isWin) {
                activePlayer.win();
                _game.setGameStatus(GameStatus.FINISHED);
            }
        }


        // Update player's field
        activePlayer.getField().setStatus(x, y, status == ShootStatus.HIT ? CellStatus.HIT : CellStatus.MISS);

        response.setField(activePlayer.getField());
        response.setShootStatus(status);

        // Switch an active player if miss
        if (status == ShootStatus.MISS) {
            _game.switchPlayer();
        }

        return response;
    }

    //region Private Methods and Operators

    private static Player addPlayer(String name) {
        Player player = PlayerService.createPlayer(name);
        _game.addPlayer(player);

        return player;
    }

    // Frame a killed ship with miss cells (ships cannot border each other)
    private static void frameKilledShip(Ship ship) {

        ArrayList<Point> coordinates = ship.getCoordinates();
        Collections.sort(coordinates);

        Point min = coordinates.get(0);
        Point max = coordinates.get(coordinates.size() - 1);

        int minX = min.getX() - 1 < 0 ? 0 : min.getX() - 1;
        int minY = min.getY() - 1 < 0 ? 0 : min.getY() - 1;
        int maxX = max.getX() + 1 > 9 ? 9 : max.getX() + 1;
        int maxY = max.getY() + 1 > 9 ? 9 : max.getY() + 1;

        Field field = _game.getActivePlayer().getField();

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                if (field.getStatus(x, y) != CellStatus.HIT) {
                    field.setStatus(x, y, CellStatus.MISS);
                }
            }
        }
    }

    //endregion Private Methods and Operators
}
