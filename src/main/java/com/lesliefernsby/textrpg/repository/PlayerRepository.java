package com.lesliefernsby.textrpg.repository;
import com.lesliefernsby.textrpg.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
}
