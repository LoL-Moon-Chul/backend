package com.lolmoonchul.lolmoonchul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class LolmoonchulApplication {

    public static void main(String[] args) {
        SpringApplication.run(LolmoonchulApplication.class, args);
    }

}
