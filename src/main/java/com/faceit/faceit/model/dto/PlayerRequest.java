package com.faceit.faceit.model.dto;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlayerRequest {
    private String nickname;
    @JsonCreator
    public PlayerRequest(@JsonProperty("nickname") String nickname) {
        this.nickname = nickname;
    }
}


