package com.zyan.tordata.controller;


import com.zyan.tordata.domain.BridgesByIpVersion;
import com.zyan.tordata.domain.RelaysAndBridges;
import com.zyan.tordata.domain.RelaysByIpVersion;
import com.zyan.tordata.domain.RelaysByPlatform;
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
public class ServersController {
    @Autowired
    private RelaysAndBridgesService relaysAndBridgesService;
    @Autowired
    private RelaysByRelayFlagService relaysByRelayFlagService;
    @Autowired
    private RelaysByTorVersionService relaysByTorVersionService;
    @Autowired
    private RelaysByPlatformService relaysByPlatformService;
    @Autowired
    private RelaysByIpVersionService relaysByIpVersionService;
    @Autowired
    private BridgesByIpVersionService bridgesByIpVersionService;
    @Autowired
    private TotalConsensusWeightsAcrossBwAuService totalConsensusWeightsAcrossBwAuService;

    @RequestMapping("/servers/relaysAndBridgeDefault")
    @ResponseBody
    public Result<List<RelaysAndBridges>> getRelaysAndBridgesDefault() {
        //默认当前三个月数据
        String end = DateTimeUtil.dateToStr(new Date());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -3);
        Date startDate = calendar.getTime();
        String start = DateTimeUtil.dateToStr(startDate);
        log.info("请求一次/servers/relaysAndBridgeDefault，参数为start:{},end:{}", start, end);
        List<RelaysAndBridges> list = relaysAndBridgesService.listRelaysAndBridgesDefault(start, end);
        return Result.success(list);
    }

    @RequestMapping("/servers/relaysAndBridge")
    @ResponseBody
    public Result<List<RelaysAndBridges>> getRelaysAndBridges(@Param("start") String start, @Param("end") String end) {
        log.info("请求一次/servers/relaysAndBridge，参数为start:{},end:{}", start, end);
        List<RelaysAndBridges> relaysAndBridgesList = relaysAndBridgesService.listRelaysAndBridges(start, end);
        if (relaysAndBridgesList != null) {
            return Result.success(relaysAndBridgesList);
        } else {
            //TODO 启动获取数据函数
            return Result.error(CodeMsg.NULL_DATA);
        }
    }

//    @RequestMapping("/servers/fillRelaysAndBridge")
//    @ResponseBody
//    public Result<Integer> fillRelaysAndBridges() throws NoSuchAlgorithmException, KeyManagementException {
//        log.info("请求一次/servers/fillRelaysAndBridge");
//        int number = relaysAndBridgesService.fillRelaysAndBridges();
//        return Result.success(number);
//    }

    @RequestMapping("/servers/relaysAndFlagDefault")
    @ResponseBody
    public Result<List<List<String>>> getRelaysAndFlagDefault() {
        //默认当前三个月数据
        String end = DateTimeUtil.dateToStr(new Date());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -3);
        Date startDate = calendar.getTime();
        String start = DateTimeUtil.dateToStr(startDate);
        log.info("请求一次/servers/relaysAndFlagDefault，参数为start:{},end:{}", start, end);
        List<List<String>> list = relaysByRelayFlagService.listRelaysAndFlagDefault(start, end);
        return Result.success(list);

    }

    @RequestMapping("/servers/relaysAndFlag")
    @ResponseBody
    public Result<List<List<String>>> getRelaysAndFlag(@Param("start") String start, @Param("end") String end) {
        log.info("请求一次/servers/relaysAndFlag，参数为start:{},end:{}", start, end);
        List<List<String>> lists = relaysByRelayFlagService.listRelaysAndFlagDefault(start, end);
        if (lists.size() == 0) {
            return Result.error(CodeMsg.NULL_DATA);
        }
        return Result.success(lists);
    }

//    @RequestMapping("/servers/fillRelaysAndFlag")
//    @ResponseBody
//    public Result<Integer> fillRelaysAndFlag() throws NoSuchAlgorithmException, KeyManagementException {
//        log.info("请求一次/servers/fillRelaysAndFlag");
//        int number = relaysByRelayFlagService.fillRelaysAndFlag();
//        return Result.success(number);
//    }

    @RequestMapping("/servers/versionAndRelaysDefault")
    @ResponseBody
    public Result<List<List<String>>> getVersionAndRelaysDefault() {
        //默认当前三个月数据
        String end = DateTimeUtil.dateToStr(new Date());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -3);
        Date startDate = calendar.getTime();
        String start = DateTimeUtil.dateToStr(startDate);
        log.info("请求一次/servers/versionAndRelaysDefault，参数为start:{},end:{}", start, end);
        List<List<String>> list = relaysByTorVersionService.listVersionAndRelaysDefault(start, end);
        return Result.success(list);

    }

    @RequestMapping("/servers/versionAndRelays")
    @ResponseBody
    public Result<List<List<String>>> getVersionAndRelays(@Param("start") String start, @Param("end") String end) {
        log.info("请求一次/servers/versionAndRelays，参数为start:{},end:{}", start, end);
        List<List<String>> lists = relaysByTorVersionService.listVersionAndRelaysDefault(start, end);
        if (lists.size() == 0) {
            return Result.error(CodeMsg.NULL_DATA);
        }
        return Result.success(lists);
    }


//    @RequestMapping("/servers/fillVersionAndRelays")
//    @ResponseBody
//    public Result<Integer> fillVersionAndRelays() throws NoSuchAlgorithmException, KeyManagementException {
//        log.info("请求一次/servers/fillVersionAndRelays");
//        int number = relaysByTorVersionService.fillVersionAndRelays();
//        return Result.success(number);
//    }

    @RequestMapping("/servers/platformDefault")
    @ResponseBody
    public Result<List<RelaysByPlatform>> getPlatfromDefault() {
        //默认当前三个月数据
        String end = DateTimeUtil.dateToStr(new Date());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -3);
        Date startDate = calendar.getTime();
        String start = DateTimeUtil.dateToStr(startDate);
        log.info("请求一次/servers/platformDefault，参数为start:{},end:{}", start, end);
        List<RelaysByPlatform> list = relaysByPlatformService.listPlatform(start, end);
        return Result.success(list);

    }

    @RequestMapping("/servers/platform")
    @ResponseBody
    public Result<List<RelaysByPlatform>> getPlatfrom(@Param("start") String start, @Param("end") String end) {
        log.info("请求一次/servers/platform，参数为start:{},end:{}", start, end);
        List<RelaysByPlatform> platformList = relaysByPlatformService.listPlatform(start, end);
        if (platformList != null) {
            return Result.success(platformList);
        } else {
            //TODO 启动获取数据函数
            return Result.error(CodeMsg.NULL_DATA);
        }
    }

//    @RequestMapping("/servers/fillPlatform")
//    @ResponseBody
//    public Result<Integer> fillPlatform() throws NoSuchAlgorithmException, KeyManagementException {
//        log.info("请求一次/servers/fillPlatform，");
//        int number = relaysByPlatformService.fillPlatform();
//        return Result.success(number);
//    }

    @RequestMapping("/servers/relaysIpVersionDefault")
    @ResponseBody
    public Result<List<RelaysByIpVersion>> getRelaysIpVersionDefault() {
        //默认当前三个月数据
        String end = DateTimeUtil.dateToStr(new Date());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -3);
        Date startDate = calendar.getTime();
        String start = DateTimeUtil.dateToStr(startDate);
        log.info("请求一次/servers/relaysIpVersionDefault，参数为start:{},end:{}", start, end);
        List<RelaysByIpVersion> list = relaysByIpVersionService.listRelaysIpVersion(start, end);
        return Result.success(list);

    }

    @RequestMapping("/servers/relaysIpVersion")
    @ResponseBody
    public Result<List<RelaysByIpVersion>> getRelaysIpVersion(@Param("start") String start, @Param("end") String end) {
        log.info("请求一次/servers/relaysIpVersion，参数为start:{},end:{}", start, end);
        List<RelaysByIpVersion> relaysIpVersionList = relaysByIpVersionService.listRelaysIpVersion(start, end);
        if (relaysIpVersionList != null) {
            return Result.success(relaysIpVersionList);
        } else {
            //TODO 启动获取数据函数
            return Result.error(CodeMsg.NULL_DATA);
        }
    }

//    @RequestMapping("/servers/fillRelaysIpVersion")
//    @ResponseBody
//    public Result<Integer> fillRelaysIpVersion() throws NoSuchAlgorithmException, KeyManagementException {
//        log.info("请求一次/servers/fillRelaysIpVersion");
//        int number = relaysByIpVersionService.fillRelaysIpVersion();
//        return Result.success(number);
//    }

    @RequestMapping("/servers/bridgesIpVersionDefault")
    @ResponseBody
    public Result<List<BridgesByIpVersion>> getBridgesIpVersionDefault() {
        //默认当前三个月数据
        String end = DateTimeUtil.dateToStr(new Date());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -3);
        Date startDate = calendar.getTime();
        String start = DateTimeUtil.dateToStr(startDate);
        log.info("请求一次/servers/bridgesIpVersionDefault，参数为start:{},end:{}", start, end);
        List<BridgesByIpVersion> list = bridgesByIpVersionService.listBridgesIpVersion(start, end);
        return Result.success(list);

    }

    @RequestMapping("/servers/bridgesIpVersion")
    @ResponseBody
    public Result<List<BridgesByIpVersion>> getBridgesIpVersion(@Param("start") String start, @Param("end") String end) {
        log.info("请求一次/servers/bridgesIpVersion，参数为start:{},end:{}", start, end);
        List<BridgesByIpVersion> bridgesIpVersionList = bridgesByIpVersionService.listBridgesIpVersion(start, end);
        if (bridgesIpVersionList != null) {
            return Result.success(bridgesIpVersionList);
        } else {
            //TODO 启动获取数据函数
            return Result.error(CodeMsg.NULL_DATA);
        }
    }

//    @RequestMapping("/servers/fillBridgesIpVersion")
//    @ResponseBody
//    public Result<Integer> fillBridgesIpVersion() throws NoSuchAlgorithmException, KeyManagementException {
//        log.info("请求一次/servers/fillBridgesIpVersion");
//        int number = bridgesByIpVersionService.fillBridgesIpVersion();
//        return Result.success(number);
//    }

    @RequestMapping("/servers/totalConWDefault")
    @ResponseBody
    public Result<List<List<String>>> getTotalConWDefault() {
        //默认当前三个月数据
        String end = DateTimeUtil.dateToStr(new Date());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -3);
        Date startDate = calendar.getTime();
        String start = DateTimeUtil.dateToStr(startDate);
        log.info("请求一次/servers/totalConWDefault，参数为start:{},end:{}", start, end);
        List<List<String>> list = totalConsensusWeightsAcrossBwAuService.listTotalConWDefault(start, end);
        return Result.success(list);

    }

    @RequestMapping("/servers/totalConW")
    @ResponseBody
    public Result<List<List<String>>> getTotalConW(@Param("start") String start, @Param("end") String end) {
        log.info("请求一次/servers/totalConW，参数为start:{},end:{}", start, end);
        List<List<String>> lists = totalConsensusWeightsAcrossBwAuService.listTotalConWDefault(start, end);
        if (lists.size() == 0) {
            return Result.error(CodeMsg.NULL_DATA);
        }
        return Result.success(lists);
    }


//    @RequestMapping("/servers/fillTotalConW")
//    @ResponseBody
//    public Result<Integer>fillTotalConW() throws NoSuchAlgorithmException, KeyManagementException {
//        log.info("请求一次/servers/fillTotalConW");
//        int number = totalConsensusWeightsAcrossBwAuService.fillTotalCW();
//        return Result.success(number);
//    }
}
