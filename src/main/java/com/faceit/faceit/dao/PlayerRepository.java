package com.faceit.faceit.dao;

import com.faceit.faceit.model.entity.Player;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
  @EntityGraph(attributePaths = {"subUsers"})
  Optional<Player> findPlayerByNickname(String nickname);
}
