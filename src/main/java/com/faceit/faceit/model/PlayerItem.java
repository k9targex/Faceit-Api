package com.faceit.faceit.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlayerItem {
  @JsonProperty("player_id")
  private String playerId;

  @JsonProperty("nickname")
  private String nickname;

  @JsonProperty("avatar")
  private String avatarUrl;

  @JsonProperty("country")
  private String country;

  @JsonProperty("games")
  private List<Game> games;


  public String getSkillLevel() {
    if (games != null) {
      for (Game game : games) {
        if (game.getName().equals("cs2")) {
          return game.getSkillLevel();
        }
      }
    }
    return "3";
  }
}
