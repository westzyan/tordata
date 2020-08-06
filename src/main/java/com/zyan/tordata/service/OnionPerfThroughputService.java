package com.zyan.tordata.service;

import com.zyan.tordata.dao.OnionPerfLatenciesDao;
import com.zyan.tordata.dao.OnionPerfThroughputDao;
import com.zyan.tordata.dao.TorPerfDao;
import com.zyan.tordata.domain.OnionPerfLatencies;
import com.zyan.tordata.domain.OnionPerfThroughput;
import com.zyan.tordata.domain.TorPerf;
import com.zyan.tordata.result.Const;
import com.zyan.tordata.util.DateTimeUtil;
import com.zyan.tordata.util.DownloadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Service
@Slf4j
public class OnionPerfThroughputService {
    @Autowired
    private OnionPerfThroughputDao onionPerfThroughputDao;

    public List<OnionPerfThroughput> listOnionPerfLatenciesByCondition(String server, String start, String end){
        return onionPerfThroughputDao.listOnionPerfLatenciesByCondition(server, start, end);
    }

    public int fillOnionPerfLatencies() throws KeyManagementException, NoSuchAlgorithmException {
        Date lastDate = onionPerfThroughputDao.getLastDate();
        System.out.println(DateTimeUtil.dateToStr(lastDate));
        if (lastDate.equals(new Date())) {
            return 0;
        }
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(lastDate);
        calendar.add(Calendar.DATE, 1); //把日期往后增加一天,整数  往后推,负数往前移动
        Date startDate = calendar.getTime(); //这个时间就是日期往后推一天的结果
        String startDateString = DateTimeUtil.dateToStr(startDate);
        String endDateString = DateTimeUtil.dateToStr(new Date());
        String url = Const.ONION_PERF_THROUGHPUT + "?start=" + startDateString + "&end=" + endDateString;
        System.out.println(url);
        //下载剩余的csv数据
        List<String> list = DownloadUtil.downloadCSV(url);
        List<OnionPerfThroughput> usersList = new ArrayList<OnionPerfThroughput>();
        for (String s : list) {
            String[] fields = s.split(",");  //这里为了处理类似于"2017-07-12,51200,op-us,onion,,,"
            OnionPerfThroughput onionPerfThroughput = new OnionPerfThroughput();
            onionPerfThroughput.setDate(DateTimeUtil.strToDate(fields[0]));
            onionPerfThroughput.setSource(fields[1]);
            onionPerfThroughput.setServer(fields[2]);
            onionPerfThroughput.setLow(Integer.parseInt(fields[3]));
            onionPerfThroughput.setQ1(Integer.parseInt(fields[4]));
            onionPerfThroughput.setMd(Integer.parseInt(fields[5]));
            onionPerfThroughput.setQ3(Integer.parseInt(fields[6]));
            onionPerfThroughput.setHigh(Integer.parseInt(fields[7]));
            usersList.add(onionPerfThroughput);
        }
        System.out.println(usersList.size());
        //填充到数据库中, 为了加快写入速度，也为了避免堆溢出，每400条写入一次
        int rows = 0;
        for (int i = 0; i < usersList.size() / 400 + 1; i++) {
            int end = (i + 1) * 400;
            if (end >= list.size()) {
                end = list.size();
            }
            List<OnionPerfThroughput> sublist = usersList.subList(i * 400, end);
            if (sublist.size() == 0){
                break;
            }
            rows = rows + onionPerfThroughputDao.insertPerfThroughput(sublist);
        }
        log.info("写入了{}条数据", rows);

        return rows;
    }


//    @Async("executor")
//    @Scheduled(cron = "0 0/2 * * * ? ")
    public void fillOnion() throws KeyManagementException, NoSuchAlgorithmException {
        Date lastDate = onionPerfThroughputDao.getLastDate();
        String lastDateStr = DateTimeUtil.dateToStr(lastDate);
        log.info("last date:{}",lastDateStr);
        String newDate = DateTimeUtil.dateToStr(new Date());
        if (lastDateStr.equals(newDate)) {
            log.info("new date:{}",newDate);
            return;
        }
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(lastDate);
        calendar.add(Calendar.DATE, 1); //把日期往后增加一天,整数  往后推,负数往前移动
        Date startDate = calendar.getTime(); //这个时间就是日期往后推一天的结果
        String startDateString = DateTimeUtil.dateToStr(startDate);
        String endDateString = DateTimeUtil.dateToStr(new Date());
        String url = Const.ONION_PERF_THROUGHPUT + "?start=" + startDateString + "&end=" + endDateString;
        System.out.println(url);
        //下载剩余的csv数据
        List<String> list = DownloadUtil.downloadCSV(url);
        List<OnionPerfThroughput> usersList = new ArrayList<OnionPerfThroughput>();
        for (String s : list) {
            String[] fields = s.split(",");  //这里为了处理类似于"2017-07-12,51200,op-us,onion,,,"
            OnionPerfThroughput onionPerfThroughput = new OnionPerfThroughput();
            onionPerfThroughput.setDate(DateTimeUtil.strToDate(fields[0]));
            onionPerfThroughput.setSource(fields[1]);
            onionPerfThroughput.setServer(fields[2]);
            onionPerfThroughput.setLow(Integer.parseInt(fields[3]));
            onionPerfThroughput.setQ1(Integer.parseInt(fields[4]));
            onionPerfThroughput.setMd(Integer.parseInt(fields[5]));
            onionPerfThroughput.setQ3(Integer.parseInt(fields[6]));
            onionPerfThroughput.setHigh(Integer.parseInt(fields[7]));
            usersList.add(onionPerfThroughput);
        }
        if (list.size() == 0){
            log.info("未下载到数据");
            return;
        }
        //填充到数据库中, 为了加快写入速度，也为了避免堆溢出，每400条写入一次
        int rows = 0;
        for (int i = 0; i < usersList.size() / 400 + 1; i++) {
            int end = (i + 1) * 400;
            if (end >= list.size()) {
                end = list.size();
            }
            List<OnionPerfThroughput> sublist = usersList.subList(i * 400, end);
            if (sublist.size() == 0){
                break;
            }
            rows = rows + onionPerfThroughputDao.insertPerfThroughput(sublist);
        }
        if (rows < 0){
            log.error("本次写入失败");
        }else {
            log.info("写入了{}条数据", rows);
        }
    }

}
