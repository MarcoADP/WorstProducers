package com.textoit.worstproducers;

import com.textoit.worstproducers.service.MovieService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WorstProducersApplication {

	final
	MovieService movieService;

	@Value("${moviecsv.filename}")
	private String movieCsvFilename;

	public WorstProducersApplication(MovieService movieService) {
		this.movieService = movieService;
	}

	public static void main(String[] args) {
		SpringApplication.run(WorstProducersApplication.class, args);
	}

	@Bean
	CommandLineRunner runner() {
		return args -> movieService.migrateMovies(movieCsvFilename);
	}

}
