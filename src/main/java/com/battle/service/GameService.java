package com.battle.service;

import com.battle.model.Game;
import com.battle.model.Player;
import com.battle.model.Point;
import com.battle.model.Ship;
import com.battle.service.enums.CellStatus;
import com.battle.service.enums.GameStatus;
import com.battle.service.enums.ShootStatus;

public class GameService {

    private static Game _game;

    public static Game getActiveGame() {
        return _game;
    }

    // Start the game
    public static GameResponse startGame() {
        _game = new Game();
        return new GameResponse(GameStatus.STARTED);
    }

    // Restart after one player win a game
    public static GameResponse restartGame() {
        _game.restart();
        GameResponse response = new GameResponse(_game.getGameStatus());
        response.setActivePlayer(_game.getActivePlayer());
        return response;
    }

    // Get game status
    public static GameResponse getGameStatus() {
        GameResponse response = new GameResponse(_game.getGameStatus());
        response.setActivePlayer(_game.getActivePlayer());
        return response;
    }

    // Join an existing game
    public static GameResponse joinGame(String playerName) {

        if (_game.getGameStatus() == GameStatus.IN_PROGRESS) {
            // TODO: All available players are joined - throw an exception
            return new GameResponse(GameStatus.BOOKED);
        }

        Player player = new Player(playerName);
        _game.addPlayer(player);

        GameResponse response = new GameResponse(_game.getGameStatus());
        response.setActivePlayer(player);

        return response;
    }

    // Make a shoot
    public static ShootResponse makeShoot(long playerId, int x, int y) {

        ShootResponse response = new ShootResponse();

        Player activePlayer = _game.getActivePlayer();
        Player opponent = _game.getOpponent();

        if (_game.getGameStatus() == GameStatus.FINISHED) {
            response.setGameStatus(GameStatus.FINISHED);
            return response;
        }

        if (activePlayer.getId() != playerId) {
            response.setGameStatus(GameStatus.WAITING_FOR_SHOOT);
            return response;
        }

        // Check if the cell is already processed
        if (activePlayer.getField().getStatus(x, y) != CellStatus.CLOSED) {
            return response;
        }

        // Check if player shoot any opponent ship
        ShootStatus status = ShootStatus.MISS;

        for (Ship ship : opponent.getShips()) {
            for (Point point : ship.getCoordinates()) {
                if (point.getX() == x && point.getY() == y) {
                    status = ShootStatus.HIT;
                    ship.hit();

                    if (ship.getHits() == ship.getSize()) {
                        ship.setIsDead();
                        status = ShootStatus.KILL;
                    }

                    response.setShipType(ship.getType());
                    response.setShipHits(ship.getHits());

                    break;
                }
            }
            if (status == ShootStatus.HIT || status == ShootStatus.KILL) { break; }
        }

        // Check if a player kill all opponent's ships and make him a winner
        if (status == ShootStatus.KILL) {
            status = ShootStatus.WIN;
            for (Ship ship : opponent.getShips()) {
                if (!ship.getIsDead()) {
                    status = ShootStatus.KILL;
                    break;
                }
            }
        }

        if (status == ShootStatus.WIN) {
            activePlayer.win();
            _game.setGameStatus(GameStatus.FINISHED);
        }

        // Update player's field
        CellStatus cellStatus = (status == ShootStatus.HIT || status == ShootStatus.KILL) ? CellStatus.HIT : CellStatus.MISS;
        activePlayer.getField().setStatus(x, y, cellStatus);

        if (status == ShootStatus.MISS) {
            _game.switchPlayer();
        }

        response.setShootStatus(status);
        response.setGameStatus(_game.getGameStatus());
        return response;
    }
}
