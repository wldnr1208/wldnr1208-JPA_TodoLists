package com.example.jpatodolists;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class JpaTodoListsApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpaTodoListsApplication.class, args);
    }

}
