package com.lesliefernsby.textrpg.service.impl;

import com.lesliefernsby.textrpg.model.Player;
import com.lesliefernsby.textrpg.repository.PlayerRepository;
import com.lesliefernsby.textrpg.service.PlayerService;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    @Override
    public Optional<Player> getPlayerById(Long id) {
        return playerRepository.findById(id);
    }

    @Override
    public Player createPlayer(Player player) {
        return playerRepository.save(player);
    }

    @Override
    public Player updatePlayer(Long id, Player playerDetails) {
        Player existingPlayer = playerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Player not found with id " + id));

        existingPlayer.setName(playerDetails.getName());
        existingPlayer.setExp(playerDetails.getExp());
        existingPlayer.setLvl(playerDetails.getLvl());
        existingPlayer.setStr(playerDetails.getStr());
        existingPlayer.setDex(playerDetails.getDex());
        existingPlayer.setCon(playerDetails.getCon());
        existingPlayer.setIntel(playerDetails.getIntel());
        existingPlayer.setWis(playerDetails.getWis());
        existingPlayer.setCha(playerDetails.getCha());

        return playerRepository.save(existingPlayer);
    }

    @Override
    public void deletePlayer(Long id) {
        Player existingPlayer = playerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Player not found with id " + id));

        playerRepository.delete(existingPlayer);
    }
    @Override
    @Transactional
    public Player generateRandomPlayer(String name) {
        int str = generateRandomAttribute();
        int dex = generateRandomAttribute();
        int con = generateRandomAttribute();
        int intel = generateRandomAttribute();
        int wis = generateRandomAttribute();
        int cha = generateRandomAttribute();

        int lvl = 1;
        int exp = 0;

        Player player = new Player();
        player.setName(name);
        player.setExp(exp);
        player.setLvl(lvl);
        player.setStr(str);
        player.setDex(dex);
        player.setCon(con);
        player.setIntel(intel);
        player.setWis(wis);
        player.setCha(cha);

        Player savedPlayer = playerRepository.save(player);
        return savedPlayer;
    }

    private int generateRandomAttribute() {
        return ThreadLocalRandom.current().nextInt(3, 19); 
    }
}

