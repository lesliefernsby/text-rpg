package com.lesliefernsby.textrpg.controller;

import com.lesliefernsby.textrpg.model.Player;
import com.lesliefernsby.textrpg.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/api/players")
@CrossOrigin(origins = "*") 
public class PlayerController {

    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping("/random")
    public ResponseEntity<Player> generateRandomPlayer(@RequestBody PlayerNameRequest request) {
        String name = request.getName();
        if (name == null || name.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Player randomPlayer = playerService.generateRandomPlayer(name.trim());
        return ResponseEntity.ok(randomPlayer);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Player> getPlayerById(@PathVariable("id") Long id) {
        Optional<Player> playerOpt = playerService.getPlayerById(id);
        return playerOpt.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}

// temp DTO
class PlayerNameRequest {
    private String name;

    public PlayerNameRequest() {}

    public PlayerNameRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    } 
}
