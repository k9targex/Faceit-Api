package com.Faceit.Faceit.service;
import com.Faceit.Faceit.model.PlayerInfoAndStats;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.Faceit.Faceit.model.PlayerStats;
import com.Faceit.Faceit.model.PlayerInfo;
import com.Faceit.Faceit.model.PlayerInfoAndStats;


@Service
public class FaceitService {

    public static PlayerInfoAndStats getRequest(String nickname) {


        String token = "Bearer 88887511-8b30-4eaa-a38a-ad593868dfac";
        String templateByNickname = "https://open.faceit.com/data/v4/search/players?nickname=%s&game=cs2&offset=0&limit=1";

        String urlByNickname = String.format(templateByNickname, nickname);
        WebClient.Builder builder = WebClient.builder();

        PlayerInfo responseByNickname = WebClient.builder()
                .baseUrl(urlByNickname)
                .defaultHeader("Authorization", token)
                .build()
                .get()
                .retrieve()
                .bodyToMono(PlayerInfo.class)
                .block();

        String templateById = "https://open.faceit.com/data/v4/players/%s/stats/cs2";
        System.out.println(responseByNickname.getItems().get(0).getPlayer_id());
        String urlById = String.format(templateById, responseByNickname.getItems().get(0).getPlayer_id());
        WebClient.Builder builder2 = WebClient.builder();

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



}

