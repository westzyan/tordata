package com.zyan.tordata.controller;


import com.zyan.tordata.result.CodeMsg;
import com.zyan.tordata.result.Result;
import com.zyan.tordata.service.OnionServiceTrafficService;
import com.zyan.tordata.service.OnionUniqueAddressService;
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

@Slf4j
@Controller
@RequestMapping(value = "/onion")
public class OnionController {
    @Autowired
    private OnionServiceTrafficService onionServiceTrafficService;
    @Autowired
    private OnionUniqueAddressService onionUniqueAddressService;


    @RequestMapping("/onion_service_default")
    @ResponseBody
    public Result<List<List<Object>>> getOnionServiceDefault() {
        //默认当前三个月数据
        String end = DateTimeUtil.dateToStr(new Date());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -3);
        Date startDate = calendar.getTime();
        String start = DateTimeUtil.dateToStr(startDate);
        log.info("请求一次/onion/onion_service_default，参数为start:{},end:{}", start, end);
        List<List<Object>> lists = onionUniqueAddressService.listOnionServiceData(start, end);
        if (lists.size() == 0){
            return Result.error(CodeMsg.NULL_DATA);
        }
        return Result.success(lists);
    }


    @RequestMapping("/onion_service")
    @ResponseBody
    public Result<List<List<Object>>> getOnionService(@Param("start") String start, @Param("end") String end) {

        log.info("请求一次/onion/onion_service，参数为start:{},end:{}", start, end);

        List<List<Object>> lists = onionUniqueAddressService.listOnionServiceData(start, end);
        if (lists.size() == 0){
            return Result.error(CodeMsg.NULL_DATA);
        }
        return Result.success(lists);
    }

//    @RequestMapping("/fillOnionServiceTraffic")
//    @ResponseBody
//    public Result<Integer> fillOnionServiceTraffic() throws NoSuchAlgorithmException, KeyManagementException {
//        int number = onionServiceTrafficService.fillOnionServiceTraffic();
//        return Result.success(number);
//    }
//
//    @RequestMapping("/fillOnionUniqueAddress")
//    @ResponseBody
//    public Result<Integer> fillOnionUniqueAddress() throws NoSuchAlgorithmException, KeyManagementException {
//        int number = onionUniqueAddressService.fillOnionUniqueAddress();
//        return Result.success(number);
//    }
}
