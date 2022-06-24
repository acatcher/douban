package com.yzx.service;

import com.yzx.entity.Evaluation;

import java.util.List;

public interface EvaluationService {
    public List<Evaluation> getEvaluationById(Long id);

    public Evaluation addEvaluation(Long memberId, Long bookId, Integer score, String content);

    public Evaluation likeEvaluation(Long evaluationId);
}
