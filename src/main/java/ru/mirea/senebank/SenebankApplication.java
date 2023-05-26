package ru.mirea.senebank;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@RequiredArgsConstructor
@SpringBootApplication
public class SenebankApplication {

    public static void main(String[] args) {
        SpringApplication.run(SenebankApplication.class, args);
    }

}
