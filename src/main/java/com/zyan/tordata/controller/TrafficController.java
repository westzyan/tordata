package com.zyan.tordata.controller;


import com.alibaba.fastjson.JSON;
import com.zyan.tordata.domain.*;
import com.zyan.tordata.result.CodeMsg;
import com.zyan.tordata.result.Result;
import com.zyan.tordata.service.*;
import com.zyan.tordata.util.DateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
@Slf4j
public class TrafficController {

    @Autowired
    private TotalRelayBandwidthService totalRelayBandwidthService;
    @Autowired
    private AdvAndConBwByRelayFlagsService advAndConBWByRelayFlagsService;
    @Autowired
    private AdvBwByIpVersionService advBWByIpVersionService;
    @Autowired
    private AdvBwDistributionService advBwDistributionService;
    @Autowired
    private AdvBwOfNthFastestRelaysService advBwOfNthFastestRelaysService;
    @Autowired
    private BwSpentOnRequestService bwSpentOnRequestService;
    @Autowired
    private FracOfConnService fracOfConnService;

    @RequestMapping("/traffic/advbwAndBwhistDefault")
    @ResponseBody
    public Result<List<TotalRelayBandwidth>> getAdvbwAndBwhistsDefault() {
        //默认当前三个月数据
        String end = DateTimeUtil.dateToStr(new Date());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -3);
        Date startDate = calendar.getTime();
        String start = DateTimeUtil.dateToStr(startDate);
        log.info("请求一次/servers/platformDefault，参数为start:{},end:{}", start, end);
        List<TotalRelayBandwidth> list = totalRelayBandwidthService.listAdvbwAndBwhists(start, end);
        return Result.success(list);

    }

    @RequestMapping("/traffic/advbwAndBwhist")
    @ResponseBody
    public Result<List<TotalRelayBandwidth>> getAdvbwAndBwhists(@Param("start") String start, @Param("end") String end) {
        log.info("请求一次/traffic/advbwAndBwhist");
        List<TotalRelayBandwidth> totalRelayBandwidthList = totalRelayBandwidthService.listAdvbwAndBwhists(start, end);
        if (totalRelayBandwidthList != null) {
            return Result.success(totalRelayBandwidthList);
        } else {
            //TODO 启动获取数据函数
            return Result.error(CodeMsg.NULL_DATA);
        }
    }

    @RequestMapping("/traffic/fillAdvbwAndBwhist")
    @ResponseBody
    public Result<Integer> fillAdvbwAndBwhists() throws NoSuchAlgorithmException, KeyManagementException {
        log.info("请求一次/traffic/fillAdvbwAndBwhist");
        int number = totalRelayBandwidthService.fillAdvbwAndBwhists();
        return Result.success(number);
    }

    @RequestMapping("/traffic/advAndConBwDefault")
    @ResponseBody
    public Result<List<List<List<String>>>> getAdvAndConBwDefault() {
        //默认当前三个月数据
        String end = DateTimeUtil.dateToStr(new Date());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -3);
        Date startDate = calendar.getTime();
        String start = DateTimeUtil.dateToStr(startDate);
        log.info("请求一次/traffic/advAndConBwDefault，参数为start:{},end:{}", start, end);
        List<List<List<String>>> list = advAndConBWByRelayFlagsService.listAdvAndConBwDefault(start, end);
        return Result.success(list);

    }

    @RequestMapping("/traffic/advAndConBw")
    @ResponseBody
    public Result<List<List<List<String>>>> getAdvAndConBw(@Param("start") String start, @Param("end") String end) {
        log.info("请求一次/traffic/advAndConBw，参数为start:{},end:{}", start, end);
        List<List<List<String>>> lists = advAndConBWByRelayFlagsService.listAdvAndConBwDefault(start, end);
        if (lists.size() == 0) {
            return Result.error(CodeMsg.NULL_DATA);
        }
        return Result.success(lists);
    }

    @RequestMapping("/traffic/fillAdvAndConBw")
    @ResponseBody
    public Result<Integer> fillAdvAndConBw() throws NoSuchAlgorithmException, KeyManagementException {
        log.info("请求一次/traffic/fillAdvAndConBw");
        int number = advAndConBWByRelayFlagsService.fillAAndCBW();
        return Result.success(number);
    }

    @RequestMapping("/traffic/advBwDefault")
    @ResponseBody
    public Result<List<AdvBwByIpVersion>> getBandwidthIpVersionDefault() {
        //默认当前三个月数据
        String end = DateTimeUtil.dateToStr(new Date());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -3);
        Date startDate = calendar.getTime();
        String start = DateTimeUtil.dateToStr(startDate);
        log.info("请求一次/servers/advBwDefault，参数为start:{},end:{}", start, end);
        List<AdvBwByIpVersion> list = advBWByIpVersionService.listAdvBWByIpVersion(start, end);
        return Result.success(list);

    }

    @RequestMapping("/traffic/advBw")
    @ResponseBody
    public Result<List<AdvBwByIpVersion>> getBandwidthIpVersion(@Param("start") String start, @Param("end") String end) {
        log.info("请求一次/traffic/advBw，参数为start:{},end:{}", start, end);
        List<AdvBwByIpVersion> advBwByIpVersionList = advBWByIpVersionService.listAdvBWByIpVersion(start, end);
        if (advBwByIpVersionList != null) {
            return Result.success(advBwByIpVersionList);
        } else {
            //TODO 启动获取数据函数
            return Result.error(CodeMsg.NULL_DATA);
        }
    }

    @RequestMapping("/traffic/fillAdvBw")
    @ResponseBody
    public Result<Integer> fillBandwidthIpVersion() throws NoSuchAlgorithmException, KeyManagementException {
        log.info("请求一次/traffic/fillAdvBw");
        int number = advBWByIpVersionService.fillAdvBWByIpVersion();
        return Result.success(number);
    }

    @RequestMapping("/traffic/advBwDisDefault")
    @ResponseBody
    public Result<List<List<List<String>>>> getAdvBwDisDefault() {
        //默认当前三个月数据
        String end = DateTimeUtil.dateToStr(new Date());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -3);
        Date startDate = calendar.getTime();
        String start = DateTimeUtil.dateToStr(startDate);
        log.info("请求一次/traffic/advBwDisDefault，参数为start:{},end:{}", start, end);
        List<List<List<String>>> list = advBwDistributionService.listAdvBwDisDefault(start, end);
        return Result.success(list);

    }

    @RequestMapping("/traffic/advBwDis")
    @ResponseBody
    public Result<List<List<List<String>>>> getAdvBwDis(@Param("start") String start, @Param("end") String end) {
        log.info("请求一次/traffic/advBwDis，参数为start:{},end:{}", start, end);
        List<List<List<String>>> lists = advBwDistributionService.listAdvBwDisDefault(start, end);
        if (lists.size() == 0) {
            return Result.error(CodeMsg.NULL_DATA);
        }
        return Result.success(lists);
    }

    @RequestMapping("/traffic/fillAdvBwDis")
    @ResponseBody
    public Result<Integer> fillAdvBwDis() throws NoSuchAlgorithmException, KeyManagementException {
        log.info("请求一次/traffic/fillAdvBwDis");
        int number = advBwDistributionService.fillAdvBwDis();
        return Result.success(number);
    }

    @RequestMapping("/traffic/advBwOfNthDefault")
    @ResponseBody
    public Result<List<List<List<String>>>> getAdvBwOfNthDefault() {
        //默认当前三个月数据
        String end = DateTimeUtil.dateToStr(new Date());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -3);
        Date startDate = calendar.getTime();
        String start = DateTimeUtil.dateToStr(startDate);
        log.info("请求一次/traffic/advBwOfNthDefault，参数为start:{},end:{}", start, end);
        List<List<List<String>>> list = advBwOfNthFastestRelaysService.listAdvBwOfNthDefault(start, end);
        return Result.success(list);

    }

    @RequestMapping("/traffic/advBwOfNth")
    @ResponseBody
    public Result<List<List<List<String>>>> getAdvBwOfNth(@Param("start") String start, @Param("end") String end) {
        log.info("请求一次/traffic/advBwOfNth，参数为start:{},end:{}", start, end);
        List<List<List<String>>> lists = advBwOfNthFastestRelaysService.listAdvBwOfNthDefault(start, end);
        if (lists.size() == 0) {
            return Result.error(CodeMsg.NULL_DATA);
        }
        return Result.success(lists);
    }

    @RequestMapping("/traffic/fillAdvBwOfNth")
    @ResponseBody
    public Result<Integer> fillAdvBwOfNth() throws NoSuchAlgorithmException, KeyManagementException {
        log.info("请求一次/traffic/fillAdvBwOfNth");
        int number = advBwOfNthFastestRelaysService.fillAdvBwOfNth();
        return Result.success(number);
    }

    @RequestMapping("/traffic/bwOnRequestDefault")
    @ResponseBody
    public Result<List<BwSpentOnRequest>> getBwOnRequestDefault() {
        //默认当前三个月数据
        String end = DateTimeUtil.dateToStr(new Date());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -3);
        Date startDate = calendar.getTime();
        String start = DateTimeUtil.dateToStr(startDate);
        log.info("请求一次/traffic/bwOnRequestDefault，参数为start:{},end:{}", start, end);
        List<BwSpentOnRequest> list = bwSpentOnRequestService.listBwOnRequest(start, end);
        return Result.success(list);

    }

    @RequestMapping("/traffic/bwOnRequest")
    @ResponseBody
    public Result<List<BwSpentOnRequest>> getBwOnRequest(@Param("start") String start, @Param("end") String end) {
        log.info("请求一次/traffic/bwOnRequest，参数为start:{},end:{}", start, end);
        List<BwSpentOnRequest> lists = bwSpentOnRequestService.listBwOnRequest(start, end);
        if (lists.size() == 0) {
            return Result.error(CodeMsg.NULL_DATA);
        }
        return Result.success(lists);
    }

    @RequestMapping("/traffic/fillBwOnRequest")
    @ResponseBody
    public Result<Integer> fillBwOnRequest() throws NoSuchAlgorithmException, KeyManagementException {
        log.info("请求一次/traffic/fillBwOnRequest");
        int number = bwSpentOnRequestService.fillBwOnRequest();
        return Result.success(number);
    }

    @RequestMapping("/traffic/fracOfConnDefault")
    @ResponseBody
    public Result<List<List<String>>> getFracOfConnDefault() {
        //默认当前三个月数据
        String end = DateTimeUtil.dateToStr(new Date());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -3);
        Date startDate = calendar.getTime();
        String start = DateTimeUtil.dateToStr(startDate);
        log.info("请求一次/servers/fracOfConnDefault，参数为start:{},end:{}", start, end);
        List<List<String>> list = fracOfConnService.listFracOfConnDefault(start, end);
        return Result.success(list);

    }

    @RequestMapping("/traffic/fracOfConn")
    @ResponseBody
    public Result<String> getFracOfConn(@Param("start") String start, @Param("end") String end) {
        log.info("请求一次/traffic/fracOfConn，参数为start:{},end:{}", start, end);
        List<FracOfConn> fracOfConnList = fracOfConnService.listFracOfConn(start, end);
        if (fracOfConnList != null) {
            String userJson = JSON.toJSONStringWithDateFormat(fracOfConnList, "yyyy-MM-dd");
            return Result.success(userJson);
        } else {
            //TODO 启动获取数据函数
            return Result.error(CodeMsg.NULL_DATA);
        }
    }

    @RequestMapping("/traffic/fillFracOfConn")
    @ResponseBody
    public Result<Integer> fillFracOfConn() throws NoSuchAlgorithmException, KeyManagementException {
        log.info("请求一次/traffic/fillFracOfConn");
        int number = fracOfConnService.fillFracOfConn();
        return Result.success(number);
    }
}
