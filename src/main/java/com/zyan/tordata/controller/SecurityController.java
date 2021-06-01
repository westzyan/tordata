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
import java.util.ArrayList;
import java.util.Collections;
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