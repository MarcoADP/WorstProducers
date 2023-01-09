package com.textoit.worstproducers.service;

import com.textoit.worstproducers.entity.AwardInterval;
import com.textoit.worstproducers.entity.AwardIntervalMinMax;
import com.textoit.worstproducers.entity.Movie;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AwardIntervalService {

    final
    MovieService movieService;

    public AwardIntervalService(MovieService movieService) {
        this.movieService = movieService;
    }

    public AwardIntervalMinMax generateIntervalMinMax() {
        var producers = movieService.findProducersWithMoreThanOneMovie();

        List<AwardInterval> awardIntervalMin = new ArrayList<>();
        List<AwardInterval> awardIntervalMax = new ArrayList<>();
        for (var producer: producers.keySet()) {
            List<Movie> movies = producers.get(producer);
            movies.sort(Comparator.comparing(Movie::getYear));
            for (int i = 0; i < movies.size() - 1; i++) {
                awardIntervalMin = checkMinInterval(producer, movies.get(i).getYear(), movies.get(i+1).getYear(),
                        awardIntervalMin);
                awardIntervalMax = checkMaxInterval(producer, movies.get(i).getYear(), movies.get(i+1).getYear(),
                        awardIntervalMax);
            }

        }
        return new AwardIntervalMinMax(awardIntervalMin, awardIntervalMax);
    }

    private List<AwardInterval> checkMinInterval(
            String producer,
            int year,
            int nextYear,
            List<AwardInterval> awardIntervalMin
    ) {
        int interval = nextYear - year;
        if (awardIntervalMin.isEmpty() || awardIntervalMin.get(0).getInterval().equals(interval)) {
            awardIntervalMin.add(new AwardInterval(producer, interval, year, nextYear));
        } else if (awardIntervalMin.get(0).getInterval() > interval) {
            awardIntervalMin = new ArrayList<>();
            awardIntervalMin.add(new AwardInterval(producer, interval, year, nextYear));
        }
        return awardIntervalMin;
    }

    private List<AwardInterval> checkMaxInterval(
            String producer,
            int year,
            int nextYear,
            List<AwardInterval> awardIntervalMax
    ) {
        int interval = nextYear - year;
        if (awardIntervalMax.isEmpty() || awardIntervalMax.get(0).getInterval().equals(interval)) {
            awardIntervalMax.add(new AwardInterval(producer, interval, year, nextYear));
        } else if (awardIntervalMax.get(0).getInterval() < interval) {
            awardIntervalMax = new ArrayList<>();
            awardIntervalMax.add(new AwardInterval(producer, interval, year, nextYear));
        }
        return awardIntervalMax;
    }

}
