package com.faceit.faceit.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PlayerInfoAndStats {
  private PlayerInfo playerInfo;
  private PlayerStats playerStats;
}
