package com.textoit.worstproducers.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AwardInterval {

    private String producer;

    private Integer interval;

    private Integer previousWin;

    private Integer followingWin;


}
