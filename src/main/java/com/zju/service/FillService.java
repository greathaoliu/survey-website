package com.zju.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.zju.dao.AnswerDAO;
import com.zju.dao.FillDAO;
import com.zju.dao.QuestionDAO;
import com.zju.dao.SurveyDAO;
import com.zju.entity.Answer;
import com.zju.entity.Fill;
import com.zju.entity.Question;
import com.zju.entity.Survey;
import com.zju.result.Result;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class FillService {
    final private AnswerDAO answerDAO;
    final private SurveyDAO surveyDAO;
    final private QuestionDAO questionDAO;
    final private FillDAO fillDAO; // 负责查看已有填写关系
    final private Gson gson = new Gson();

    public FillService(AnswerDAO answerDAO, SurveyDAO surveyDAO, FillDAO fillDAO, QuestionDAO questionDAO) {
        this.answerDAO = answerDAO;
        this.surveyDAO = surveyDAO;
        this.fillDAO = fillDAO;
        this.questionDAO = questionDAO;
    }


    public Boolean isFillable(Integer sid, String ip, String username) {
        Survey survey = surveyDAO.findDistinctBySid(sid);
        if(survey.getStatus() == 0 || survey.getStatus() == 2) {
            return false;
        }
        Date now = new Date();
        if(now.after(survey.getEndtime())) {
            surveyDAO.updateSurveyStatus(sid, 2);
            return false;
        }
        if (survey.getFillType() == 0) {
            return !username.equals("");
        } else if(survey.getFillType() == 1) {
            List<Fill> fills = fillDAO.findAllByIpAndSid(ip, sid);
            int fCountToday = 0;
            for(Fill fill: fills) {
                if(fill.getFDate().getDay() == now.getDay()) {
                    fCountToday ++;
                }
            }
            return fCountToday < survey.getFillnum();
        } else {
            List<Fill> fills = fillDAO.findAllByIpAndSid(ip, sid);
            return fills.size() < survey.getFillnum();
        }
    }

    public Result submit(Integer sid, String username, String IP, String aList) {
        // 先写入填写关系，本来fill中的值应该是外键，阿里巴巴Java规范中提到避免使用外键，尽量从业务代码中完成功能
        Fill fill = new Fill();
        fill.setFid(0);
        fill.setIp(IP);
        fill.setUsername(username);
        fill.setSid(sid);
        fill.setFDate(new Date());
        fillDAO.save(fill);
        surveyDAO.incFillCountBySid(sid);
        // [{"sid":"51","qno":0,"qType":"single_choice","aOption":1,"aOptions":[],"aRate":0,"aFloat":0,"aInt":0,"aText":""},{"sid":"51","qno":1,"qType":"multi_choice","aOption":0,"aOptions":[0,2],"aRate":0,"aFloat":0,"aInt":0,"aText":""},{"sid":"51","qno":2,"qType":"single_text","aOption":0,"aOptions":[],"aRate":0,"aFloat":0,"aInt":0,"aText":"这是一行文本的答案"},{"sid":"51","qno":3,"qType":"text","aOption":0,"aOptions":[],"aRate":0,"aFloat":0,"aInt":0,"aText":"这是第一行\n这是第二行\n这是第三行"},{"sid":"51","qno":4,"qType":"int","aOption":0,"aOptions":[],"aRate":0,"aFloat":"777","aInt":0,"aText":""},{"sid":"51","qno":5,"qType":"float","aOption":0,"aOptions":[],"aRate":0,"aFloat":0,"aInt":"888.88","aText":""},{"sid":"51","qno":6,"qType":"rate","aOption":0,"aOptions":[],"aRate":4,"aFloat":0,"aInt":0,"aText":""}]
        // 写入答案
        JsonArray answerListJson = gson.fromJson(aList, JsonArray.class);
        for (JsonElement tmp : answerListJson) {
            JsonObject aJson = gson.fromJson(gson.toJson(tmp), JsonObject.class);
            Answer answer = new Answer();
            answer.setAid(0);
            answer.setIp(IP);
            answer.setUsername(username);
            answer.setSid(sid);
            answer.setQno(aJson.get("qno").getAsInt());
            answer.setQType(aJson.get("qType").getAsString());
            answer.setAFloat(aJson.get("aFloat").getAsFloat());
            answer.setAInt(aJson.get("aInt").getAsInt());
            answer.setAOption(aJson.get("aOption").getAsInt());
            answer.setAOptions(gson.toJson(aJson.get("aOptions")));
            answer.setARate(aJson.get("aRate").getAsInt());
            answer.setAText(aJson.get("aText").getAsString());
            answerDAO.save(answer);
        }

        return new Result(200);
    }

    // json返回一个问卷的所有问题
    public String getQList(Integer sid) {
        List<Question> qList = questionDAO.findAllBySid(sid);
        return gson.toJson(qList);
    }

    public String getSurveyInfo(Integer sid) {
        Survey surveyInfo = surveyDAO.findDistinctBySid(sid);
        return gson.toJson(surveyInfo);
    }
}
