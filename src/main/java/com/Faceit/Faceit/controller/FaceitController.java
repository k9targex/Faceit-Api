package com.Faceit.Faceit.controller;


import com.Faceit.Faceit.model.PlayerStats;
import com.Faceit.Faceit.service.FaceitService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/v1/stats")
public class FaceitController {
    @GetMapping("/sashka")
    public PlayerStats hello(@RequestParam(defaultValue = "s1mple") String nickname) {
        return FaceitService.getRequest(nickname);

    }



}
