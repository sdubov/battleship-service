package com.battle.service;

import com.battle.model.Player;
import com.battle.service.enums.GameStatus;
import com.sun.istack.internal.Nullable;

public class GameResponse {

    private GameStatus _status;

    // TODO: Send only Player ID and Player Name!!!
    private Player _activePlayer;

    private String _player1Name;

    private Integer _player1Score;

    private String _player2Name;

    private Integer _player2Score;

    public GameResponse(GameStatus status) {
        _status = status;
    }

    public GameStatus getStatus() {
        return _status;
    }

    public void setStatus(GameStatus status) {
        _status = status;
    }

    public Player getActivePlayer() {
        return _activePlayer;
    }

    public void setActivePlayer(Player activePlayer) {
        _activePlayer = activePlayer;
    }

    @Nullable
    public String getPlayer1Name() {
        if (GameService.getActiveGame() == null) {
            return null;
        }

        Player player = GameService.getActiveGame().getPlayer1();
        return player != null ? player.getName() : null;
    }

    public Integer getPlayer1Score() {
        if (GameService.getActiveGame() == null) {
            return 0;
        }

        Player player = GameService.getActiveGame().getPlayer1();
        return player != null ? player.getScore() : 0;
    }

    @Nullable
    public String getPlayer2Name() {
        if (GameService.getActiveGame() == null) {
            return null;
        }

        Player player = GameService.getActiveGame().getPlayer2();
        return player != null ? player.getName() : null;
    }

    public Integer getPlayer2Score() {
        if (GameService.getActiveGame() == null) {
            return 0;
        }

        Player player = GameService.getActiveGame().getPlayer2();

        return player != null ? player.getScore() : 0;
    }
}
