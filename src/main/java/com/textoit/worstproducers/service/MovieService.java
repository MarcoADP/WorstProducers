package com.textoit.worstproducers.service;

import com.textoit.worstproducers.entity.Movie;
import com.textoit.worstproducers.repository.MovieRepository;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class MovieService {

    final
    MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Iterable<Movie> findAll() {
        return movieRepository.findAll();
    }

    public void migrateMovies(String filename) throws IOException {
        String content = readCsv(filename);
        List<String> rows = separateRows(content);
        List<Movie> movies = convertRowsToMovies(rows);
        saveAll(movies);
    }

    public String readCsv(String filename) throws IOException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        try (InputStream inputStream =  classloader.getResourceAsStream(filename)) {

            if (inputStream == null) {
                throw new FileNotFoundException("Sem filmes");
            }

            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

        } catch (IOException e) {
            throw new IOException("Arquivo não encontrado. Verifique se o CSV está na pasta Resources!");
        }
    }

    public List<String> separateRows(String moviesCsv) {
        return Arrays.asList(moviesCsv.split("\n"));
    }

    public List<Movie> convertRowsToMovies(List<String> rows) {
        List<Movie> movies = new ArrayList<>();
        rows.forEach(row -> convertToMovie(row).ifPresent(movies::add));
        return movies;
    }

    public Optional<Movie> convertToMovie(String row) {
        List<String> columns = getColumns(row);

        if (isNumber(columns.get(0))) {
            return Optional.of(new Movie(columns));
        }
        return Optional.empty();
    }

    private List<String> getColumns(String row) {
        return Arrays.asList(row.split(";"));
    }

    private Boolean isNumber(String column) {
        if (column == null) {
            return false;
        }
        Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
        return pattern.matcher(column).matches();
    }

    public void saveAll(List<Movie> movies) {
        movieRepository.saveAll(movies);
    }

    public List<Movie> findWinners() {
        return movieRepository.findMoviesByWinnerTrue();
    }

    public Map<String, List<Movie>> findProducersWithMoreThanOneMovie() {
        var movies = findWinners();
        Map<String, List<Movie>> moviesGroupedByProducer = groupMoviesByProducer(movies);
        return getProducersWithMoreThanOneMovie(moviesGroupedByProducer);

    }

    public Map<String, List<Movie>> groupMoviesByProducer(List<Movie> movies) {
        Map<String, List<Movie>> moviesGroupedByProducers = new HashMap<>();
        movies.forEach(movie -> movie.getProducersList().forEach(producer ->
                moviesGroupedByProducers.compute(producer, (k, v) -> addMovie(v, movie))));
        return moviesGroupedByProducers;
    }

    private List<Movie> addMovie(List<Movie> movies, Movie movie) {
        if (movies == null) {
            movies = new ArrayList<>();
        }
        movies.add(movie);
        return movies;
    }

    public Map<String, List<Movie>> getProducersWithMoreThanOneMovie(Map<String, List<Movie>> moviesGrouped) {
        return moviesGrouped.entrySet().stream()
                .filter(prod -> prod.getValue().size() > 1)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Set<String> findProducers() {
        Iterable<Movie> movies = findAll();
        Set<String> producers = new HashSet<>();
        movies.forEach(movie -> producers.addAll(movie.getProducersList()));
        return producers;
    }
}
