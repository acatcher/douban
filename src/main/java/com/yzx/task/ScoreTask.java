package com.yzx.task;

import com.yzx.service.MovieService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Component
public class ScoreTask {

    @Resource
    MovieService movieService;

    // in each minute
    @Scheduled(cron = "0 * * * * ?")
    public void updateAvgScore(){
        movieService.updateAvgScore();
        System.out.println("Score Re-compute Now!");
    }


}
