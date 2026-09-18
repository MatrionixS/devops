package com.rusyn;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class App implements CommandLineRunner {
    static void main() {
        SpringApplication.run(App.class);
    }

    @Override
    public void run(String... args) {
        System.out.println("Test log from run method");
    }
}
