package com.zyan.tordata.controller;

import com.zyan.tordata.domain.OnionPerfBuildTimes;
import com.zyan.tordata.domain.TorPerf;
import com.zyan.tordata.result.CodeMsg;
import com.zyan.tordata.result.Result;
import com.zyan.tordata.service.OnionPerfBuildTimesService;
import com.zyan.tordata.service.OnionPerfLatenciesService;
import com.zyan.tordata.service.OnionPerfThroughputService;
import com.zyan.tordata.service.TorPerfService;
import com.zyan.tordata.util.DateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
@Slf4j
public class TorPerfController {
    @Autowired
    private TorPerfService torPerfService;
    @Autowired
    private OnionPerfBuildTimesService onionPerfBuildTimesService;
    @Autowired
    private OnionPerfLatenciesService onionPerfLatenciesService;
    @Autowired
    private OnionPerfThroughputService onionPerfThroughputService;


    @RequestMapping("/perf/torperf_default")
    @ResponseBody
    public Result<List<List<String>>> getTorPerfDefault() {
        //默认当前三个月数据
        String end = DateTimeUtil.dateToStr(new Date());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -3);
        Date startDate = calendar.getTime();
        String start = DateTimeUtil.dateToStr(startDate);
        String server = "public";
        int filesize = 51200;
        log.info("请求一次/perf/torperf_default，参数为start:{},end:{},server:{},filesize:{}", start, end, server, filesize);
        List<List<String>> lists = torPerfService.listTorperf(filesize, server, start, end);
        if (lists.size() == 0){
            return Result.error(CodeMsg.NULL_DATA);
        }
        return Result.success(lists);
    }


    @RequestMapping("/perf/torperf")
    @ResponseBody
    public Result<List<List<String>>> getTorPerf(@Param("filesize") int filesize, @Param("server") String server, @Param("start") String start, @Param("end") String end) {

        log.info("请求一次/perf/torperf，参数为start:{},end:{},server:{},filesize:{}", start, end, server, filesize);
        List<List<String>> lists = torPerfService.listTorperf(filesize, server, start, end);
        if (lists.size() == 0){
            return Result.error(CodeMsg.NULL_DATA);
        }
        return Result.success(lists);
    }


    @RequestMapping("/perf/onion_buildtimes")
    @ResponseBody
    public Result<List<OnionPerfBuildTimes>> getTorPerfBuildTimes(@Param("start") String start, @Param("end") String end ,@Param("source") String source) {

        log.info("请求一次/perf/onion_buildtimes，参数为start:{},end:{},source:{}", start, end, source);
        List<OnionPerfBuildTimes> list = onionPerfBuildTimesService.listTorPerfByCondition(start, end, source);
        if (list.size() == 0){
            return Result.error(CodeMsg.NULL_DATA);
        }
        return Result.success(list);
    }

//    @RequestMapping("/perf/fillTorPerf")
//    @ResponseBody
//    public Result<Integer> fillTorPerf() throws NoSuchAlgorithmException, KeyManagementException {
//        int number = torPerfService.fillTorPerf();
//        return Result.success(number);
//    }
    //http://localhost:8080/perf/search?filesize=5242880&server=public&start=2018-01-01&end=2020-04-24
    @GetMapping(value = "/perf/search")
    @ResponseBody
    public Result<List<TorPerf>> search(@RequestParam(name = "filesize") int filesize, @RequestParam(name = "server")String server,
                                        @RequestParam(name = "start")String start, @RequestParam(name = "end")String end) throws NoSuchAlgorithmException, KeyManagementException {
        List<TorPerf> list= torPerfService.listTorPerfByCondition(filesize, server, start, end);
        return Result.success(list);
    }

//    @RequestMapping("/perf/fillBuildTimes")
//    @ResponseBody
//    public Result<Integer> fillBuildTimes() throws NoSuchAlgorithmException, KeyManagementException {
//        int number = onionPerfBuildTimesService.fillBuildTimes();
//        return Result.success(number);
//    }
//
//    @RequestMapping("/perf/fillLatencies")
//    @ResponseBody
//    public Result<Integer> fillLatencies() throws NoSuchAlgorithmException, KeyManagementException {
//        int number = onionPerfLatenciesService.fillOnionPerfLatencies();
//        return Result.success(number);
//    }

    @RequestMapping("/perf/fillThroughput")
    @ResponseBody
    public Result<Integer> fillThroughput() throws NoSuchAlgorithmException, KeyManagementException {
        int number = onionPerfThroughputService.fillOnionPerfLatencies();
        return Result.success(number);
    }

}
