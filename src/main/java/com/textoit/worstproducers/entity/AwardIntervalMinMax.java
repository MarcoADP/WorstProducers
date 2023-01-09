package com.textoit.worstproducers.entity;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AwardIntervalMinMax {

    private List<AwardInterval> min;

    private List<AwardInterval> max;

}
