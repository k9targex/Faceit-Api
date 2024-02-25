package com.faceit.faceit.service;
import com.faceit.faceit.model.PlayerInfoAndStats;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.faceit.faceit.model.PlayerStats;
import com.faceit.faceit.model.PlayerInfo;


@Service
public class FaceitService {

    public static PlayerInfoAndStats getRequest(String nickname) {


        String token = "Bearer 88887511-8b30-4eaa-a38a-ad593868dfac";
        String templateByNickname = "https://open.faceit.com/data/v4/search/players?nickname=%s&game=cs2&offset=0&limit=1";

        String urlByNickname = String.format(templateByNickname, nickname);
        PlayerInfo responseByNickname = WebClient.builder()
                .baseUrl(urlByNickname)
                .defaultHeader("Authorization", token)
                .build()
                .get()
                .retrieve()
                .bodyToMono(PlayerInfo.class)
                .block();
        if (responseByNickname != null) {
            String templateById = "https://open.faceit.com/data/v4/players/%s/stats/cs2";
            String urlById = String.format(templateById, responseByNickname.getItems().get(0).getPlayerId());

            PlayerStats responseById = WebClient.builder()
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
        }
        else
            throw new NullPointerException("Not found");
    }
    private FaceitService() {}
}

