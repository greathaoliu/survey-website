package com.zju.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.zju.service.DesignService;
import com.zju.result.Result;
import org.springframework.web.bind.annotation.*;


@RestController
// 名字起得不太准确，本控制器内还包含了许多其他管理问卷的功能
public class DesignController {
    final DesignService designService;
    final Gson gson;
    public DesignController(DesignService designService, Gson gson) {
        this.designService = designService;
        this.gson = gson;
    }

    @CrossOrigin
    @PostMapping("api/saveSurvey")
    public Result saveSurvey(@RequestBody String reqBody) {
        JsonObject req = gson.fromJson(reqBody, JsonObject.class);
        //{"sDescription":"问卷描述","sTitle":"问卷标题","sid":-1,"fillType":0,"fillnum":1,"endtime":"2020-06-20T04:04:59.162Z"}
        String surveyInfo = req.get("surveyInfo").toString();
        String qList = req.get("qList").toString();
        return designService.saveSurvey(surveyInfo, qList);
    }

    @CrossOrigin
    @PostMapping("api/deleteSurvey")
    public Result deleteSurvey(@RequestParam Integer sid) {
        return designService.deleteSurvey(sid);
    }

    @CrossOrigin
    @PostMapping("api/releaseSurvey")
    public Result releaseSurvey(@RequestParam Integer sid) {
        return designService.releaseSurvey(sid);
    }

}
