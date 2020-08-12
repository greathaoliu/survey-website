package com.zju;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.zju.dao.SurveyDAO;
import com.zju.entity.Question;
import com.zju.entity.Survey;

import java.util.ArrayList;
import java.util.List;

public class test {
    private static SurveyDAO surveyDAO;

    public static void main(String[] args){
        Gson gson = new Gson();
//        List<Question> list = new ArrayList<Question>();
//        Question q = new Question();
//        q.setQid(1);
//        q.setSid(2);
//        q.setQDescription("aaa");
//        q.setQIsRequired(true);
////        q.setQshow(true);
////        q.setQType("single");
////        q.setQTitle("bababa");
////        q.setQsubsequents("aa");
//        list.add(q);
//        list.add(q);
//        System.out.println(gson.toJson(list));
//        JsonObject result = new JsonObject();
//        result.add("test", gson.fromJson(gson.toJson(list), JsonArray.class));
//
//        System.out.println(gson.toJson(result));
        String rBody = "{\"surveyInfo\":{\"username\":\"admin\",\"sDescription\":\"b\",\"sTitle\":\"a\",\"sid\":-1,\"fillType\":0,\"fillnum\":1,\"status\":0,\"starttime\":\"2020-06-20T07:05:39.563Z\",\"endtime\":\"2020-06-20T07:05:39.563Z\"},\"qList\":[{\"qNo\":0,\"qIsRequired\":true,\"qDescription\":\"\",\"qType\":\"single_choice\",\"qOptions\":[\"b\"],\"maxrate\":5}]}";
        JsonObject rJson = gson.fromJson(rBody, JsonObject.class);
//        String surveyInfo = rJson.get("surveyInfo").toString();
        String qList = rJson.get("qList").toString();
        JsonArray qListJson = gson.fromJson(qList, JsonArray.class);
        for(JsonElement tmp:qListJson) {
            JsonObject qJson = gson.fromJson(gson.toJson(tmp), JsonObject.class);
            Question qEntity = new Question();
            qEntity.setQid(0);
            qEntity.setQno(qJson.get("qNo").getAsInt());
            qEntity.setQType(qJson.get("qType").getAsString());
            qEntity.setQOptions(gson.toJson(qJson.get("qOptions")));
            qEntity.setQDescription(qJson.get("qDescription").getAsString());
            qEntity.setQIsRequired(qJson.get("qIsRequired").getAsBoolean());
            qEntity.setMaxrate(qJson.get("maxrate").getAsInt());
        }
//        System.out.println(surveyInfo);
//        System.out.println(qList);
//        JsonObject surveyinfoJson = gson.fromJson(surveyInfo, JsonObject.class);
//        Survey surveyEntity = new Survey();
//        Survey surveyEntity2 = new Survey();
        //surveyEntity = gson.fromJson(surveyinfoJson, Survey.class);
//        surveyDAO.save(surveyEntity);

    }
}