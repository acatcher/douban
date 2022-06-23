package com.yzx.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yzx.entity.Category;
import com.yzx.entity.Evaluation;
import com.yzx.entity.Movie;
import com.yzx.service.CategoryService;
import com.yzx.service.EvaluationService;
import com.yzx.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class MovieController {

    @Resource
    private CategoryService categoryService;

    @Resource
    private MovieService movieService;

    @Resource
    private EvaluationService evaluationService;

//  首页加载
    @GetMapping("/")
    public ModelAndView getIndex(){
        ModelAndView mav = new ModelAndView("/index");
        List<Category> list = categoryService.selectAll();
        mav.addObject("categoryList", list);
        return mav;
    }

//  分页加载更多
    @GetMapping("/movie")
    @ResponseBody
    public IPage<Movie> getMovie(Long categoryId, String order, @RequestParam("p") Integer page){

        Integer rows = 10;
        IPage<Movie> p = movieService.getPage(categoryId, order, page, rows);

        return p;

    }

//    获取详细信息
    @GetMapping("/movie/{id}")
    public ModelAndView getDetail(@PathVariable("id") Long movieId){
        //book info
        Movie movie = movieService.getMovie(movieId);
        //evaluation info
        List<Evaluation> evaluations = evaluationService.getEvaluationById(movieId);

        //mav return
        ModelAndView mav = new ModelAndView("/movieDetails");
        mav.addObject("book", movie);
        mav.addObject("evaluationList", evaluations);

        return mav;
    }




}
