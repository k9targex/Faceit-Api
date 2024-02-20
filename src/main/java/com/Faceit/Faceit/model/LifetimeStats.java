package com.Faceit.Faceit.model;

import lombok.Builder;
import lombok.Data;

 import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
 import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1

import java.util.ArrayList;
/* ObjectMapper om = new ObjectMapper();
Root root = om.readValue(myJsonString, Root.class); */

@Data
@Builder
public class LifetimeStats {
    @JsonProperty("Longest Win Streak")
    public String longestWinStreak;
    @JsonProperty("Current Win Streak")
    public String currentWinStreak;
    @JsonProperty("Matches")
    public String matches;
    @JsonProperty("K/D Ratio")
    public String kDRatio;
    @JsonProperty("Average K/D Ratio")
    public String averageKDRatio;
    @JsonProperty("Average Headshots %")
    public String averageHeadshots;
    @JsonProperty("Win Rate %")
    public String winRate;
    @JsonProperty("Total Headshots %")
    public String totalHeadshots;
    @JsonProperty("Recent Results")
    public ArrayList<String> recentResults;
    @JsonProperty("Wins")
    public String wins;
}