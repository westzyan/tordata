package com.zyan.tordata.controller;


import com.alibaba.fastjson.JSON;
import com.zyan.tordata.domain.UserStatsBridgeCountry;
import com.zyan.tordata.domain.UserStatsBridgeVersion;
import com.zyan.tordata.domain.UserStatsRelayCountry;
import com.zyan.tordata.result.CodeMsg;
import com.zyan.tordata.result.Result;
import com.zyan.tordata.service.*;
import com.zyan.tordata.util.DateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Slf4j
@Controller
public class UsersController {

    @Autowired
    private UserStatsRelayCountryService userStatsRelayCountryService;
    @Autowired
    private UserStatsBridgeCountryService userStatsBridgeCountryService;
    @Autowired
    private UserStatsBridgeTransportService userStatsBridgeTransportService;
    @Autowired
    private UserStatsBridgeCombinedService userStatsBridgeCombinedService;
    @Autowired
    private UserStatsBridgeVersionService userStatsBridgeVersionService;
    @Autowired
    private BridgeDBDistributorService bridgeDBDistributorService;
    @Autowired
    private BridgeDBTransportService bridgeDBTransportService;
    @Autowired
    private OnionUniqueAddressService onionUniqueAddressService;

    @RequestMapping("/users/allUser")
    @ResponseBody
    public Result<String> getAllUserStatsRelayCountry() {
        List<UserStatsRelayCountry> userStatsRelayCountryList = userStatsRelayCountryService.listAllUser();
        if (userStatsRelayCountryList != null) {
            String userJson = JSON.toJSONStringWithDateFormat(userStatsRelayCountryList, "yyyy-MM-dd");
            return Result.success(userJson);
        } else {
            //TODO 启动获取数据函数

            return Result.error(CodeMsg.NULL_DATA);
        }
    }

    @RequestMapping("/users/relay_country_default")
    @ResponseBody
    public Result<List<UserStatsRelayCountry>> getAllUserStatsRelayCountryDefault() {
        //默认当前三个月所有国家数据
        String country = "";
        String end = DateTimeUtil.dateToStr(new Date());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -3);
        Date startDate = calendar.getTime();
        String start = DateTimeUtil.dateToStr(startDate);
        log.info("请求一次/users/relay_country_default，参数为country:{},start:{},end:{}", country, start, end);
        List<UserStatsRelayCountry> list = userStatsRelayCountryService.listUserByCountryAndDate(country, start, end);
        return Result.success(list);
    }

    @RequestMapping("/users/relay_country")
    @ResponseBody
    public Result<List<UserStatsRelayCountry>> getAllUserStatsRelayCountry(@Param("country") String country,
                                                                           @Param("start") String start,
                                                                           @Param("end") String end) {
        log.info("请求一次/users/relay_country，参数为country:{},start:{},end:{}", country, start, end);
        if (country.equals("all")) {
            country = "";
        }
        List<UserStatsRelayCountry> list = userStatsRelayCountryService.listUserByCountryAndDate(country, start, end);
        return Result.success(list);
    }

    @RequestMapping("/users/bridge_country_default")
    @ResponseBody
    public Result<List<UserStatsBridgeCountry>> getBridgeCountryDefault() {
        //默认当前三个月所有国家数据
        String country = "";
        String end = DateTimeUtil.dateToStr(new Date());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -3);
        Date startDate = calendar.getTime();
        String start = DateTimeUtil.dateToStr(startDate);
        log.info("请求一次/users/bridge_country_default，参数为country:{},start:{},end:{}", country, start, end);
        List<UserStatsBridgeCountry> list = userStatsBridgeCountryService.listUserByCountryAndDate(country, start, end);
        return Result.success(list);
    }

    @RequestMapping("/users/bridge_country")
    @ResponseBody
    public Result<List<UserStatsBridgeCountry>> getBridgeCountry(@Param("country") String country,
                                                                 @Param("start") String start,
                                                                 @Param("end") String end) {
        log.info("请求一次/users/bridge_country，参数为country:{},start:{},end:{},", country, start, end);
        if (country.equals("all")) {
            country = "";
        }
        List<UserStatsBridgeCountry> list = userStatsBridgeCountryService.listUserByCountryAndDate(country, start, end);
        return Result.success(list);
    }


    @RequestMapping("/users/bridge_transport_default")
    @ResponseBody
    public Result<List<List<String>>> getBridgeTransportDefault() {
        //默认当前三个月数据
        String end = DateTimeUtil.dateToStr(new Date());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -3);
        Date startDate = calendar.getTime();
        String start = DateTimeUtil.dateToStr(startDate);
        log.info("请求一次/users/bridge_transport_default，参数为start:{},end:{}", start, end);
        List<List<String>> list = userStatsBridgeTransportService.listTransportUser(start, end);
        return Result.success(list);
    }

    @RequestMapping("/users/bridge_transport")
    @ResponseBody
    public Result<List<List<String>>> getBridgeTransport(@Param("start") String start, @Param("end") String end) {
        log.info("请求一次/users/bridge_transport，参数为start:{},end:{},", start, end);
        List<List<String>> list = userStatsBridgeTransportService.listTransportUser(start, end);
        return Result.success(list);
    }

    @RequestMapping("/users/test")
    @ResponseBody
    public Result<Map<String, Integer>> getBridgeTransport() {
        log.info("请求一次/users/test");
        Map<String, Integer> map1 = new HashMap<String, Integer>();
        map1.put("fdjsal", 3);
        map1.put("33", 35);
        map1.put("444", 36);
        return Result.success(map1);
    }

    @RequestMapping("/users/bridge_combined_default")
    @ResponseBody
    public Result<Map<String, Integer>> getBridgeCombinedDefault() {
        String country = "??";  //代表所有国家的数据
        String date = DateTimeUtil.dateToStr(new Date());
//        String date = "2019-03-05";
        log.info("请求一次/users/bridge_combined_default，参数为date:{},country:{},", date, country);
        Map<String, Integer> map = userStatsBridgeCombinedService.mapCountryAndDate(date, country);
        if (map == null || map.size() == 0) {
            return Result.error(CodeMsg.NULL_DATA);
        }
        return Result.success(map);
    }

    @RequestMapping("/users/bridge_combined")
    @ResponseBody
    public Result<Map<String, Integer>> getBridgeCombined(@Param("start") String start, @Param("country") String country) {
        if (country.equals("all")){
            country = "??";
        }
        log.info("请求一次/users/bridge_combined，参数为start:{},country:{},", start, country);
        Map<String, Integer> map = userStatsBridgeCombinedService.mapCountryAndDate(start, country);
        if (map == null || map.size() == 0) {
            return Result.error(CodeMsg.NULL_DATA);
        }
        if (country.equals("??")){
            country = "全球";
        }
        return Result.success(start+country, map);
    }

    @RequestMapping("/users/bridge_version_default")
    @ResponseBody
    public Result<List<UserStatsBridgeVersion>> getBridgeVersionDefault(@Param("version") String version) {
        //默认当前三个月数据
        String end = DateTimeUtil.dateToStr(new Date());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -3);
        Date startDate = calendar.getTime();
        String start = DateTimeUtil.dateToStr(startDate);
        log.info("请求一次/users/bridge_version_default，参数为start:{},end:{},version:{}", start, end, version);
        if (version.equals("all")){
            version = "";
        }
        List<UserStatsBridgeVersion> list = userStatsBridgeVersionService.listUserByStartAndEndAndVersion(start, end, version);
        return Result.success(list);
    }

    @RequestMapping("/users/bridge_version")
    @ResponseBody
    public Result<List<UserStatsBridgeVersion>> getBridgeVersion(@Param("start") String start,@Param("end") String end,@Param("version") String version) {
        log.info("请求一次/users/bridge_version_default，参数为start:{},end:{},version:{}", start, end, version);
        if (version.equals("all")){
            version = "";
        }
        List<UserStatsBridgeVersion> list = userStatsBridgeVersionService.listUserByStartAndEndAndVersion(start, end, version);
        if (list.size() == 0){
            return Result.error(CodeMsg.NULL_DATA);
        }
        return Result.success(list);
    }

    @RequestMapping("/users/bridgeDB_Transport_default")
    @ResponseBody
    public Result<List<List<String>>> getBridgeDBTransport() {
        //默认当前三个月数据
        String end = DateTimeUtil.dateToStr(new Date());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -3);
        Date startDate = calendar.getTime();
        String start = DateTimeUtil.dateToStr(startDate);
        log.info("请求一次/users/bridge_version_default，参数为start:{},end:{}", start, end);

        List<List<String>> lists = bridgeDBTransportService.listBridgeDBPTByDate(start, end);
        if (lists.size() == 0){
            return Result.error(CodeMsg.NULL_DATA);
        }
        return Result.success(lists);
    }


    @RequestMapping("/users/bridgeDB_Transport")
    @ResponseBody
    public Result<List<List<String>>> getBridgeDBTransport(@Param("start") String start,@Param("end") String end) {

        log.info("请求一次/users/bridge_version，参数为start:{},end:{}", start, end);

        List<List<String>> lists = bridgeDBTransportService.listBridgeDBPTByDate(start, end);
        if (lists.size() == 0){
            return Result.error(CodeMsg.NULL_DATA);
        }
        return Result.success(lists);
    }

    @RequestMapping("/users/bridgeDB_distributor_default")
    @ResponseBody
    public Result<List<List<String>>> getBridgeDBDistributor() {
        //默认当前三个月数据
        String end = DateTimeUtil.dateToStr(new Date());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -3);
        Date startDate = calendar.getTime();
        String start = DateTimeUtil.dateToStr(startDate);
        log.info("请求一次/users/bridgeDB_distributor_default，参数为start:{},end:{}", start, end);

        List<List<String>> lists = bridgeDBDistributorService.listBridgeDBDistByDate(start, end);
        if (lists.size() == 0){
            return Result.error(CodeMsg.NULL_DATA);
        }
        return Result.success(lists);
    }


    @RequestMapping("/users/bridgeDB_distributor")
    @ResponseBody
    public Result<List<List<String>>> getBridgeDBDistributor(@Param("start") String start,@Param("end") String end) {

        log.info("请求一次/users/bridgeDB_distributor，参数为start:{},end:{}", start, end);

        List<List<String>> lists = bridgeDBDistributorService.listBridgeDBDistByDate(start, end);
        if (lists.size() == 0){
            return Result.error(CodeMsg.NULL_DATA);
        }
        return Result.success(lists);
    }


    @RequestMapping("/users/top_relay_default")
    @ResponseBody
    public Result<List<UserStatsRelayCountry>> topRelayDefault() {
        log.info("请求一次/users/top_relay_default");
        List<UserStatsRelayCountry> list = userStatsRelayCountryService.listRelayUserByDateAndNumberDescDefault();
        if (list.size() == 0){
            return Result.error(CodeMsg.NULL_DATA);
        }
        return Result.success(list);
    }

    @RequestMapping("/users/top_relay")
    @ResponseBody
    public Result<List<UserStatsRelayCountry>> topRelay(@Param("start") String start) {
        log.info("请求一次/users/top_relay,参数为：start:{}",start);
        List<UserStatsRelayCountry> list = userStatsRelayCountryService.listRelayUserByDateAndNumberDesc(start);
        if (list.size() == 0){
            return Result.error(CodeMsg.NULL_DATA);
        }
        return Result.success(list);
    }



    @RequestMapping("/users/top_bridge_default")
    @ResponseBody
    public Result<List<UserStatsBridgeCountry>> topBridgeDefault() {
        log.info("请求一次/users/top_bridge_default");
        List<UserStatsBridgeCountry> list = userStatsBridgeCountryService.listBridgeUserByDateAndNumberDescDefault();
        if (list.size() == 0){
            return Result.error(CodeMsg.NULL_DATA);
        }
        return Result.success(list);
    }

    @RequestMapping("/users/top_bridge")
    @ResponseBody
    public Result<List<UserStatsBridgeCountry>> topBridge(@Param("start") String start) {
        log.info("请求一次/users/top_bridge,参数为：start:{}",start);
        List<UserStatsBridgeCountry> list = userStatsBridgeCountryService.listBridgeUserByDateAndNumberDesc(start);
        if (list.size() == 0){
            return Result.error(CodeMsg.NULL_DATA);
        }
        return Result.success(list);
    }


//    @RequestMapping("/users/fillUserStatsRelay")
//    @ResponseBody
//    public Result<Integer> fillUserStatsRelay() throws NoSuchAlgorithmException, KeyManagementException {
//
//        int number = userStatsRelayCountryService.fillUserStatsRelay();
//        return Result.success(number);
//    }
//
//    @RequestMapping("/users/fillBridgeCountry")
//    @ResponseBody
//    public Result<Integer> fillBridgeCountry() throws NoSuchAlgorithmException, KeyManagementException {
//        int number = userStatsBridgeCountryService.fillUserStatsBridgeCountry();
//        return Result.success(number);
//    }

//    @RequestMapping("/users/fillBridgeTransport")
//    @ResponseBody
//    public Result<Integer> fillBridgeTransport() throws NoSuchAlgorithmException, KeyManagementException {
//        int number = userStatsBridgeTransportService.fillUserStatsBridgeTransport();
//        return Result.success(number);
//    }

//    @RequestMapping("/users/fillBridgeCombined")
//    @ResponseBody
//    public Result<Integer> fillBridgeCombined() throws NoSuchAlgorithmException, KeyManagementException {
//        int number = userStatsBridgeCombinedService.fillUserStatsBridgeCombined();
//        return Result.success(number);
//    }

//    @RequestMapping("/users/fillBridgeVersion")
//    @ResponseBody
//    public Result<Integer> fillBridgeVersion() throws NoSuchAlgorithmException, KeyManagementException {
//        int number = userStatsBridgeVersionService.fillUserStatsBridgeVersion();
//        return Result.success(number);
//    }

//    @RequestMapping("/users/fillBridgeDBDistributor")
//    @ResponseBody
//    public Result<Integer> fillBridgeDBDistributor() throws NoSuchAlgorithmException, KeyManagementException {
//        int number = bridgeDBDistributorService.fillBridgeDBDistributor();
//        return Result.success(number);
//    }
//
//    @RequestMapping("/users/fillBridgeDBTransport")
//    @ResponseBody
//    public Result<Integer> fillBridgeDBTransport() throws NoSuchAlgorithmException, KeyManagementException {
//        int number = bridgeDBTransportService.fillUserStatsBridgeVersion();
//        return Result.success(number);
//    }
}
