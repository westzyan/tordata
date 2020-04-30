package com.zyan.tordata.controller;


import com.alibaba.fastjson.JSON;
import com.zyan.tordata.domain.UserStatsRelayCountry;
import com.zyan.tordata.result.CodeMsg;
import com.zyan.tordata.result.Result;
import com.zyan.tordata.service.UserStatsRelayCountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Controller
public class UsersController {

    @Autowired
    private UserStatsRelayCountryService userStatsRelayCountryService;

    @RequestMapping("/users/allUser")
    @ResponseBody
    public Result<String> getAllUserStatsRelayCountry(){
        List<UserStatsRelayCountry> userStatsRelayCountryList = userStatsRelayCountryService.listAllUser();
        if (userStatsRelayCountryList != null){
            String userJson = JSON.toJSONStringWithDateFormat(userStatsRelayCountryList,"yyyy-MM-dd");
            return Result.success(userJson);
        }else {
            //TODO 启动获取数据函数

            return Result.error(CodeMsg.NULL_DATA);
        }
    }


    @RequestMapping("/users/allUser1")
    @ResponseBody
    public Result<Integer> getAllUserStatsRelayCountry1() throws NoSuchAlgorithmException, KeyManagementException {
        int number = userStatsRelayCountryService.fillUserStatsRelay();
        return Result.success(number);
    }
}
