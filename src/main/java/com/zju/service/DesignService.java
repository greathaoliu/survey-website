package com.zju.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.zju.dao.QuestionDAO;
import com.zju.dao.SurveyDAO;
import com.zju.entity.Question;
import com.zju.entity.Survey;
import com.zju.result.Result;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class DesignService {
    private final SurveyDAO surveyDAO;
    private final QuestionDAO questionDAO;
    private Gson gson = new Gson();

    public DesignService(SurveyDAO surveyDAO, QuestionDAO questionDAO) {
        this.surveyDAO = surveyDAO;
        this.questionDAO = questionDAO;
    }


    // 分为保存到survey表和保存到question表两部分
    public Result saveSurvey(String surveyInfo, String qList) {
        JsonObject surveyinfoJson = gson.fromJson(surveyInfo, JsonObject.class);
        JsonArray qListJson = gson.fromJson(qList, JsonArray.class);

        // 保存到survey表
        Survey surveyEntity = gson.fromJson(surveyinfoJson, Survey.class);
        surveyEntity.setSid(0);
        surveyEntity.setFillCount(0);
        surveyEntity = surveyDAO.save(surveyEntity);
        //
        for(JsonElement tmp:qListJson) {
            JsonObject qJson = gson.fromJson(gson.toJson(tmp), JsonObject.class);
            Question qEntity = new Question();
            qEntity.setQid(0);
            qEntity.setQno(qJson.get("qno").getAsInt());
            qEntity.setQType(qJson.get("qType").getAsString());
            qEntity.setQOptions(gson.toJson(qJson.get("qOptions")));
            qEntity.setQDescription(qJson.get("qDescription").getAsString());
            qEntity.setQIsRequired(qJson.get("qIsRequired").getAsBoolean());
            qEntity.setMaxrate(qJson.get("maxrate").getAsInt());
            qEntity.setSid(surveyEntity.getSid());
            questionDAO.save(qEntity);
        }
        return new Result(200, "问卷保存成功！");
    }


    public Result deleteSurvey(Integer sid) {
        // 删除问卷信息
        surveyDAO.deleteDistinctBySid(sid);
        // 删除对应问题
        questionDAO.deleteDistinctBySid(sid);
        return new Result(200, "问卷删除成功！");
    }

    public Result releaseSurvey(Integer sid) {
        surveyDAO.updateSurveyStatus(sid, 1);
        return new Result(200, "修改问卷状态成功！");
    }


}
