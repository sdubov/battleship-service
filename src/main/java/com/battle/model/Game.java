package com.battle.model;

import com.battle.service.enums.GameStatus;

public class Game {

    private GameStatus _status;

    private Player _player1;

    private Player _player2;

    private Player _activePlayer;

    public Game() {
        _status = GameStatus.STARTED;
    }

    public void addPlayer(Player player) {
        if (_player1 == null) {
            _player1 = player;
            _activePlayer = _player1;
            _status = GameStatus.WAITING_FOR_OPPONENT;
            return;
        }

        _player2 = player;
        _status = GameStatus.IN_PROGRESS;
    }

    public GameStatus getGameStatus() {
        return _status;
    }

    public void setGameStatus(GameStatus status) {
        _status = status;
    }

    public Player getActivePlayer() {
        return _activePlayer;
    }

    public Player getPlayer1() {
        return _player1;
    }

    public Player getPlayer2() {
        return _player2;
    }

    // Restart the game: clean player's shoots field and regenerate players' ships
    public void restart() {
        _status = GameStatus.IN_PROGRESS;
        _player1.recreateShips();
        _player1.setField(new Field());
        _player2.recreateShips();
        _player2.setField(new Field());
    }

    // Switch a player to his opponent
    public void switchPlayer() {
        if (_activePlayer == _player1) {
            _activePlayer = _player2;
        } else {
            _activePlayer = _player1;
        }
    }

    // Return current active player's opponent
    public Player getOpponent() {
        if (_activePlayer == _player1) {
            return _player2;
        }

        return _player1;
    }
}
