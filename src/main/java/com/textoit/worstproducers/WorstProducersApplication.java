package com.textoit.worstproducers;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WorstProducersApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorstProducersApplication.class, args);
	}

	@Bean
	CommandLineRunner runner() {
		return args -> {
			ClassLoader classloader = Thread.currentThread().getContextClassLoader();
			try (InputStream inputStream =  classloader.getResourceAsStream("movielist.csv")) {

				if (inputStream == null) {
					throw new FileNotFoundException("Sem filmes");
				}

				String text = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
				System.out.println(text);
			} catch (FileNotFoundException e) {
				throw new FileNotFoundException("Arquivo não encontrado. Verifique se o CSV está na pasta Resources!");
			}

		};
	}

}
