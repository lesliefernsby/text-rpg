package com.lesliefernsby.textrpg;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TextRpgApplication {

    public static void main(String[] args) {
        String env = System.getenv("SPRING_PROFILES_ACTIVE");
        if (env == null || !env.equalsIgnoreCase("prod")) {
            Dotenv dotenv = Dotenv.load();
            dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
        }

        SpringApplication.run(TextRpgApplication.class, args);
    }
}