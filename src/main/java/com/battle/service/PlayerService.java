package com.battle.service;

import com.battle.model.Game;
import com.battle.model.Player;
import com.battle.service.enums.PlayerStatus;

public class PlayerService {

    public static Player createPlayer(String playerName) {
        Player player = new Player(playerName);
        player.setStatus(PlayerStatus.JOINED);
        return player;
    }

    public static void setPlayerStatus(long playerId, PlayerStatus status) {
        Game game = GameService.getActiveGame();

        if (game == null) {
            // TODO: throw Game is not initialized
            return;
        }

        Player player = game.getPlayerById(playerId);

        if (player == null) {
            // TODO: throw No user with Id found
            return;
        }

        player.setStatus(status);
    }



}
