package com.Faceit.Faceit.model;
import com.Faceit.Faceit.model.LifetimeStats;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder

@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlayerStats {
//
//    private String player_id;
//    private String game_id;
    private LifetimeStats lifetime;
}

