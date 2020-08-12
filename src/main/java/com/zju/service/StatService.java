package com.zju.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.zju.dao.AnswerDAO;
import com.zju.dao.QuestionDAO;
import com.zju.dao.SurveyDAO;
import com.zju.entity.Answer;
import com.zju.entity.Question;
import com.zju.entity.Survey;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Service
public class StatService {
    private final SurveyDAO surveyDAO;
    private final QuestionDAO questionDAO;
    private final AnswerDAO answerDAO;
    private Gson gson = new Gson();

    public StatService(SurveyDAO surveyDAO, QuestionDAO questionDAO, AnswerDAO answerDAO) {
        this.surveyDAO = surveyDAO;
        this.questionDAO = questionDAO;
        this.answerDAO = answerDAO;
    }


    // 返回Json字符串
    // 某个用户创建的全部问题
    public String getSurveysByUsername(String username) {
        JsonObject result = new JsonObject();
        List<Survey> slist = surveyDAO.findAllByUsername(username);
        // 第二个参数要转化为JsonElement
        return gson.toJson(slist);
    }

    public String getStatBySidAndQno(Integer sid, Integer qno, String qType) {
        List<Answer> aList = answerDAO.findAllBySidAndQno(sid, qno);
        Question question = questionDAO.findDistinctBySidAndQno(sid, qno);
        switch (qType) {
            case "single_choice": {
                String optionsJson = question.getQOptions();
                JsonArray options = gson.fromJson(optionsJson, JsonArray.class);
                int[] optionNumbers = new int[options.size()];

                for(Answer tmp: aList) {
                    int choice = tmp.getAOption();
                    optionNumbers[choice]++;
                }
                return gson.toJson(optionNumbers);
            }
            case "multi_choice": {
                String optionsJson = question.getQOptions();
                JsonArray options = gson.fromJson(optionsJson, JsonArray.class);
                int[] optionNumbers = new int[options.size()];
                for(Answer tmp: aList) {
                    JsonArray optionsArray = gson.fromJson(tmp.getAOptions(), JsonArray.class);
                    for(JsonElement t: optionsArray) {
                        int choice = gson.fromJson(t, Integer.class);
                        optionNumbers[choice]++;
                    }
                }
                return gson.toJson(optionNumbers);
            }
            case "int": {
                float average = 0;
                for(Answer tmp: aList) {
                    int ans = tmp.getAInt();
                    average += ans;
                }
                average = average/aList.size();
                return String.valueOf(average);
            }
            case "float": {
                float average = 0;
                for(Answer tmp: aList) {
                    float ans = tmp.getAFloat();
                    average += ans;
                }
                average = average/aList.size();
                return String.valueOf(average);
            }
            case "rate": {
                float average = 0;
                for(Answer tmp: aList) {
                    int ans = tmp.getARate();
                    average += ans;
                }
                average = average/aList.size();
                return String.valueOf(average);
            }
            default:
                return "";
        }
    }
}
