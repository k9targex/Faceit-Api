package com.Faceit.Faceit.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

 import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1
 import com.fasterxml.jackson.annotation.JsonProperty; // version 2.11.1
import lombok.NoArgsConstructor;

import java.util.ArrayList;
/* ObjectMapper om = new ObjectMapper();
Root root = om.readValue(myJsonString, Root.class); */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LifetimeStats {

    @JsonProperty("Average K/D Ratio")
    public String averageKDRatio;

    @JsonProperty("Average Headshots %")
    public String averageHeadshots;

    @JsonProperty("Total Headshots %")
    public String totalHeadshots;

    @JsonProperty("Matches")
    public String matches;

    @JsonProperty("Wins")
    public String wins;

    @JsonProperty("Win Rate %")
    public String winRate;

    @JsonProperty("Current Win Streak")
    public String currentWinStreak;

    @JsonProperty("Longest Win Streak")
    public String longestWinStreak;

    @JsonIgnore
    public String kDRatio;

    @JsonIgnore
    public ArrayList<String> recentResults;

}