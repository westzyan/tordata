package com.zyan.tordata.service;

import com.zyan.tordata.dao.RelaysByRelayFlagDao;
import com.zyan.tordata.domain.AdvBwByIpVersion;
import com.zyan.tordata.domain.RelaysByRelayFlag;
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
public class RelaysByRelayFlagService {
    @Autowired
    RelaysByRelayFlagDao relaysByRelayFlagDao;

    public List<List<String>> listRelaysAndFlag(String start, String end) {
        return listRelaysAndFlagDefault(start,end);
    }

    public List<List<String>> listRelaysAndFlagDefault(String start, String end) {
        List<List<String>> result = new ArrayList<List<String>>();
        String preDate;
        List<RelaysByRelayFlag> list = relaysByRelayFlagDao.listRelaysAndFlag(start, end);
        if (list.size() > 0) {
            preDate = DateTimeUtil.dateToStr(list.get(0).getDate());
        } else {
            return result;
        }

        List<String> dateList = new ArrayList<String>();
        List<String> runningList = new ArrayList<String>();
        List<String> exitList = new ArrayList<String>();
        List<String> fastList = new ArrayList<String>();
        List<String> guardList = new ArrayList<String>();
        List<String> stableList = new ArrayList<String>();
        List<String> hsdirList = new ArrayList<String>();

        String running = "0";
        String exit = "0";
        String fast = "0";
        String guard = "0";
        String stable = "0";
        String hsdir = "0";

        for (RelaysByRelayFlag relaysByRelayFlag : list) {
            Date curDate = relaysByRelayFlag.getDate();
            String dateStr = DateTimeUtil.dateToStr(curDate);
            if (preDate.equals(dateStr)) {
                switch (relaysByRelayFlag.getFlag()) {
                    case "Running":
                        running = String.valueOf(relaysByRelayFlag.getRelays());
                        break;
                    case "Exit":
                        exit = String.valueOf(relaysByRelayFlag.getRelays());
                        break;
                    case "Fast":
                        fast = String.valueOf(relaysByRelayFlag.getRelays());
                        break;
                    case "Guard":
                        guard = String.valueOf(relaysByRelayFlag.getRelays());
                        break;
                    case "Stable":
                        stable = String.valueOf(relaysByRelayFlag.getRelays());
                        break;
                    case "HSDir":
                        hsdir = String.valueOf(relaysByRelayFlag.getRelays());
                        break;
                }
            } else {
                dateList.add(preDate);
                runningList.add(running);
                exitList.add(exit);
                fastList.add(fast);
                guardList.add(guard);
                stableList.add(stable);
                hsdirList.add(hsdir);


                running = "0";
                exit = "0";
                fast = "0";
                guard = "0";
                stable = "0";
                hsdir = "0";


                preDate = dateStr;

                switch (relaysByRelayFlag.getFlag()) {
                    case "Running":
                        running = String.valueOf(relaysByRelayFlag.getRelays());
                        break;
                    case "Exit":
                        exit = String.valueOf(relaysByRelayFlag.getRelays());
                        break;
                    case "Fast":
                        fast = String.valueOf(relaysByRelayFlag.getRelays());
                        break;
                    case "Guard":
                        guard = String.valueOf(relaysByRelayFlag.getRelays());
                        break;
                    case "Stable":
                        stable = String.valueOf(relaysByRelayFlag.getRelays());
                        break;
                    case "HSDir":
                        hsdir = String.valueOf(relaysByRelayFlag.getRelays());
                        break;
                }

            }
        }
        dateList.add(preDate);
        runningList.add(running);
        exitList.add(exit);
        fastList.add(fast);
        guardList.add(guard);
        stableList.add(stable);
        hsdirList.add(hsdir);

        result.add(dateList);
        result.add(runningList);
        result.add(exitList);
        result.add(fastList);
        result.add(guardList);
        result.add(stableList);
        result.add(hsdirList);

        return result;
    }

    /**
     * 填充后续的数据
     * 查询最新的日期，然后startTime为最新日期的后一天，endTime为当天
     */
    @Async("executor")
     @Scheduled(cron = "0 0/30 * * * ?  ")
    public void fillRelaysAndFlag() throws KeyManagementException, NoSuchAlgorithmException {
        Date lastDate = relaysByRelayFlagDao.getLastDate();
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
        //https://metrics.torproject.org/relayflags.csv?start=2020-02-02&end=2020-05-02&flag=Running&flag=Exit&flag=Fast&flag=Guard&flag=Stable
        String url = Const.RELAYS_BY_RELAY_FLAG + "?start=" + startDateString + "&end=" + endDateString + "&flag=Running&flag=Exit&flag=Fast&flag=Guard&flag=Stable";
        System.out.println(url);
        //下载剩余的csv数据
        List<String> list = DownloadUtil.downloadCSV(url);
        List<RelaysByRelayFlag> relaysByRelayFlagList = new ArrayList<RelaysByRelayFlag>();
        for (String s : list) {
            String[] fields = s.split(",");
            RelaysByRelayFlag relaysByRelayFlag = new RelaysByRelayFlag();
            relaysByRelayFlag.setDate(DateTimeUtil.strToDate(fields[0]));
            relaysByRelayFlag.setFlag(fields[1]);
            relaysByRelayFlag.setRelays(Integer.parseInt(fields[2]));

            relaysByRelayFlagList.add(relaysByRelayFlag);
        }
        if (list.size() == 0){
            log.info("未下载到数据");
            return;
        }
        //填充到数据库中
        int rows = 0;
        for (int i = 0; i < relaysByRelayFlagList.size() / 400 + 1; i++) {
            int end = (i + 1) * 400;
            if (end >= list.size()) {
                end = list.size();
            }
            List<RelaysByRelayFlag> sublist = relaysByRelayFlagList.subList(i * 400, end);
            if (sublist.size() == 0){
                break;
            }
            rows = rows + relaysByRelayFlagDao.insertRelaysAndFlag(sublist);
        }
        if (rows < 0){
            log.error("本次写入失败");
        }else {
            log.info("写入了{}条数据", rows);
        }
    }

}
