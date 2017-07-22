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

    // Start the game
    public static GameResponse startGame() {
        _game = new Game();
        return new GameResponse(GameStatus.STARTED);
    }

    // Restart after one player win a game
    public static GameResponse restartGame() {
        _game.restart();
        return new GameResponse(GameStatus.STARTED);
    }

    // Join an existing game
    public static GameResponse joinGame(String playerName) {

        if (_game.getGameStatus() == GameStatus.IN_PROGRESS) {
            // TODO: All available player are joined.
            // TODO: throw an exception
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

        if (_game.getGameStatus() == GameStatus.FINISHED) {
            // TODO: Replace with broadcasting
            response.setShootStatus(ShootStatus.LOOSE);
            return response;
        }

        Player activePlayer = _game.getActivePlayer();
        Player opponent = _game.getOpponent();

        if (activePlayer.getId() != playerId) {
            response.setGameStatus(GameStatus.WAITING_FOR_SHOOT);
            return response;
        }

        // Check if the cell is already processed
        if (activePlayer.getField().getStatus(x, y) != CellStatus.CLOSED) {
            // TODO: Update to one common status (?)
            return response;
        }

        // Check if player shoot an opponent's ship
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

        // Check if player hit all opponent's ships and make him a winner
        if (status == ShootStatus.KILL) {
            status = ShootStatus.WIN;
            for (Ship ship : opponent.getShips()) {
                if (!ship.getIsDead()) {
                    status = ShootStatus.KILL;
                    break;
                }
            }
        }

        // Current player win the game
        if (status == ShootStatus.WIN) {
            activePlayer.win();
            // TODO: Restart the game only once (replace with broadcasting)
            _game.setGameStatus(GameStatus.FINISHED);
        }

        // Update the cell
        CellStatus cellStatus = (status == ShootStatus.HIT || status == ShootStatus.KILL) ? CellStatus.HIT : CellStatus.MISS;
        activePlayer.getField().setStatus(x, y, cellStatus);

        // Check if we need to switch a player
        if (status == ShootStatus.MISS) {
            _game.switchPlayer();
        }

        response.setShootStatus(status);
        return response;
    }
}
