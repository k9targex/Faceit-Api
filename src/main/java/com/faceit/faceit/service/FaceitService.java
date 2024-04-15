package com.faceit.faceit.service;

import com.faceit.faceit.model.PlayerInfoAndStats;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.faceit.faceit.model.PlayerStats;
import com.faceit.faceit.model.PlayerInfo;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
public class FaceitService {
  @Value("${faceit.api}")
  private String token;

  public PlayerInfoAndStats getRequest(String nickname) {
    String templateByNickname =
        "https://open.faceit.com/data/v4/search/players?nickname=%s&game=cs2&offset=0&limit=1";

    String urlByNickname = String.format(templateByNickname, nickname);
    PlayerInfo responseByNickname =
        WebClient.builder()
            .baseUrl(urlByNickname)
            .defaultHeader("Authorization", token)
            .build()
            .get()
            .retrieve()
            .bodyToMono(PlayerInfo.class)
            .block();

    if (responseByNickname != null && !responseByNickname.getItems().isEmpty()) {
      String templateById = "https://open.faceit.com/data/v4/players/%s/stats/cs2";
      String urlById =
          String.format(templateById, responseByNickname.getItems().get(0).getPlayerId());

      PlayerStats responseById =
          WebClient.builder()
              .baseUrl(urlById)
              .defaultHeader("Authorization", token)
              .build()
              .get()
              .retrieve()
              .bodyToMono(PlayerStats.class)
              .block();

      PlayerInfoAndStats playerInfoAndStats = new PlayerInfoAndStats();
      playerInfoAndStats.setPlayerInfo(responseByNickname);
      playerInfoAndStats.setPlayerStats(responseById);

      return playerInfoAndStats;
    } else {
      throw new WebClientResponseException(
          HttpStatus.NOT_FOUND.value(), "Player not found", null, null, null);
    }
  }
}
