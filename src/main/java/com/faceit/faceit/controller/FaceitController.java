package com.faceit.faceit.controller;


import com.faceit.faceit.model.PlayerInfoAndStats;
import com.faceit.faceit.service.FaceitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.web.client.HttpClientErrorException;


@Controller
@RequestMapping("api/v1")
public class FaceitController {


    private final FaceitService faceitService;
    public FaceitController(FaceitService faceitService){this.faceitService = faceitService;}

    @ExceptionHandler(HttpClientErrorException.NotFound.class)
    public ResponseEntity<String> handleNotFoundException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Player not found");
    }

    @GetMapping("/faceit/info")
    public String getControl(@RequestParam(defaultValue = "s1mple") String nickname, Model model) {
            PlayerInfoAndStats playerInfoAndStats = faceitService.getRequest(nickname);
            model.addAttribute("playerInfoAndStats", playerInfoAndStats);
            return "index";

    }
}
