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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;
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
    public Result<String> getData() {
        Node node = new Node();
        Node node2 = new Node();
        Link link = new Link();
        Link link2 = new Link();
        Category category = new Category();
        Category category2 = new Category();

        List<Object> list=new LinkedList<>();
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

        list.add(nodeList);
        list.add(linkList);
        list.add(categoryList);

        String res = JSON.toJSONString(list);
        if (res.length() == 0) {
            return Result.error(CodeMsg.NULL_DATA);
        }
        System.out.println(res);
        return Result.success(res);
    }

    public static void main(String[] args) throws FileNotFoundException {
        SecurityController securityController = new SecurityController();
        securityController.getData();
    }
}
