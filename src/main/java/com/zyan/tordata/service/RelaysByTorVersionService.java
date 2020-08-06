package com.zyan.tordata.service;

import com.zyan.tordata.dao.RelaysByTorVersionDao;
import com.zyan.tordata.domain.RelaysByTorVersion;
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
public class RelaysByTorVersionService {
    @Autowired
    RelaysByTorVersionDao relaysByTorVersionDao;

    public List<RelaysByTorVersion> listVersionAndRelays(String start, String end) {
        return relaysByTorVersionDao.listVersionAndRelays(start,end);
    }

    public List<List<String>> listVersionAndRelaysDefault(String start, String end) {
        List<List<String>> result = new ArrayList<List<String>>();
        String preDate;
        List<RelaysByTorVersion> list = relaysByTorVersionDao.listVersionAndRelays(start, end);
        if (list.size() > 0) {
            preDate = DateTimeUtil.dateToStr(list.get(0).getDate());
        } else {
            return result;
        }

        List<String> dateList = new ArrayList<String>();
        List<String> v1List = new ArrayList<String>();
        List<String> v2List = new ArrayList<String>();
        List<String> v3List = new ArrayList<String>();
        List<String> v4List = new ArrayList<String>();
        List<String> v5List = new ArrayList<String>();
        List<String> v6List = new ArrayList<String>();
        List<String> v7List = new ArrayList<String>();

        String v1 = "0";
        String v2 = "0";
        String v3 = "0";
        String v4 = "0";
        String v5 = "0";
        String v6 = "0";
        String v7 = "0";

        for (RelaysByTorVersion relaysByTorVersion : list) {
            Date curDate = relaysByTorVersion.getDate();
            String dateStr = DateTimeUtil.dateToStr(curDate);
            if (preDate.equals(dateStr)) {
                switch (relaysByTorVersion.getVersion()) {
                    case "0.2.9":
                        v1 = String.valueOf(relaysByTorVersion.getRelays());
                        break;
                    case "0.3.5":
                        v2 = String.valueOf(relaysByTorVersion.getRelays());
                        break;
                    case "0.4.0":
                        v3 = String.valueOf(relaysByTorVersion.getRelays());
                        break;
                    case "0.4.1":
                        v4 = String.valueOf(relaysByTorVersion.getRelays());
                        break;
                    case "0.4.2":
                        v5 = String.valueOf(relaysByTorVersion.getRelays());
                        break;
                    case "0.4.3":
                        v6 = String.valueOf(relaysByTorVersion.getRelays());
                        break;
                    case "Other":
                        v6 = String.valueOf(relaysByTorVersion.getRelays());
                        break;
                }
            } else {
                dateList.add(preDate);
                v1List.add(v1);
                v2List.add(v2);
                v3List.add(v3);
                v4List.add(v4);
                v5List.add(v5);
                v6List.add(v6);
                v7List.add(v7);


                v1 = "0";
                v2 = "0";
                v3 = "0";
                v4 = "0";
                v5 = "0";
                v6 = "0";
                v7 = "0";


                preDate = dateStr;

                switch (relaysByTorVersion.getVersion()) {
                    case "0.2.9":
                        v1 = String.valueOf(relaysByTorVersion.getRelays());
                        break;
                    case "0.3.5":
                        v2 = String.valueOf(relaysByTorVersion.getRelays());
                        break;
                    case "0.4.0":
                        v3 = String.valueOf(relaysByTorVersion.getRelays());
                        break;
                    case "0.4.1":
                        v4 = String.valueOf(relaysByTorVersion.getRelays());
                        break;
                    case "0.4.2":
                        v5 = String.valueOf(relaysByTorVersion.getRelays());
                        break;
                    case "0.4.3":
                        v6 = String.valueOf(relaysByTorVersion.getRelays());
                        break;
                    case "Other":
                        v6 = String.valueOf(relaysByTorVersion.getRelays());
                        break;
                }

            }
        }
        dateList.add(preDate);
        v1List.add(v1);
        v2List.add(v2);
        v3List.add(v3);
        v4List.add(v4);
        v5List.add(v5);
        v6List.add(v6);
        v7List.add(v7);

        result.add(dateList);
        result.add(v1List);
        result.add(v2List);
        result.add(v3List);
        result.add(v4List);
        result.add(v5List);
        result.add(v6List);
        result.add(v7List);

        return result;
    }
    /**
     * 填充后续的数据
     * 查询最新的日期，然后startTime为最新日期的后一天，endTime为当天
     */
    @Async("executor")
     @Scheduled(cron = "0 0/30 * * * ?  ")
    public void fillVersionAndRelays() throws KeyManagementException, NoSuchAlgorithmException {
        Date lastDate = relaysByTorVersionDao.getLastDate();
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
        //https://metrics.torproject.org/versions.csv?start=2020-02-02&end=2020-05-02
        String url = Const.RELAYS_BY_TOR_VERSION + "?start=" + startDateString + "&end=" + endDateString;
        System.out.println(url);
        //下载剩余的csv数据
        List<String> list = DownloadUtil.downloadCSV(url);
        List<RelaysByTorVersion> relaysByTorVersionList = new ArrayList<RelaysByTorVersion>();
        for (String s : list) {
            String[] fields = s.split(",");
            RelaysByTorVersion relaysByTorVersion = new RelaysByTorVersion();
            relaysByTorVersion.setDate(DateTimeUtil.strToDate(fields[0]));
            relaysByTorVersion.setVersion(fields[1]);
            relaysByTorVersion.setRelays(Integer.parseInt(fields[2]));

            relaysByTorVersionList.add(relaysByTorVersion);
        }
        if (list.size() == 0){
            log.info("未下载到数据");
            return;
        }
        //填充到数据库中
        int rows = 0;
        for (int i = 0; i < relaysByTorVersionList.size() / 400 + 1; i++) {
            int end = (i + 1) * 400;
            if (end >= list.size()) {
                end = list.size();
            }
            List<RelaysByTorVersion> sublist = relaysByTorVersionList.subList(i * 400, end);
            if (sublist.size() == 0){
                break;
            }
            rows = rows + relaysByTorVersionDao.insertVersionAndRelays(sublist);
        }
        if (rows < 0){
            log.error("本次写入失败");
        }else {
            log.info("写入了{}条数据", rows);
        }
    }


}
