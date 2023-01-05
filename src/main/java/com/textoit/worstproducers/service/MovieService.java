package com.textoit.worstproducers.service;

import com.textoit.worstproducers.entity.Movie;
import com.textoit.worstproducers.repository.MovieRepository;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
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

}
