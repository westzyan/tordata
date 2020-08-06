package com.zyan.tordata.service;

import com.zyan.tordata.dao.RelaysByPlatformDao;
import com.zyan.tordata.domain.RelaysByPlatform;
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
public class RelaysByPlatformService {
    @Autowired
    RelaysByPlatformDao relaysByPlatformDao;

    public List<RelaysByPlatform> listPlatform(String start, String end) {
        return relaysByPlatformDao.listPlatform(start,end);
    }


    /**
     * 填充后续的数据
     * 查询最新的日期，然后startTime为最新日期的后一天，endTime为当天
     *
     */
//    @Async("executor")
//    @Scheduled(cron = "0 0/2 * * * ? ")
    public void fillPlatform() throws KeyManagementException, NoSuchAlgorithmException {
        Date lastDate = relaysByPlatformDao.getLastDate();
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
        //https://metrics.torproject.org/platforms.csv?start=2020-02-02&end=2020-05-02
        String url = Const.RELAYS_BY_PLATFORM + "?start=" + startDateString + "&end=" + endDateString;
        System.out.println(url);
        //下载剩余的csv数据
        List<String> list = DownloadUtil.downloadCSV(url);
        List<RelaysByPlatform> relaysByPlatformList = new ArrayList<RelaysByPlatform>();
        for (String s : list) {
            String[] fields = s.split(",");
            RelaysByPlatform relaysByPlatform = new RelaysByPlatform();
            relaysByPlatform.setDate(DateTimeUtil.strToDate(fields[0]));
            relaysByPlatform.setBsd(Integer.parseInt(fields[1]));
            relaysByPlatform.setLinux(Integer.parseInt(fields[2]));
            relaysByPlatform.setMacos(Integer.parseInt(fields[3]));
            relaysByPlatform.setOther(Integer.parseInt(fields[4]));
            relaysByPlatform.setWindows(Integer.parseInt(fields[5]));

            relaysByPlatformList.add(relaysByPlatform);
        }
        if (list.size() == 0){
            log.info("未下载到数据");
            return;
        }
        //填充到数据库中
        int rows = 0;
        for (int i = 0; i < relaysByPlatformList.size() / 400 + 1; i++) {
            int end = (i + 1) * 400;
            if (end >= list.size()) {
                end = list.size();
            }
            List<RelaysByPlatform> sublist = relaysByPlatformList.subList(i * 400, end);
            if (sublist.size() == 0){
                break;
            }
            rows = rows + relaysByPlatformDao.insertPlatform(sublist);
        }
        if (rows < 0){
            log.error("本次写入失败");
        }else {
            log.info("写入了{}条数据", rows);
        }
    }
}
