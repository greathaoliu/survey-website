package com.zju.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.zju.service.StatService;
import org.springframework.web.bind.annotation.*;

@RestController
//
public class StatController {
    final private StatService statService;
    final private Gson gson = new Gson();

    public StatController(StatService statService) {
        this.statService = statService;
    }

    @CrossOrigin
    @PostMapping("api/getSurveys")
    public String getSurveys(@RequestParam String username) {
        return statService.getSurveysByUsername(username);
    }

    @CrossOrigin
    @PostMapping("api/getStat")
    public String getAnswerList(@RequestBody String reqBody) {
        JsonObject req = gson.fromJson(reqBody, JsonObject.class);
        Integer sid = req.get("sid").getAsInt();
        Integer qno = req.get("qno").getAsInt();
        String qType = req.get("qType").getAsString();
        return statService.getStatBySidAndQno(sid, qno, qType);
    }

}
