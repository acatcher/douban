package com.yzx.service.Impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yzx.entity.Movie;
import com.yzx.service.MovieService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class MovieServiceImplTest {

    @Resource
    private MovieService movieService;

    @Test
    public void testGetPage(){
        IPage<Movie> page = movieService.getPage(-1L, null, 2, 10);
        for(Movie m : page.getRecords()){
            System.out.println(m);
        }
        System.out.println(page.getPages());
        System.out.println(page.getTotal());
    }


}