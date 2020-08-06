package com.zyan.tordata.service;

import com.zyan.tordata.dao.AdvAndConBwByRelayFlagsDao;
import com.zyan.tordata.domain.AdvAndConBwByRelayFlags;
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
public class AdvAndConBwByRelayFlagsService {
    @Autowired
    AdvAndConBwByRelayFlagsDao advAndConBwByRelayFlagsDao;

    public List<AdvAndConBwByRelayFlags> listAdvAndConBw(String start, String end) {
        return advAndConBwByRelayFlagsDao.listAdvAndConBw(start, end);
    }

    public List<List<List<String>>> listAdvAndConBwDefault(String start, String end) {
        List<List<List<String>>> result = new ArrayList<List<List<String>>>();
        String preDate;
        List<AdvAndConBwByRelayFlags> list = advAndConBwByRelayFlagsDao.listAdvAndConBw(start, end);
        if (list.size() > 0) {
            preDate = DateTimeUtil.dateToStr(list.get(0).getDate());
        } else {
            return result;
        }

        List<List<String>> dateAllList = new ArrayList<List<String>>();
        List<List<String>> exitAndGuardAllList = new ArrayList<List<String>>();
        List<List<String>> exitAllList = new ArrayList<List<String>>();
        List<List<String>> guardAllList = new ArrayList<List<String>>();
        List<List<String>> nExitAndGuardAllList = new ArrayList<List<String>>();

        List<String> dateList = new ArrayList<String>();

        List<String> exitAndGuardAdvList = new ArrayList<String>();
        List<String> exitAndGuardConList = new ArrayList<String>();

        List<String> exitAdvList = new ArrayList<String>();
        List<String> exitConList = new ArrayList<String>();

        List<String> guardAdvList = new ArrayList<String>();
        List<String> guardConList = new ArrayList<String>();

        List<String> nExitAndGuardAdvList = new ArrayList<String>();
        List<String> nExitAndGuardConList = new ArrayList<String>();


        String exitAndGuardAdv = "0";
        String exitAndGuardCon = "0";

        String exitAdv = "0";
        String exitCon = "0";

        String guardAdv = "0";
        String guardCon = "0";

        String nExitAndGuardAdv = "0";
        String nExitAndGuardCon = "0";


        for (AdvAndConBwByRelayFlags advAndConBwByRelayFlags : list) {
            Date curDate = advAndConBwByRelayFlags.getDate();
            String dateStr = DateTimeUtil.dateToStr(curDate);
            if (preDate.equals(dateStr)) {
                if ("TRUE".equals(advAndConBwByRelayFlags.getHaveExitFlag()) && "TRUE".equals(advAndConBwByRelayFlags.getHaveGuardFlag())) {
                    exitAndGuardAdv = advAndConBwByRelayFlags.getAdvbw();
                    exitAndGuardCon = advAndConBwByRelayFlags.getBwhist();
                } else if ("FALSE".equals(advAndConBwByRelayFlags.getHaveExitFlag()) && "FALSE".equals(advAndConBwByRelayFlags.getHaveGuardFlag())) {
                    nExitAndGuardAdv = advAndConBwByRelayFlags.getAdvbw();
                    nExitAndGuardCon = advAndConBwByRelayFlags.getBwhist();
                } else if ("FALSE".equals(advAndConBwByRelayFlags.getHaveExitFlag()) && "TRUE".equals(advAndConBwByRelayFlags.getHaveGuardFlag())) {
                    guardAdv = advAndConBwByRelayFlags.getAdvbw();
                    guardCon = advAndConBwByRelayFlags.getBwhist();
                } else if ("TRUE".equals(advAndConBwByRelayFlags.getHaveExitFlag()) && "FALSE".equals(advAndConBwByRelayFlags.getHaveGuardFlag())) {
                    exitAdv = advAndConBwByRelayFlags.getAdvbw();
                    exitCon = advAndConBwByRelayFlags.getBwhist();
                }
            } else {
                dateList.add(preDate);
                exitAndGuardAdvList.add(exitAndGuardAdv);
                exitAndGuardConList.add(exitAndGuardCon);

                nExitAndGuardAdvList.add(nExitAndGuardAdv);
                nExitAndGuardConList.add(nExitAndGuardCon);

                exitAdvList.add(exitAdv);
                exitConList.add(exitCon);

                guardAdvList.add(guardAdv);
                guardConList.add(guardCon);


                exitAndGuardAdv = "0";
                exitAndGuardCon = "0";

                exitAdv = "0";
                exitCon = "0";

                guardAdv = "0";
                guardCon = "0";

                nExitAndGuardAdv = "0";
                nExitAndGuardCon = "0";


                preDate = dateStr;

                if ("TRUE".equals(advAndConBwByRelayFlags.getHaveExitFlag()) && "TRUE".equals(advAndConBwByRelayFlags.getHaveGuardFlag())) {
                    exitAndGuardAdv = advAndConBwByRelayFlags.getAdvbw();
                    exitAndGuardCon = advAndConBwByRelayFlags.getBwhist();
                } else if ("FALSE".equals(advAndConBwByRelayFlags.getHaveExitFlag()) && "FALSE".equals(advAndConBwByRelayFlags.getHaveGuardFlag())) {
                    nExitAndGuardAdv = advAndConBwByRelayFlags.getAdvbw();
                    nExitAndGuardCon = advAndConBwByRelayFlags.getBwhist();
                } else if ("FALSE".equals(advAndConBwByRelayFlags.getHaveExitFlag()) && "TRUE".equals(advAndConBwByRelayFlags.getHaveGuardFlag())) {
                    guardAdv = advAndConBwByRelayFlags.getAdvbw();
                    guardCon = advAndConBwByRelayFlags.getBwhist();
                } else if ("TRUE".equals(advAndConBwByRelayFlags.getHaveExitFlag()) && "FALSE".equals(advAndConBwByRelayFlags.getHaveGuardFlag())) {
                    exitAdv = advAndConBwByRelayFlags.getAdvbw();
                    exitCon = advAndConBwByRelayFlags.getBwhist();
                }

            }
        }
        dateList.add(preDate);
        dateAllList.add(dateList);
        exitAndGuardAdvList.add(exitAndGuardAdv);
        exitAndGuardConList.add(exitAndGuardCon);

        nExitAndGuardAdvList.add(nExitAndGuardAdv);
        nExitAndGuardConList.add(nExitAndGuardCon);

        exitAdvList.add(exitAdv);
        exitConList.add(exitCon);

        guardAdvList.add(guardAdv);
        guardConList.add(guardCon);

        exitAndGuardAllList.add(exitAndGuardAdvList);
        exitAndGuardAllList.add(exitAndGuardConList);

        nExitAndGuardAllList.add(nExitAndGuardAdvList);
        nExitAndGuardAllList.add(nExitAndGuardConList);

        exitAllList.add(exitAdvList);
        exitAllList.add(exitConList);

        guardAllList.add(guardAdvList);
        guardAllList.add(guardConList);

        result.add(dateAllList);
        result.add(exitAndGuardAllList);
        result.add(nExitAndGuardAllList);
        result.add(exitAllList);
        result.add(guardAllList);

        return result;
    }


    /**
     * 填充后续的数据
     * 查询最新的日期，然后startTime为最新日期的后一天，endTime为当天
     */
    @Async("executor")
    @Scheduled(cron = "0 0/30 * * * ?  ")
    public void fillAAndCBW() throws KeyManagementException, NoSuchAlgorithmException {
        Date lastDate = advAndConBwByRelayFlagsDao.getLastDate();
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
        //https://metrics.torproject.org/bandwidth-flags.csv?start=2020-02-03&end=2020-05-03
        String url = Const.ADV_AND_CON_BANDWIDTH_BY_RELAYS_FLAG + "?start=" + startDateString + "&end=" + endDateString;
        System.out.println(url);
        //下载剩余的csv数据
        List<String> list = DownloadUtil.downloadCSV(url);
        List<AdvAndConBwByRelayFlags> advAndConBwByRelayFlagsList = new ArrayList<AdvAndConBwByRelayFlags>();
        for (String s : list) {
            String[] fields = s.split(",",5);
            AdvAndConBwByRelayFlags advAndConBWByRelayFlags = new AdvAndConBwByRelayFlags();
            advAndConBWByRelayFlags.setDate(DateTimeUtil.strToDate(fields[0]));
            advAndConBWByRelayFlags.setHaveGuardFlag(fields[1]);
            advAndConBWByRelayFlags.setHaveExitFlag(fields[2]);
            if (fields[3].equals("")){
                advAndConBWByRelayFlags.setAdvbw("");
            }else{
                advAndConBWByRelayFlags.setAdvbw(fields[3]);
            }
            if (fields[4].equals("")){
                advAndConBWByRelayFlags.setBwhist("");
            }else{
                advAndConBWByRelayFlags.setBwhist(fields[4]);
            }

            advAndConBwByRelayFlagsList.add(advAndConBWByRelayFlags);
        }
        if (list.size() == 0){
            log.info("未下载到数据");
            return;
        }
        //填充到数据库中
        int rows = 0;
        for (int i = 0; i < advAndConBwByRelayFlagsList.size() / 400 + 1; i++) {
            int end = (i + 1) * 400;
            if (end >= list.size()) {
                end = list.size();
            }
            List<AdvAndConBwByRelayFlags> sublist = advAndConBwByRelayFlagsList.subList(i * 400, end);
            if (sublist.size() == 0) {
                break;
            }
            rows = rows + advAndConBwByRelayFlagsDao.insertAdvAndConBw(sublist);
        }
        if (rows < 0){
            log.error("本次写入失败");
        }else {
            log.info("写入了{}条数据", rows);
        }
    }


}
