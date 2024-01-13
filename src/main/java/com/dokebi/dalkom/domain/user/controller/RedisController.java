package com.dokebi.dalkom.domain.user.controller;

import com.dokebi.dalkom.common.response.Response;
import com.dokebi.dalkom.domain.user.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;


@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private RedisService redisService;

    // GET
    @GetMapping("")
    public Response getValue(@RequestHeader("Authorization") String accessToken){
        String refreshToken = redisService.getValues(accessToken);
        return Response.success(refreshToken);
    }

    // POST
    @PostMapping("")
    public Response setValue(@RequestBody HashMap<String, String> body){
        redisService.setValues(body.get("accessToken"), body.get("refreshToken"));
        return Response.success();
    }

    // DELETE
    @DeleteMapping("")
    public Response deleteValue(@RequestHeader("Authorization") String accessToken){
        redisService.deleteValues(accessToken);
        return Response.success();
    }
}
