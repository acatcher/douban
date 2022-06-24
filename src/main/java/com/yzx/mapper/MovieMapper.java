package com.yzx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yzx.entity.Movie;

public interface MovieMapper extends BaseMapper<Movie> {

    // spring task
    // compute score in schedule
    public void updateAvgScore();
}
