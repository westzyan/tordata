package com.zyan.tordata.controller;


import com.alibaba.fastjson.JSON;
import com.zyan.tordata.domain.*;
import com.zyan.tordata.result.CodeMsg;
import com.zyan.tordata.result.Result;
import com.zyan.tordata.service.AssessmentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;
import java.util.*;

@Slf4j
@Controller
public class SecurityController {

    @Autowired
    AssessmentService assessmentService;

    @RequestMapping("/security/radar")
    @ResponseBody
    public Result<List<List<Integer>>> getRadarData(@Param("indicator1") Integer indicator1,
                                              @Param("indicator2") Integer indicator2,
                                              @Param("indicator3") Integer indicator3,
                                              @Param("indicator4") Integer indicator4,
                                              @Param("indicator5") Integer indicator5,
                                              @Param("indicator6") Integer indicator6,
                                              @Param("indicator7") Integer indicator7,
                                              @Param("indicator8") Integer indicator8,
                                              @Param("indicator9") Integer indicator9,
                                              @Param("indicator10") Integer indicator10,
                                              @Param("indicator11") Integer indicator11,
                                              @Param("indicator12") Integer indicator12,
                                              @Param("indicator13") Integer indicator13,
                                              @Param("indicator14") Integer indicator14,
                                              @Param("indicator15") Integer indicator15,
                                              @Param("indicator16") Integer indicator16
    ) {
        //经过一系列逻辑处理
        List<Integer> list = new ArrayList<>();
        list.add(indicator1);
        list.add(indicator2);
        list.add(indicator3);
        list.add(indicator4);
        list.add(indicator5);
        list.add(indicator6);
        list.add(indicator7);
        list.add(indicator8);
        list.add(indicator9);
        list.add(indicator10);
        list.add(indicator11);
        list.add(indicator12);
        list.add(indicator13);
        list.add(indicator14);
        list.add(indicator15);
        list.add(indicator16);
        for (Integer integer : list) {
            System.out.println(integer);
        }
        List<Integer> result_list = new ArrayList<>();
        double[] weights = {0.094211,0.094211,0.126751,0.043933,
                            0.104014,0.015805,0.066251,0.065321,
                            0.062217,0.058361,0.056751,0.104014,
                            0.067642,0.093956,0.093956,0.032566};
        double lastValue = 0;
        for (int i = 0; i < 16; i++) {
            lastValue = lastValue + list.get(i) * weights[i];
        }
        result_list.add((int)lastValue);
        List<List<Integer>> objects = new ArrayList<>();
        objects.add(list);
        objects.add(result_list);
        System.out.println(lastValue);
        return Result.success(objects);
    }

    @RequestMapping("/security/assessment_search_body")
    @ResponseBody
    public List<Assessment> ListAssessmentByCondition(@Param("condition") String condition){
        System.out.println(condition);
        List<Assessment> list = new ArrayList<>();
        if (condition == null || condition.isEmpty()) {
            return list;
        }
        condition = "%" + condition + "%";
        List<Assessment> assessmentList = assessmentService.listAssessment(condition);
        for (Assessment assessment : assessmentList) {
            System.out.println(assessment);
        }
        return assessmentList;
    }


    @RequestMapping("/search")
    public String Search(){
        return "tables";
    }

    @RequestMapping("/assessment_search")
    public String ListAssessment(ModelMap modelMap, @Param("condition") String condition){
        System.out.println(condition);
        List<Assessment> list = new ArrayList<>();
        condition = "%" + condition + "%";
        List<Assessment> assessmentList = assessmentService.listAssessment(condition);
        for (Assessment assessment : assessmentList) {
            System.out.println(assessment);
        }
        modelMap.addAttribute("result", assessmentList);
        return "tables";
    }



}