package com.zyan.tordata.service;

import com.zyan.tordata.dao.UserStatsBridgeCountryDao;
import com.zyan.tordata.dao.UserStatsRelayCountryDao;
import com.zyan.tordata.domain.UserStatsBridgeCountry;
import com.zyan.tordata.domain.UserStatsRelayCountry;
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
public class UserStatsBridgeCountryService {
    @Autowired
    UserStatsBridgeCountryDao userStatsBridgeCountryDao;

    public List<UserStatsBridgeCountry> listAllUser() {
        return userStatsBridgeCountryDao.listAllUser();
    }

    public List<UserStatsBridgeCountry> listUserByCountryAndDate(String country, String start, String end) {
        return userStatsBridgeCountryDao.listUserByCountryAndDate(country, start, end);
    }


    /**
     * 填充后续的数据
     * 查询最新的日期，然后startTime为最新日期的后一天，endTime为当天
     * @return 返回填充的数据条数
     */
    //TODO 需要设置定时任务
    public int fillUserStatsBridgeCountry() throws KeyManagementException, NoSuchAlgorithmException {
        Date lastDate = userStatsBridgeCountryDao.getLastDate();
        System.out.println(lastDate);
        if (lastDate.equals(new Date())) {
            return 0;
        }
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(lastDate);
        calendar.add(Calendar.DATE, 1); //把日期往后增加一天,整数  往后推,负数往前移动
        Date startDate = calendar.getTime(); //这个时间就是日期往后推一天的结果
        String startDateString = DateTimeUtil.dateToStr(startDate);
        String endDateString = DateTimeUtil.dateToStr(new Date());
        //https://metrics.torproject.org/userstats-relay-country.csv?start=2020-01-30&end=2020-04-29&events=off"
        String url = Const.USERS_BRIDGE_COUNTRY + "?start=" + startDateString + "&end=" + endDateString;
        System.out.println(url);
        //下载剩余的csv数据
        List<String> list = DownloadUtil.downloadCSV(url);
        List<UserStatsBridgeCountry> usersList = new ArrayList<UserStatsBridgeCountry>();
        for (String s : list) {
            String[] fields = s.split(",");
            UserStatsBridgeCountry userStatsBridgeCountry = new UserStatsBridgeCountry();
            userStatsBridgeCountry.setDate(DateTimeUtil.strToDate(fields[0]));
            userStatsBridgeCountry.setCountry(fields[1]);
            userStatsBridgeCountry.setUsers(Integer.parseInt(fields[2]));
            userStatsBridgeCountry.setFrac(Integer.parseInt(fields[3]));
            usersList.add(userStatsBridgeCountry);
        }
        System.out.println(usersList.size());
        //填充到数据库中
        int fillNumber = userStatsBridgeCountryDao.insertUsers(usersList);
        log.info("写入了{}条数据",fillNumber);

        return fillNumber;
    }


}
