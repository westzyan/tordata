package com.zyan.tordata.controller;


import com.alibaba.fastjson.JSON;
import com.zyan.tordata.domain.Category;
import com.zyan.tordata.domain.Data;
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
import java.util.*;

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
    public Result<List<String>> getData() {
        Node node = new Node();
        Node node2 = new Node();
        Link link = new Link();
        Link link2 = new Link();
        Category category = new Category();
        Category category2 = new Category();

        List<String> list = new LinkedList<>();
        List<Node> nodeList = new LinkedList<>();
        List<Link> linkList = new LinkedList<>();
        List<Category> categoryList = new LinkedList<>();

        node.setId(1);
        node.setName("key");
        node.setSymbolSize(19.12381);
        node.setX(-1.2);
        node.setY(2.4);
        node.setValue(11);
        node.setCategory(1);

        node2.setId(2);
        node2.setName("value");
        node2.setSymbolSize(19.12);
        node2.setX(-1.2235);
        node2.setY(2.465436);
        node2.setValue(15451);
        node2.setCategory(7);

        link.setSource(1);
        link.setTarget(2);

        link2.setSource(4);
        link2.setTarget(2);

        category.setName("类目1");
        category2.setName("类目4");

        nodeList.add(node);
        nodeList.add(node2);
        linkList.add(link);
        linkList.add(link2);
        categoryList.add(category);
        categoryList.add(category2);
        String nodeString = JSON.toJSONString(nodeList);
        String linkString = JSON.toJSONString(linkList);
        String cateString = JSON.toJSONString(categoryList);
        list.add(nodeString);
        list.add(linkString);
        list.add(cateString);

//        String res = JSON.toJSONString(list);
//        if (res.length() == 0) {
//            return Result.error(CodeMsg.NULL_DATA);
//        }
//        System.out.println(res);
        return Result.success(list);
    }

    public static void main(String[] args) throws FileNotFoundException {
        SecurityController securityController = new SecurityController();
        securityController.getData();
    }

    @RequestMapping("/radar")
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
        double[] weights = {0.11,0.12,0.13,0.03,
                            0.03,0.02,0.15,0.16,
                            0.12,0.19,0.16,0.10,
                            0.01,0.02,0.03,0.04};
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


    @RequestMapping("/radar_test")
    @ResponseBody
    public Result<List<Integer>> getRadarDataDefault() {
        //经过一系列逻辑处理
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            Integer a = new Random().nextInt(100);
            list.add(a);
            System.out.println(a);
        }
        return Result.success(list);
    }
}