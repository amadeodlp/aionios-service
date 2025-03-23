package io.aionios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AioniosApplication {

    public static void main(String[] args) {
        SpringApplication.run(AioniosApplication.class, args);
    }
}
