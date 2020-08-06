package com.zyan.tordata.service;

import com.zyan.tordata.dao.FracOfConnDao;
import com.zyan.tordata.domain.AdvBwByIpVersion;
import com.zyan.tordata.domain.FracOfConn;
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
public class FracOfConnService {
    @Autowired
    FracOfConnDao fracOfConnDao;

    public List<FracOfConn> listFracOfConn(String start, String end) {
        return fracOfConnDao.listFracOfConn(start,end);
    }

    public List<List<String>> listFracOfConnDefault(String start, String end) {
        List<List<String>> result = new ArrayList<List<String>>();
        String preDate;
        List<FracOfConn> list = fracOfConnDao.listFracOfConn(start, end);
        if (list.size() > 0) {
            preDate = DateTimeUtil.dateToStr(list.get(0).getDate());
        } else {
            return result;
        }

        List<String> dateList = new ArrayList<String>();
        List<String> bothList = new ArrayList<String>();
        List<String> writeList = new ArrayList<String>();
        List<String> readList = new ArrayList<String>();



        String both1 = "0";
        String both2 = "0";
        String both3 = "0";
        String write1 = "0";
        String write2 = "0";
        String write3 = "0";
        String read1 = "0";
        String read2 = "0";
        String read3 = "0";


        for (FracOfConn fracOfConn : list) {
            Date curDate = fracOfConn.getDate();
            String dateStr = DateTimeUtil.dateToStr(curDate);
            if (preDate.equals(dateStr)) {
                switch (fracOfConn.getDirection()) {
                    case "both":
                        both1 = String.valueOf(fracOfConn.getQ1());
                        both2 = String.valueOf(fracOfConn.getMd());
                        both3 = String.valueOf(fracOfConn.getQ3());
                        break;
//                    case "write":
//                        both = String.valueOf(fracOfConn.getRelays());
//                        break;
//                    case "read":
//                        write = String.valueOf(fracOfConn.getRelays());
//                        break;

                }
            } else {
                dateList.add(preDate);
                bothList.add(both1);
                bothList.add(both2);
                bothList.add(both3);
//                writeList.add(both);
//                readList.add(write);



                 both1 = "0";
                 both2 = "0";
                 both3 = "0";
                 write1 = "0";
                 write2 = "0";
                 write3 = "0";
                 read1 = "0";
                 read2 = "0";
                 read3 = "0";


                preDate = dateStr;

                switch (fracOfConn.getDirection()) {
                    case "both":
                        both1 = String.valueOf(fracOfConn.getQ1());
                        both2 = String.valueOf(fracOfConn.getMd());
                        both3 = String.valueOf(fracOfConn.getQ3());
                        break;
//                    case "write":
//                        both = String.valueOf(fracOfConn.getRelays());
//                        break;
//                    case "read":
//                        write = String.valueOf(fracOfConn.getRelays());
//                        break;

                }

            }
        }
        dateList.add(preDate);
        bothList.add(both1);
        bothList.add(both2);
        bothList.add(both3);
//        writeList.add(both);
//        readList.add(write);


        result.add(dateList);
        result.add(bothList);
//        result.add(writeList);
//        result.add(readList);
//        result.add(guardList);
//        result.add(stableList);
//        result.add(hsdirList);

        return result;
    }
    /**
     * 填充后续的数据
     * 查询最新的日期，然后startTime为最新日期的后一天，endTime为当天
     */
    @Async("executor")
    @Scheduled(cron = "0 0/30 * * * ?  ")
    public void fillFracOfConn() throws KeyManagementException, NoSuchAlgorithmException {
        Date lastDate = fracOfConnDao.getLastDate();
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
        //https://metrics.torproject.org/connbidirect.csv?start=2020-02-05&end=2020-05-05
        String url = Const.FRAC_OF_CONN + "?start=" + startDateString + "&end=" + endDateString;
        System.out.println(url);
        //下载剩余的csv数据
        List<String> list = DownloadUtil.downloadCSV(url);
        List<FracOfConn> fracOfConnList = new ArrayList<FracOfConn>();
        for (String s : list) {
            String[] fields = s.split(",");
            FracOfConn fracOfConn = new FracOfConn();
            fracOfConn.setDate(DateTimeUtil.strToDate(fields[0]));
            fracOfConn.setDirection(fields[1]);
            fracOfConn.setQ1(fields[2]);
            fracOfConn.setMd(fields[3]);
            fracOfConn.setQ3(fields[4]);

            fracOfConnList.add(fracOfConn);
        }
        if (list.size() == 0){
            log.info("未下载到数据");
            return;
        }
        //填充到数据库中
        int rows = 0;
        for (int i = 0; i < fracOfConnList.size() / 400 + 1; i++) {
            int end = (i + 1) * 400;
            if (end >= list.size()) {
                end = list.size();
            }
            List<FracOfConn> sublist = fracOfConnList.subList(i * 400, end);
            if (sublist.size() == 0){
                break;
            }
            rows = rows + fracOfConnDao.insertFracOfConn(sublist);
        }
        if (rows < 0){
            log.error("本次写入失败");
        }else {
            log.info("写入了{}条数据", rows);
        }
    }


}
