package com.ostafon.quizservice.feign;

import com.ostafon.quizservice.model.QuestionWrapper;
import com.ostafon.quizservice.model.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "QUESTIONSERVICE", url = "http://localhost:8080")
public interface QuizInterFace {

    @GetMapping("/question/generate")
    ResponseEntity<List<Integer>> generateQuestionsForQuiz(
            @RequestParam String category,
            @RequestParam Integer numberOfQuestions
    );

    @PostMapping("/question/getQuestions")
    ResponseEntity<List<QuestionWrapper>> getQuestionById(@RequestBody List<Integer> questionIds);

    @PostMapping("/question/getScore")
    ResponseEntity<Integer> getScore(@RequestBody List<Response> responses);
}
