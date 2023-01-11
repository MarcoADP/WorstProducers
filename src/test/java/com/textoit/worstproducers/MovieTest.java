package com.textoit.worstproducers;

import com.textoit.worstproducers.entity.Movie;
import com.textoit.worstproducers.service.MovieService;
import java.io.IOException;
import java.util.List;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MovieTest {

    @Autowired
    private MovieService movieService;

    @Value("${moviecsv.filename}")
    private String movieCsvFilename;

    private static final int HEADER = 1;
    private static final String NOT_FOUND_MSG = "Arquivo não encontrado. Verifique se o CSV está na pasta Resources!";

    @SneakyThrows
    @Test
    void migrateSuccess() {
        String content = movieService.readCsv(movieCsvFilename);
        Assertions.assertNotNull(content);

        List<String> rows = movieService.separateRows(content);
        Assertions.assertFalse(rows.isEmpty());

        List<Movie> movies = movieService.convertRowsToMovies(rows);
        Assertions.assertEquals(rows.size(), movies.size() + HEADER);
    }

    @Test
    void migrateFileNotFound() {
        Exception exception = Assertions.assertThrows(
                IOException.class,
                () -> movieService.readCsv("ERRO.csv"));
        Assertions.assertEquals(NOT_FOUND_MSG, exception.getMessage());
    }

}
