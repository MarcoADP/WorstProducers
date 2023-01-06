package com.textoit.worstproducers.repository;

import com.textoit.worstproducers.entity.Movie;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface MovieRepository extends CrudRepository<Movie, Integer> {

    List<Movie> findMoviesByWinnerTrue();

}
