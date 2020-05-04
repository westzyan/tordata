package com.zyan.tordata.controller;

import com.zyan.tordata.domain.TorPerf;
import com.zyan.tordata.result.Result;
import com.zyan.tordata.service.OnionPerfBuildTimesService;
import com.zyan.tordata.service.OnionPerfLatenciesService;
import com.zyan.tordata.service.OnionPerfThroughputService;
import com.zyan.tordata.service.TorPerfService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
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


    @RequestMapping("/perf/fillTorPerf")
    @ResponseBody
    public Result<Integer> fillTorPerf() throws NoSuchAlgorithmException, KeyManagementException {
        int number = torPerfService.fillTorPerf();
        return Result.success(number);
    }
    //http://localhost:8080/perf/search?filesize=5242880&server=public&start=2018-01-01&end=2020-04-24
    @GetMapping(value = "/perf/search")
    @ResponseBody
    public Result<List<TorPerf>> search(@RequestParam(name = "filesize") int filesize, @RequestParam(name = "server")String server,
                                        @RequestParam(name = "start")String start, @RequestParam(name = "end")String end) throws NoSuchAlgorithmException, KeyManagementException {
        List<TorPerf> list= torPerfService.listTorPerfByCondition(filesize, server, start, end);
        return Result.success(list);
    }

    @RequestMapping("/perf/fillBuildTimes")
    @ResponseBody
    public Result<Integer> fillBuildTimes() throws NoSuchAlgorithmException, KeyManagementException {
        int number = onionPerfBuildTimesService.fillBuildTimes();
        return Result.success(number);
    }

    @RequestMapping("/perf/fillLatencies")
    @ResponseBody
    public Result<Integer> fillLatencies() throws NoSuchAlgorithmException, KeyManagementException {
        int number = onionPerfLatenciesService.fillOnionPerfLatencies();
        return Result.success(number);
    }

    @RequestMapping("/perf/fillThroughput")
    @ResponseBody
    public Result<Integer> fillThroughput() throws NoSuchAlgorithmException, KeyManagementException {
        int number = onionPerfThroughputService.fillOnionPerfLatencies();
        return Result.success(number);
    }

}
