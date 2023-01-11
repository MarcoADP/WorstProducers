package com.textoit.worstproducers.controller;

import com.textoit.worstproducers.entity.Movie;
import com.textoit.worstproducers.service.MovieService;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path="/movie")
public class MovieController {

    final
    MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping()
    public @ResponseBody Iterable<Movie> findAll() {
        return movieService.findAll();
    }

    @GetMapping("/winners")
    public @ResponseBody List<Movie> findWinners() {
        return movieService.findWinners();
    }

    @GetMapping("/producers")
    public @ResponseBody Set<String> findProducers() {
        return movieService.findProducers();
    }

}
