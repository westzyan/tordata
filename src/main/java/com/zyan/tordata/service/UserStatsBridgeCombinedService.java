package com.zyan.tordata.service;

import com.zyan.tordata.dao.UserStatsBridgeCombinedDao;
import com.zyan.tordata.dao.UserStatsBridgeTransportDao;
import com.zyan.tordata.domain.UserStatsBridgeCombined;
import com.zyan.tordata.domain.UserStatsBridgeTransport;
import com.zyan.tordata.result.Const;
import com.zyan.tordata.util.DateTimeUtil;
import com.zyan.tordata.util.DownloadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Service
@Slf4j
public class UserStatsBridgeCombinedService {
    @Autowired
    UserStatsBridgeCombinedDao userStatsBridgeCombinedDao;


    /**
     * 统计某一个某个国家的不同类型的pt用户数量
     * @param start 日期
     * @param countryCode 国家
     * @return
     */
    public Map<String, Integer> mapCountryAndDate(String start, String countryCode) {
        List<UserStatsBridgeCombined> list = userStatsBridgeCombinedDao.listUserByCountryAndDate(start, countryCode);
        if (list.size() == 0){
            log.info("userStatsBridgeCombinedDao 查询到start: size:{}",list.size());
            return null;
        }
        String defaultOP = "<OR>";
        String obfs2 = "obfs2";
        String obfs3 = "obfs3";
        String obfs4 = "obfs4";
        String websocket = "websocket";
        String fte = "fte";
        String meek = "meek";
        String scramblesuit = "scramblesuit";
        String snowflake = "snowflake";
        Map<String, Integer> map = new HashMap<>();
        for (UserStatsBridgeCombined userStatsBridgeCombined : list) {
            switch (userStatsBridgeCombined.getTransport()) {
                case "<OR>":
                    map.put(defaultOP,userStatsBridgeCombined.getHigh());
                    break;
                case "obfs2":
                    map.put(obfs2,userStatsBridgeCombined.getHigh());
                    break;
                case "obfs3":
                    map.put(obfs3,userStatsBridgeCombined.getHigh());
                    break;
                case "obfs4":
                    map.put(obfs4,userStatsBridgeCombined.getHigh());
                    break;
                case "websocket":
                    map.put(websocket,userStatsBridgeCombined.getHigh());
                    break;
                case "fte":
                    map.put(fte,userStatsBridgeCombined.getHigh());
                    break;
                case "meek":
                    map.put(meek,userStatsBridgeCombined.getHigh());
                    break;
                case "scramblesuit":
                    map.put(scramblesuit,userStatsBridgeCombined.getHigh());
                    break;
                case "snowflake":
                    map.put(snowflake,userStatsBridgeCombined.getHigh());
                    break;
            }
        }
        return map;
    }


    /**
     * 填充后续的数据
     * 查询最新的日期，然后startTime为最新日期的后一天，endTime为当天
     *
     */
    @Async("executor")
     @Scheduled(cron = "0 0/30 * * * ?  ")
    public void fillUserStatsBridgeCombined() throws KeyManagementException, NoSuchAlgorithmException {
        Date lastDate = userStatsBridgeCombinedDao.getLastDate();
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
        String url = Const.USERS_BRIDGE_COMBINED + "?start=" + startDateString + "&end=" + endDateString;
        System.out.println(url);
        //下载剩余的csv数据
        List<String> list = DownloadUtil.downloadCSV(url);
        List<UserStatsBridgeCombined> usersList = new ArrayList<UserStatsBridgeCombined>();
        for (String s : list) {
            String[] fields = s.split(",");
            UserStatsBridgeCombined userStatsBridgeCombined = new UserStatsBridgeCombined();
            userStatsBridgeCombined.setDate(DateTimeUtil.strToDate(fields[0]));
            userStatsBridgeCombined.setCountry(fields[1]);
            userStatsBridgeCombined.setTransport(fields[2]);

            BigDecimal bd1 = new BigDecimal(fields[3]);
            int low = Integer.parseInt(bd1.toPlainString());
            BigDecimal bd2 = new BigDecimal(fields[4]);
            int high = Integer.parseInt(bd2.toPlainString());

            userStatsBridgeCombined.setLow(low);
            userStatsBridgeCombined.setHigh(high);
            userStatsBridgeCombined.setFrac(Integer.parseInt(fields[5]));
            usersList.add(userStatsBridgeCombined);
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
            List<UserStatsBridgeCombined> sublist = usersList.subList(i * 400, end);
            if (sublist.size() == 0){
                break;
            }
            rows = rows + userStatsBridgeCombinedDao.insertUsers(sublist);
        }
        if (rows < 0){
            log.error("本次写入失败");
        }else {
            log.info("写入了{}条数据", rows);
        }
    }


}
