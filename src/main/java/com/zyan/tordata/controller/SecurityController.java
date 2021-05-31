package com.zyan.tordata.controller;


import com.alibaba.fastjson.JSON;
import com.zyan.tordata.domain.Category;
import com.zyan.tordata.domain.Link;
import com.zyan.tordata.domain.Node;
import com.zyan.tordata.result.CodeMsg;
import com.zyan.tordata.result.Result;
import com.zyan.tordata.service.OnionServiceTrafficService;
import com.zyan.tordata.service.OnionUniqueAddressService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping(value = "/security")
public class SecurityController {
    @Autowired
    private OnionServiceTrafficService onionServiceTrafficService;
    @Autowired
    private OnionUniqueAddressService onionUniqueAddressService;


    @RequestMapping("/association_graph")
    @ResponseBody
    public Result<String> getData(@Param("filename")String fileName) throws FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        fileName = "/media/zyan/文档/项目/安全评估/tordata/src/main/resources/static/json/data1.json";
        try {
            String str;
            BufferedReader in = new BufferedReader(new FileReader(fileName));
            while ((str = in.readLine()) != null) {
                sb.append(str.trim());
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        String res = sb.toString();
        if (res.length() == 0) {
            return Result.error(CodeMsg.NULL_DATA);
        }
        System.out.println(res);
        return Result.success(res);
    }

    @RequestMapping("/association_graph_default")
    @ResponseBody
    public Result<String> getDataDefault() throws FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        String filename = "/media/zyan/文档/项目/安全评估/tordata/src/main/resources/static/json/data1.json";
        try {
            String str;
            BufferedReader in = new BufferedReader(new FileReader(filename));
            while ((str = in.readLine()) != null) {
                sb.append(str.trim());
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        String res = sb.toString();
        if (res.length() == 0) {
            return Result.error(CodeMsg.NULL_DATA);
        }
        System.out.println(res);
        return Result.success(res);
    }


    public static void main(String[] args) throws FileNotFoundException {
        SecurityController securityController = new SecurityController();
        securityController.getData("/media/zyan/文档/项目/安全评估/tordata/src/main/resources/static/json/data1.json");
    }

    @RequestMapping("/radar")
    @ResponseBody
    public Result<List<Integer>> getRadarData(@Param("indicator1") int indicator1,
                                              @Param("indicator2") int indicator2,
                                              @Param("indicator3") int indicator3,
                                              @Param("indicator4") int indicator4,
                                              @Param("indicator5") int indicator5,
                                              @Param("indicator6") int indicator6,
                                              @Param("indicator7") int indicator7,
                                              @Param("indicator8") int indicator8,
                                              @Param("indicator9") int indicator9,
                                              @Param("indicator10") int indicator10,
                                              @Param("indicator11") int indicator11,
                                              @Param("indicator12") int indicator12,
                                              @Param("indicator13") int indicator13,
                                              @Param("indicator14") int indicator14,
                                              @Param("indicator15") int indicator15,
                                              @Param("indicator16") int indicator16
                                              ) throws FileNotFoundException {
        //经过一系列逻辑处理
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            list.add(i * 3);
        }
        return Result.success(list);
    }

}
