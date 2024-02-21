package com.Faceit.Faceit.controller;


import com.Faceit.Faceit.model.PlayerInfoAndStats;
import com.Faceit.Faceit.model.PlayerStats;
import com.Faceit.Faceit.service.FaceitService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/v1")
public class FaceitController {
    @GetMapping("/faceit/info")
    public PlayerInfoAndStats hello(@RequestParam(defaultValue = "s1mple") String nickname) {
        return FaceitService.getRequest(nickname);

    }



}
