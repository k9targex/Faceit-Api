package com.Faceit.Faceit.service;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.Faceit.Faceit.model.PlayerStats;

@Service
public class FaceitService {
    public static PlayerStats getRequest(String nickname) {
//        String template = "https://open.faceit.com/data/v4/search/players?nickname=%s&offset=0&limit=1";
        String template = "https://open.faceit.com/data/v4/players/%s/stats/cs2";

        String token = "Bearer 88887511-8b30-4eaa-a38a-ad593868dfac";
        String url = String.format(template, nickname);
        WebClient.Builder builder = WebClient.builder();

        PlayerStats response = WebClient.builder()
                .baseUrl(url)
                .defaultHeader("Authorization", token)
                .build()
                .get()
                .retrieve()
                .bodyToMono(PlayerStats.class)
                .block();

//        return response.getLifetime().getAverageKDRatio();
        return response;
    }


}

