package com.Faceit.Faceit.controller;


import com.Faceit.Faceit.model.PlayerInfoAndStats;
import com.Faceit.Faceit.model.PlayerStats;
import com.Faceit.Faceit.service.FaceitService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ui.Model;



@Controller
@RequestMapping("api/v1")
public class FaceitController {
    @GetMapping("/faceit/info")
    public String hello(@RequestParam(defaultValue = "s1mple") String nickname,Model model) {
        PlayerInfoAndStats playerInfoAndStats = FaceitService.getRequest(nickname);
        model.addAttribute("playerInfoAndStats",playerInfoAndStats);
        return "index";

    }


/*
@GetMapping("/weather")
        public String getWeather(@RequestParam String word, Model model) {
        WeatherForecast weatherForecast=weatherService.getWeatherByCity(word);
        model.addAttribute("weatherForecast",weatherForecast);
        return "index";
  }
 */
}
