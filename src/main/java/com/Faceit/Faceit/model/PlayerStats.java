package com.Faceit.Faceit.model;
import com.Faceit.Faceit.model.LifetimeStats;
import lombok.Builder;
import lombok.Data;
import java.util.List;
@Data
@Builder
public class PlayerStats {
    private String player_id;
    private String game_id;
    private LifetimeStats lifetime;
}

