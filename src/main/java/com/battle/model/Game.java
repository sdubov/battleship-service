package com.battle.model;

import com.battle.service.enums.GameStatus;
import com.battle.service.enums.PlayerStatus;

public class Game {

    private GameStatus _status;

    private Player _player1;

    private Player _player2;

    public Game() {
        _status = GameStatus.STARTED;
    }

    public void addPlayer(Player player) {
        if (_player1 == null) {

            player.setIsActive(true);
            _player1 = player;

            return;
        }

        _player2 = player;
    }

    public GameStatus getGameStatus() {
        return _status;
    }

    public void setGameStatus(GameStatus status) {
        _status = status;
    }

    public Player getActivePlayer() {
        if (_player1 != null && _player1.getIsActive()) {
            return _player1;
        }

        return _player2;
    }

    public Player getPlayer1() {
        return _player1;
    }

    public Player getPlayer2() {
        return _player2;
    }

    // Restart the game: clean player's shoots field and regenerate players' ships
    public void restart() {
        _status = GameStatus.SET_UP;

        _player1.setStatus(PlayerStatus.JOINED);
        _player1.recreateShips();
        _player1.setField(new Field());

        _player2.setStatus(PlayerStatus.JOINED);
        _player2.recreateShips();
        _player2.setField(new Field());
    }

    // Switch a player to his opponent
    public void switchPlayer() {
        if (_player1.getIsActive()) {
            _player1.setIsActive(false);
            _player2.setIsActive(true);
        } else {
            _player1.setIsActive(true);
            _player2.setIsActive(false);
        }
    }

    // Return current active player's opponent
    public Player getOpponent() {
        if (_player1.getIsActive()) {
            return _player2;
        }

        return _player1;
    }

    public Player getPlayerById(long id) {
        return _player1.getId() == id ? _player1 : _player2;
    }
}
