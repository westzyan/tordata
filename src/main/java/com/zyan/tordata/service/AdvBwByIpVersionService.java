package com.zyan.tordata.service;

import com.zyan.tordata.dao.AdvBwByIpVersionDao;
import com.zyan.tordata.domain.AdvAndConBwByRelayFlags;
import com.zyan.tordata.domain.AdvBwByIpVersion;
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
public class AdvBwByIpVersionService {
    @Autowired
    AdvBwByIpVersionDao advBwByIpVersionDao;

    public List<AdvBwByIpVersion> listAdvBWByIpVersion(String start, String end) {
        return advBwByIpVersionDao.listAdvBwByIpVersion(start,end);
    }

    /**
     * 填充后续的数据
     * 查询最新的日期，然后startTime为最新日期的后一天，endTime为当天
     */
    @Async("executor")
     @Scheduled(cron = "0 0/30 * * * ?  ")
    public void fillAdvBWByIpVersion() throws KeyManagementException, NoSuchAlgorithmException {
        Date lastDate = advBwByIpVersionDao.getLastDate();
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
        //https://metrics.torproject.org/advbw-ipv6.csv?start=2020-02-03&end=2020-05-03
        String url = Const.ADV_BW_BY_IP_VERSION + "?start=" + startDateString + "&end=" + endDateString;
        System.out.println(url);
        //下载剩余的csv数据
        List<String> list = DownloadUtil.downloadCSV(url);
        List<AdvBwByIpVersion> advBwByIpVersionList = new ArrayList<AdvBwByIpVersion>();
        for (String s : list) {
            String[] fields = s.split(",");
            AdvBwByIpVersion advBWByIpVersion = new AdvBwByIpVersion();
            advBWByIpVersion.setDate(DateTimeUtil.strToDate(fields[0]));
            advBWByIpVersion.setTotal(fields[1]);
            advBWByIpVersion.setTotalGuard(fields[2]);
            advBWByIpVersion.setTotalExit(fields[3]);
            advBWByIpVersion.setReachableGuard(fields[4]);
            advBWByIpVersion.setReachableExit(fields[5]);
            advBWByIpVersion.setExiting(fields[6]);

            advBwByIpVersionList.add(advBWByIpVersion);
        }
        if (list.size() == 0){
            log.info("未下载到数据");
            return;
        }
        //填充到数据库中
        int rows = 0;
        for (int i = 0; i < advBwByIpVersionList.size() / 400 + 1; i++) {
            int end = (i + 1) * 400;
            if (end >= list.size()) {
                end = list.size();
            }
            List<AdvBwByIpVersion> sublist = advBwByIpVersionList.subList(i * 400, end);
            if (sublist.size() == 0){
                break;
            }
            rows = rows + advBwByIpVersionDao.insertAdvBwByIpVersion(sublist);
        }
        if (rows < 0){
            log.error("本次写入失败");
        }else {
            log.info("写入了{}条数据", rows);
        }
    }
}
