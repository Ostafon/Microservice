package com.ostafon.quizservice.service;

import com.ostafon.quizservice.dao.QuizDao;
import com.ostafon.quizservice.feign.QuizInterFace;
import com.ostafon.quizservice.model.QuestionWrapper;
import com.ostafon.quizservice.model.Quiz;
import com.ostafon.quizservice.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;

    @Autowired
    QuizInterFace quizInterFace;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        List<Integer> questions = quizInterFace.generateQuestionsForQuiz(category,numQ).getBody();
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizDao.save(quiz);
        return new ResponseEntity<>("Quiz created", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        Quiz quiz = quizDao.findById(id).get();
        List<Integer> questions = quiz.getQuestions();

        return quizInterFace.getQuestionById(questions);
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {


        return quizInterFace.getScore(responses);
    }
}
