package com.Faceit.Faceit.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Builder
@Data
@NoArgsConstructor
public class PlayerInfoAndStats {
    private PlayerInfo playerInfo;
    private PlayerStats playerStats;
//    public PlayerInfoAndStats() {
//        // конструктор по умолчанию
//    }
}
