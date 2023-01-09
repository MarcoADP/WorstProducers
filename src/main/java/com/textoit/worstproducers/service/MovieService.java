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

}
