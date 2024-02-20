package com.Faceit.Faceit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication

public class FaceitApplication {

	public static void main(String[] args) {

		SpringApplication.run(FaceitApplication.class, args);
	}

}
/*
api_key = 88887511-8b30-4eaa-a38a-ad593868dfac
response = requests.get("https://open.faceit.com/data/v4/games?offset=0&limit=20",
headers={"Authorization":"Bearer "+api_key,
"Content-Type":"application/json"}
)
 */
