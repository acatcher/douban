package com.yzx.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yzx.entity.Evaluation;
import com.yzx.entity.Movie;
import com.yzx.mapper.EvaluationMapper;
import com.yzx.mapper.MovieMapper;
import com.yzx.service.EvaluationService;
import com.yzx.service.MovieService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
@Service("evaluationService")
@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
public class EvaluationServiceImpl implements EvaluationService {

    @Resource
    private EvaluationMapper evaluationMapper;

    @Resource
    private MovieMapper movieMapper;

    @Override
    public List<Evaluation> getEvaluationById(Long id) {
        // find its movie info
        Movie movie = movieMapper.selectById(id);
        //query
        QueryWrapper<Evaluation> qw = new QueryWrapper<>();
        qw.eq("book_id", id);
        qw.eq("state", "enable");
        qw.orderByDesc("create_time");
        List<Evaluation> evaluations = evaluationMapper.selectList(qw);

//        add movie info to evaluation
        for(Evaluation e:evaluations){
            e.setMovie(movie);
        }




        return evaluations;
    }
}
