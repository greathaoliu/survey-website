package com.zju.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.zju.service.DesignService;
import com.zju.service.FillService;
import com.zju.result.Result;
import org.springframework.web.bind.annotation.*;

@RestController
public class FillController {
    final private FillService fillService;
    final private DesignService designService;
    final private Gson gson = new Gson();

    public FillController(FillService fillService, DesignService designService) {
        this.fillService = fillService;
        this.designService = designService;
    }

    @CrossOrigin
    @PostMapping("/api/canAnswer")
    public Result canAnswer(@RequestBody String reqBody) {
        JsonObject req = gson.fromJson(reqBody, JsonObject.class);
        Integer sid = req.get("sid").getAsInt();
        String IP = req.get("IP").getAsString();
        String username = req.get("username").getAsString();
        if(fillService.isFillable(sid, IP, username)) {
            return new Result(200);
        }
        return new Result(400);
    }

    @CrossOrigin
    @PostMapping("/api/submitAnswers")
    public Result submitASurvey(@RequestBody String reqBody) {
        JsonObject req = gson.fromJson(reqBody, JsonObject.class);
        Integer sid = req.get("sid").getAsInt();
        String IP = req.get("IP").toString();
        String username = req.get("username").toString();
        String aList = req.get("aList").toString();
        return fillService.submit(sid, username, IP , aList);
    }


    @CrossOrigin
    @PostMapping("/api/getQList")
    public String getQuestionList(@RequestParam Integer sid) {
            return fillService.getQList(sid);
    }

    @CrossOrigin
    @PostMapping("/api/getSurveyInfo")
    public String getSurveyInfo(@RequestParam Integer sid) {
        return fillService.getSurveyInfo(sid);
    }
}
