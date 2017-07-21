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

    public static GameResponse restartGame() {
        _game.restart();
        return new GameResponse(GameStatus.STARTED);
    }

    // Make a shoot
    public static ShootResponse makeShoot(int x, int y) {

        ShootResponse response = new ShootResponse();

        Player activePlayer = _game.getActivePlayer();
        Player opponent = _game.getOpponent();

        // Check if the cell is already processed
        if (activePlayer.getField().getStatus(x, y) != CellStatus.CLOSED) {
            // TODO: Update to one common status (?)
            ShootStatus status = activePlayer.getField().getStatus(x, y) == CellStatus.MISS ? ShootStatus.MISS : ShootStatus.HIT;
            response.setStatus(status);
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
            // TODO: Restart the game
        }

        // Update the cell
        CellStatus cellStatus = (status == ShootStatus.HIT || status == ShootStatus.KILL) ? CellStatus.HIT : CellStatus.MISS;
        activePlayer.getField().setStatus(x, y, cellStatus);

        // TODO: Switch player

        response.setStatus(status);
        return response;
    }
}
