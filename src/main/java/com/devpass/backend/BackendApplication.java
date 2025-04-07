package com.devpass.backend;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure()
            .directory("./")
            .ignoreIfMissing()
            .load();

        System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
        System.setProperty("DB_URL", dotenv.get("DB_URL"));
        System.setProperty("MONGODB_URI", dotenv.get("MONGODB_URI"));
        System.setProperty("ELASTICSEARCH_USERNAME", dotenv.get("ELASTICSEARCH_USERNAME"));
        System.setProperty("ELASTICSEARCH_PASSWORD", dotenv.get("ELASTICSEARCH_PASSWORD"));
        System.setProperty("ELASTICSEARCH_URL", dotenv.get("ELASTICSEARCH_URL"));

        SpringApplication.run(BackendApplication.class, args);
    }

}
