package com.lesliefernsby.textrpg.service;
import com.lesliefernsby.textrpg.model.Player;

import java.util.List;
import java.util.Optional;

public interface PlayerService {
    List<Player> getAllPlayers();
    Optional<Player> getPlayerById(Long id);
    Player createPlayer(Player player);
    Player updatePlayer(Long id, Player playerDetails);
    void deletePlayer(Long id);
    Player generateRandomPlayer(String name);
}