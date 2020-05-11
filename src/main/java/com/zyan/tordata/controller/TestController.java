package com.zyan.tordata.controller;

import com.zyan.tordata.domain.UserStatsRelayCountry;
import com.zyan.tordata.result.Const;
import com.zyan.tordata.result.Result;
import com.zyan.tordata.service.UserStatsRelayCountryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.util.locale.provider.LocaleProviderAdapter;
import sun.util.locale.provider.ResourceBundleBasedAdapter;
import sun.util.resources.OpenListResourceBundle;

import java.util.*;
import java.util.stream.Collectors;

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

    public static void getCountries(){
        ResourceBundleBasedAdapter resourceBundleBasedAdapter = ((ResourceBundleBasedAdapter) LocaleProviderAdapter.forJRE());
        OpenListResourceBundle resource = resourceBundleBasedAdapter.getLocaleData().getLocaleNames(Locale.CHINA);
        System.out.println(resource.getString("AB"));

    }


    public void test(){
        String lastDate = userStatsRelayCountryService.getLastDate();
        if (!lastDate.equals(Const.lastDate)){
            System.out.println("ssssssssssss");

        }
    }


    public static void main(String[] args) {
        getCountries();
//        List<List<Object>> lists = new ArrayList<>();
//        List<String> list1 = new ArrayList<>();
//        List<Integer> list2 = new ArrayList<>();
//        list1.add("sss");
//        list1.add("ddd");
//        list2.add(1);
//        list2.add(2);
//        lists.add(Collections.singletonList(list1));
//        lists.add(Collections.singletonList(list2));
//
//
//        for (int i = 0; i < lists.get(0).size(); i++) {
//            System.out.println(lists.get(0).get(i));
//        }
    }
}

