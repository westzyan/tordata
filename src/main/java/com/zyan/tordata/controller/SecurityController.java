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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;
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
    public Result<String> getData() throws FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        try {
            String str;
            BufferedReader in = new BufferedReader(new FileReader("/home/ysr/下载/tordata/src/main/resources/static/json/data1.json"));
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
        securityController.getData();
    }
}
