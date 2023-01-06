package com.textoit.worstproducers.entity;

import java.util.Arrays;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "movie")
@Getter
@NoArgsConstructor
public class Movie {

    private static final Integer YEAR_IDX = 0;
    private static final Integer TITLE_IDX = 1;
    private static final Integer STUDIOS_IDX = 2;
    private static final Integer PRODUCERS_IDX = 3;
    private static final Integer WINNER_IDX = 4;

    private static final String COMMA_SEP = ", ";
    private static final String AND_SEP = " and ";
    private static final String COMMA_AND_SEP = ", and ";

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    @Column(name = "year_released")
    private Integer year;

    @NotBlank
    private String title;

    @NotBlank
    private String studios;

    @NotBlank
    private String producers;

    private Boolean winner;

    public Movie(Integer year, String title, String studios, String producers, Boolean winner) {
        this.year = year;
        this.title = title;
        this.studios = studios;
        this.producers = producers;
        this.winner = winner;
    }

    public Movie(List<String> columns) {
        this.year = Integer.parseInt(columns.get(YEAR_IDX));
        this.title = columns.get(TITLE_IDX);
        this.studios = columns.get(STUDIOS_IDX);
        this.producers = transformProducers(columns.get(PRODUCERS_IDX));
        this.winner = isWinner(columns);
    }

    private String transformProducers(String producers) {
        return removeAndSeparator(producers);
    }

    private String removeAndSeparator(String producers) {
        String[] a = producers.split(String.format("%s|%s|%s", COMMA_AND_SEP, AND_SEP, COMMA_SEP));
        return String.join(",", a);
    }

    private Boolean isWinner(List<String> columns) {
        return columns.size() == 5 && columns.get(WINNER_IDX).equalsIgnoreCase("yes");
    }

    public List<String> getProducersList() {
        return Arrays.asList(producers.split(","));
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", year=" + year +
                ", title='" + title + '\'' +
                ", studios='" + studios + '\'' +
                ", producers='" + producers + '\'' +
                ", winner=" + winner +
                '}';
    }
}
