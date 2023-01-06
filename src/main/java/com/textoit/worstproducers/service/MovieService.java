package com.textoit.worstproducers.service;

import com.textoit.worstproducers.entity.Movie;
import com.textoit.worstproducers.repository.MovieRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public void populate(String moviesCsv) {
        List<String> rows = getRows(moviesCsv);
        rows.forEach(this::convertToMovie);
        findProducers();
    }

    private List<String> getRows(String moviesCsv) {
        return Arrays.asList(moviesCsv.split("\n"));
    }

    private void convertToMovie(String row) {
        List<String> columns = getColumns(row);

        if (isNumber(columns.get(0))) {
            Movie movie = new Movie(columns);
            save(movie);
        }
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

    public Movie save(Movie movie) {
        return movieRepository.save(movie);
    }

    public List<Movie> findWinners() {
        return movieRepository.findMoviesByWinnerTrue();
    }

    public void findProducers() {
        var movies = findWinners();
        List<String> producers = getProducers(movies);
        Map<String, Integer> producersMap = countMoviesByProducer(producers);
        List<String> producersFiltered = getProducersWithMoreThanOneMovie(producersMap);
        System.out.println("\nPRODUCERS");
        producersFiltered.forEach(System.out::println);
    }

    public List<String> getProducersWithMoreThanOneMovie(Map<String, Integer> producersMap) {
        return producersMap.entrySet().stream().filter(prod -> prod.getValue() > 1).
                map(Map.Entry::getKey).collect(Collectors.toList());
    }

    public List<String> getProducers(List<Movie> movies) {
        List<String> producers = new ArrayList<>();
        movies.forEach(movie -> producers.addAll(movie.getProducersList()));
        return producers;
    }

    public Map<String, Integer> countMoviesByProducer(List<String> producers) {
        Map<String, Integer> producersMap = new HashMap<>();
        producers.forEach(e -> producersMap.compute(e, (k, v) -> v == null ? 1 : v + 1));
        return producersMap;
    }

}
