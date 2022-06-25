package com.yzx.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yzx.entity.Movie;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

public interface MovieService {
    public IPage<Movie> getPage(Long categoryId, String order, Integer page, Integer rows);

    public Movie getMovie(Long movieId);

    public void updateAvgScore();

    public Movie addMovie(Movie movie);

    public Movie updateMovie(Movie movie);

    public void deleteMovie(Long id);

}
