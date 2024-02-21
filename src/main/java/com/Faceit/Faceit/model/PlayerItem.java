package com.Faceit.Faceit.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlayerItem {
    @JsonProperty("player_id")
    private String player_id;

    @JsonProperty("nickname")
    private String nickname;

    @JsonProperty("avatar")
    private String avatarUrl;
    
    @JsonProperty("country")
    private String country;
}
