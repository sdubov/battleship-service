package com.battle.model;

public class Game {
    private Player _player1;
    private Player _player2;
    private Player _activePlayer;

    public Game() {
        _player1 = new Player();
        _player2 = new Player();
        _activePlayer = _player1;
    }

    public void restart() {
        _player1.recreateShips();
        _player1.setField(new Field());
        _player2.recreateShips();
        _player2.setField(new Field());
    }

    public void switchPlayer() {
        if (_activePlayer == _player1) {
            _activePlayer = _player2;
        } else {
            _activePlayer = _player1;
        }
    }

    public Player getActivePlayer() {
        return _activePlayer;
    }

    public Player getOpponent() {
        if (_activePlayer == _player1) {
            return _player2;
        }

        return _player1;
    }
}
