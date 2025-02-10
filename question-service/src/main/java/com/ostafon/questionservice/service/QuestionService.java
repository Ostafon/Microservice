package com.ostafon.questionservice.service;


import com.ostafon.questionservice.dao.QuestionDao;
import com.ostafon.questionservice.model.QuestionWrapper;
import com.ostafon.questionservice.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.ostafon.questionservice.model.Question;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    QuestionDao repo;

    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            if (repo.findAll().isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(repo.findAll(), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        try {
            if (repo.findByCategory(category).isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(repo.findByCategory(category), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);

    }

    public ResponseEntity<String> addQuestion(Question question) {
        repo.save(question);
        return new ResponseEntity<>("success",HttpStatus.CREATED);
    }

    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String category, Integer numberOfQuestions) {
        List<Integer> questions = repo.findRandomQuestionsByCategory(category, numberOfQuestions);
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionsById(List<Integer> questionIds) {
        List<QuestionWrapper> lqw = new ArrayList<>();
        List<Question> questions = new ArrayList<>();
        for(Integer questionId : questionIds) {
            questions.add(repo.findById(questionId).get());
        }
        for(Question question : questions) {
            QuestionWrapper qw = new QuestionWrapper(question.getId(),question.getQuestionTitle(),question.getOption1(),question.getOption2(),question.getOption3(),question.getOption4());
            lqw.add(qw);
        }
        return new ResponseEntity<>(lqw, HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(List<Response> responses) {
        Integer score = 0;
        for(Response response : responses) {
            Question question = repo.findById(response.getId()).get();
            if(response.getResponse().equals(question.getRightAnswer())){
                score++;
            }
        }
        return new ResponseEntity<>(score, HttpStatus.OK);
    }
}
