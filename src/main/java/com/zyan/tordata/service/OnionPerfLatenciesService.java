package com.zyan.tordata.service;

import com.zyan.tordata.dao.OnionPerfBuildTimesDao;
import com.zyan.tordata.dao.OnionPerfLatenciesDao;
import com.zyan.tordata.dao.TorPerfDao;
import com.zyan.tordata.domain.OnionPerfLatencies;
import com.zyan.tordata.domain.TorPerf;
import com.zyan.tordata.result.Const;
import com.zyan.tordata.util.DateTimeUtil;
import com.zyan.tordata.util.DownloadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Service
@Slf4j
public class OnionPerfLatenciesService {
    @Autowired
    private OnionPerfLatenciesDao onionPerfLatenciesDao;

    public List<OnionPerfLatencies> listOnionPerfLatenciesByCondition(String server, String start, String end){
        return onionPerfLatenciesDao.listOnionPerfLatenciesByCondition(server, start, end);
    }

    //TODO 需要设置定时任务
    public int fillOnionPerfLatencies() throws KeyManagementException, NoSuchAlgorithmException {
        Date lastDate = onionPerfLatenciesDao.getLastDate();
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
        String url = Const.ONION_PERF_LATENCIES + "?start=" + startDateString + "&end=" + endDateString;
        System.out.println(url);
        //下载剩余的csv数据
        List<String> list = DownloadUtil.downloadCSV(url);
        List<OnionPerfLatencies> usersList = new ArrayList<OnionPerfLatencies>();
        for (String s : list) {
            String[] fields = s.split(",");  //这里为了处理类似于"2017-07-12,51200,op-us,onion,,,"
            OnionPerfLatencies onionPerfLatencies = new OnionPerfLatencies();
            onionPerfLatencies.setDate(DateTimeUtil.strToDate(fields[0]));
            onionPerfLatencies.setSource(fields[1]);
            onionPerfLatencies.setServer(fields[2]);
            onionPerfLatencies.setLow(Integer.parseInt(fields[3]));
            onionPerfLatencies.setQ1(Integer.parseInt(fields[4]));
            onionPerfLatencies.setMd(Integer.parseInt(fields[5]));
            onionPerfLatencies.setQ3(Integer.parseInt(fields[6]));
            onionPerfLatencies.setHigh(Integer.parseInt(fields[7]));
            usersList.add(onionPerfLatencies);
        }
        System.out.println(usersList.size());
        //填充到数据库中, 为了加快写入速度，也为了避免堆溢出，每400条写入一次
        int rows = 0;
        for (int i = 0; i < usersList.size() / 400 + 1; i++) {
            int end = (i + 1) * 400;
            if (end >= list.size()) {
                end = list.size();
            }
            List<OnionPerfLatencies> sublist = usersList.subList(i * 400, end);
            if (sublist.size() == 0){
                break;
            }
            rows = rows + onionPerfLatenciesDao.insertPerfLatencies(sublist);
        }
        log.info("写入了{}条数据", rows);

        return rows;
    }

    public static void main(String[] args) {
        int b = Integer.parseInt("110",2);
        System.out.println(b);
    }
}
