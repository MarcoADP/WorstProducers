package com.textoit.worstproducers.controller;

import com.textoit.worstproducers.entity.AwardIntervalMinMax;
import com.textoit.worstproducers.service.AwardIntervalService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path="/award-interval")
public class AwardIntervalController {

    final
    AwardIntervalService awardIntervalService;

    public AwardIntervalController(AwardIntervalService awardIntervalService) {
        this.awardIntervalService = awardIntervalService;
    }

    @GetMapping()
    public @ResponseBody AwardIntervalMinMax get() {
        return awardIntervalService.generateIntervalMinMax();
    }

}
