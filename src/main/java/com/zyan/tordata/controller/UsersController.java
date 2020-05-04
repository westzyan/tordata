package com.zyan.tordata.controller;


import com.alibaba.fastjson.JSON;
import com.zyan.tordata.domain.UserStatsRelayCountry;
import com.zyan.tordata.result.CodeMsg;
import com.zyan.tordata.result.Result;
import com.zyan.tordata.service.*;
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
    @Autowired
    private UserStatsBridgeCountryService userStatsBridgeCountryService;
    @Autowired
    private UserStatsBridgeTransportService userStatsBridgeTransportService;
    @Autowired
    private UserStatsBridgeCombinedService userStatsBridgeCombinedService;
    @Autowired
    private UserStatsBridgeVersionService userStatsBridgeVersionService;
    @Autowired
    private BridgeDBDistributorService bridgeDBDistributorService;
    @Autowired
    private BridgeDBTransportService bridgeDBTransportService;

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

    @RequestMapping("/users/fillBridgeCountry")
    @ResponseBody
    public Result<Integer> fill() throws NoSuchAlgorithmException, KeyManagementException {
        int number = userStatsBridgeCountryService.fillUserStatsBridgeCountry();
        return Result.success(number);
    }

    @RequestMapping("/users/fillBridgeTransport")
    @ResponseBody
    public Result<Integer> fillBridgeTransport() throws NoSuchAlgorithmException, KeyManagementException {
        int number = userStatsBridgeTransportService.fillUserStatsBridgeTransport();
        return Result.success(number);
    }

    @RequestMapping("/users/fillBridgeCombined")
    @ResponseBody
    public Result<Integer> fillBridgeCombined() throws NoSuchAlgorithmException, KeyManagementException {
        int number = userStatsBridgeCombinedService.fillUserStatsBridgeCombined();
        return Result.success(number);
    }

    @RequestMapping("/users/fillBridgeVersion")
    @ResponseBody
    public Result<Integer> fillBridgeVersion() throws NoSuchAlgorithmException, KeyManagementException {
        int number = userStatsBridgeVersionService.fillUserStatsBridgeVersion();
        return Result.success(number);
    }

    @RequestMapping("/users/fillBridgeDBDistributor")
    @ResponseBody
    public Result<Integer> fillBridgeDBDistributor() throws NoSuchAlgorithmException, KeyManagementException {
        int number = bridgeDBDistributorService.fillBridgeDBDistributor();
        return Result.success(number);
    }

    @RequestMapping("/users/fillBridgeDBTransport")
    @ResponseBody
    public Result<Integer> fillBridgeDBTransport() throws NoSuchAlgorithmException, KeyManagementException {
        int number = bridgeDBDistributorService.fillBridgeDBDistributor();
        return Result.success(number);
    }
}
