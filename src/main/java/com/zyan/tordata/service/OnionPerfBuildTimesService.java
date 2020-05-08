package com.zyan.tordata.service;

import com.zyan.tordata.dao.OnionPerfBuildTimesDao;
import com.zyan.tordata.dao.TorPerfDao;
import com.zyan.tordata.domain.OnionPerfBuildTimes;
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
public class OnionPerfBuildTimesService {
    @Autowired
    private OnionPerfBuildTimesDao onionPerfBuildTimesDao;

    public List<OnionPerfBuildTimes> listTorPerfByCondition(String start, String end, String source){
        return onionPerfBuildTimesDao.listOnionPerfLatenciesByCondition(start, end, source);
    }



    //TODO 需要设置定时任务
    public int fillBuildTimes() throws KeyManagementException, NoSuchAlgorithmException {
        Date lastDate = onionPerfBuildTimesDao.getLastDate();
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
        String url = Const.ONION_PERF_BUILDTIMES + "?start=" + startDateString + "&end=" + endDateString;
        System.out.println(url);
        //下载剩余的csv数据
        List<String> list = DownloadUtil.downloadCSV(url);
        List<OnionPerfBuildTimes> usersList = new ArrayList<OnionPerfBuildTimes>();
        for (String s : list) {
            String[] fields = s.split(",");
            OnionPerfBuildTimes onionPerfBuildTimes = new OnionPerfBuildTimes();
            onionPerfBuildTimes.setDate(DateTimeUtil.strToDate(fields[0]));
            onionPerfBuildTimes.setSource(fields[1]);
            onionPerfBuildTimes.setPosition(Integer.parseInt(fields[2]));
            onionPerfBuildTimes.setQ1(Integer.parseInt(fields[3]));
            onionPerfBuildTimes.setMd(Integer.parseInt(fields[4]));
            onionPerfBuildTimes.setQ3(Integer.parseInt(fields[5]));
            usersList.add(onionPerfBuildTimes);
        }
        System.out.println(usersList.size());
        //填充到数据库中, 为了加快写入速度，也为了避免堆溢出，每400条写入一次
        int rows = 0;
        for (int i = 0; i < usersList.size() / 400 + 1; i++) {
            int end = (i + 1) * 400;
            if (end >= list.size()) {
                end = list.size();
            }
            List<OnionPerfBuildTimes> sublist = usersList.subList(i * 400, end);
            if (sublist.size() == 0){
                break;
            }
            rows = rows + onionPerfBuildTimesDao.insertPerfBuildTimes(sublist);
        }
        log.info("写入了{}条数据", rows);

        return rows;
    }

    public static void main(String[] args) {
        String s = "2017-07-12,51200,op-us,onion,,,";
        String[] strings = s.split(",",7);
        System.out.println(strings.length);
        for (String string : strings) {
            System.out.println(string);
        }
    }
}
