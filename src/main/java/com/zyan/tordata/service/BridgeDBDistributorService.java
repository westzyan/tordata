package com.zyan.tordata.service;

import com.zyan.tordata.dao.BridgeDBDistributorDao;
import com.zyan.tordata.dao.BridgeDBTransportDao;
import com.zyan.tordata.domain.BridgeDBDistributor;
import com.zyan.tordata.domain.BridgeDBTransport;
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
public class BridgeDBDistributorService {
    @Autowired
    BridgeDBDistributorDao bridgeDBDistributorDao;

    public List<BridgeDBDistributor> listUserByStartAndEnd(String start, String end) {
        return bridgeDBDistributorDao.listUserByStartAndEnd(start, end);
    }

    public List<List<String>> listBridgeDBDistByDate(String start, String end) {
        List<List<String>> result = new ArrayList<List<String>>();
        String preDate;
        List<BridgeDBDistributor> list = bridgeDBDistributorDao.listUserByStartAndEnd(start, end);
        if (list.size() > 0) {
            preDate = DateTimeUtil.dateToStr(list.get(0).getDate());
        } else {
            return result;
        }

        List<String> dateList = new ArrayList<String>();
        List<String> emailList = new ArrayList<String>();
        List<String> httpsList = new ArrayList<String>();
        List<String> moatList = new ArrayList<String>();

        String email = "0";
        String https = "0";
        String moat = "0";

        for (BridgeDBDistributor bridgeDBDistributor : list) {
            Date curDate = bridgeDBDistributor.getDate();
            String dateStr = DateTimeUtil.dateToStr(curDate);
            if (preDate.equals(dateStr)) {
                switch (bridgeDBDistributor.getDistributor()) {
                    case "email":
                        email = String.valueOf(bridgeDBDistributor.getRequests());
                        break;
                    case "https":
                        https = String.valueOf(bridgeDBDistributor.getRequests());
                        break;
                    case "moat":
                        moat = String.valueOf(bridgeDBDistributor.getRequests());
                        break;
                }
            } else {
                dateList.add(preDate);
                emailList.add(email);
                httpsList.add(https);
                moatList.add(moat);

                email = "0";
                https = "0";
                moat = "0";

                preDate = dateStr;

                switch (bridgeDBDistributor.getDistributor()) {
                    case "email":
                        email = String.valueOf(bridgeDBDistributor.getRequests());
                        break;
                    case "https":
                        https = String.valueOf(bridgeDBDistributor.getRequests());
                        break;
                    case "moat":
                        moat = String.valueOf(bridgeDBDistributor.getRequests());
                        break;
                }

            }
        }
        dateList.add(preDate);
        emailList.add(email);
        httpsList.add(https);
        moatList.add(moat);

        result.add(dateList);
        result.add(emailList);
        result.add(httpsList);
        result.add(moatList);

        return result;
    }

    /**
     * 填充后续的数据
     * 查询最新的日期，然后startTime为最新日期的后一天，endTime为当天
     */
    @Async("executor")
    @Scheduled(cron = "0 0/30 * * * ?  ")
    public void fillBridgeDBDistributor() throws KeyManagementException, NoSuchAlgorithmException {
        Date lastDate = bridgeDBDistributorDao.getLastDate();
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
        String url = Const.BRIDGEDB_DISTRIBUTOR + "?start=" + startDateString + "&end=" + endDateString;
        System.out.println(url);
        //下载剩余的csv数据
        List<String> list = DownloadUtil.downloadCSV(url);
        List<BridgeDBDistributor> usersList = new ArrayList<BridgeDBDistributor>();
        for (String s : list) {
            String[] fields = s.split(",");
            BridgeDBDistributor bridgeDBDistributor = new BridgeDBDistributor();
            bridgeDBDistributor.setDate(DateTimeUtil.strToDate(fields[0]));
            bridgeDBDistributor.setDistributor(fields[1]);
            bridgeDBDistributor.setRequests(Integer.parseInt(fields[2]));
            usersList.add(bridgeDBDistributor);
        }
        if (usersList.size() == 0){
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
            List<BridgeDBDistributor> sublist = usersList.subList(i * 400, end);
            if (sublist.size() == 0){
                break;
            }
            rows = rows + bridgeDBDistributorDao.insertUsers(sublist);
        }
        if (rows < 0){
            log.error("本次写入失败");
        }else {
            log.info("写入了{}条数据", rows);
        }
    }


}
