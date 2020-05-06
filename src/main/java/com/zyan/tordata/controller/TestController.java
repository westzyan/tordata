package com.zyan.tordata.controller;

import com.zyan.tordata.domain.UserStatsRelayCountry;
import com.zyan.tordata.result.Result;
import com.zyan.tordata.service.UserStatsRelayCountryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Slf4j
@Controller
public class TestController {

    @Autowired
    private UserStatsRelayCountryService userStatsRelayCountryService;

    @RequestMapping(value = "/echart")
    @ResponseBody
    public Result<List<UserStatsRelayCountry>> getOption(){
        log.info("触发一次/echart");
        List<UserStatsRelayCountry> list = userStatsRelayCountryService.listAllUser();
        List<UserStatsRelayCountry> list1 = userStatsRelayCountryService.listUserByCountryAndDate("cn", "2011-01-2","2020-03-23");
        return Result.success(list1);
    }
}

