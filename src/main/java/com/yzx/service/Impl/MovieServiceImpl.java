package com.yzx.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yzx.entity.Evaluation;
import com.yzx.entity.MemberReadState;
import com.yzx.entity.Movie;
import com.yzx.mapper.EvaluationMapper;
import com.yzx.mapper.MemberReadStateMapper;
import com.yzx.mapper.MovieMapper;
import com.yzx.service.MovieService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@Service("movieService")
@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
public class MovieServiceImpl implements MovieService {
    @Resource
    private MovieMapper movieMapper;

    @Resource
    private MemberReadStateMapper memberReadStateMapper;

    @Resource
    private EvaluationMapper evaluationMapper;

    @Override
    public IPage<Movie> getPage(Long categoryId, String order, Integer page, Integer rows) {

        Page<Movie> p = new Page<Movie>(page, rows);
        QueryWrapper<Movie> qw = new QueryWrapper<>();

        if(categoryId != null && categoryId != -1){
            qw.eq("category_id", categoryId);
        }
        if(order != null) {
            if(order.equals("quantity")){
                qw.orderByDesc("evaluation_quantity");
            }else if(order.equals("score")){
                qw.orderByDesc("evaluation_score");
            }
        }

        IPage<Movie> ip = movieMapper.selectPage(p, qw);

        return ip;
    }

    @Override
    public Movie getMovie(Long movieId) {
        Movie movie = movieMapper.selectById(movieId);
        return movie;
    }

    @Override
    @Transactional
    public void updateAvgScore() {
        movieMapper.updateAvgScore();
    }

    @Override
    @Transactional
    public Movie addMovie(Movie movie) {
        movieMapper.insert(movie);
        return movie;
    }

    @Override
    @Transactional
    public Movie updateMovie(Movie movie) {
        movieMapper.updateById(movie);
        return movie;
    }

    @Override
    @Transactional
    public void deleteMovie(Long id) {

        movieMapper.deleteById(id);
        QueryWrapper<MemberReadState> qwMRS = new QueryWrapper<>();
        qwMRS.eq("book_id", id);
        QueryWrapper<Evaluation> qwEva = new QueryWrapper<>();
        qwEva.eq("book_id", id);
        memberReadStateMapper.delete(qwMRS);
        evaluationMapper.delete(qwEva);

    }


}
