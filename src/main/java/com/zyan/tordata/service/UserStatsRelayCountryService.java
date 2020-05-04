package com.zyan.tordata.service;

import com.zyan.tordata.dao.UserStatsRelayCountryDao;
import com.zyan.tordata.domain.UserStatsRelayCountry;
import com.zyan.tordata.result.Const;
import com.zyan.tordata.util.DateTimeUtil;
import com.zyan.tordata.util.DownloadUtil;
import org.apache.ibatis.annotations.Insert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Service
public class UserStatsRelayCountryService {
    @Autowired
    UserStatsRelayCountryDao userStatsRelayCountryDao;

    public List<UserStatsRelayCountry> listAllUser() {
        return userStatsRelayCountryDao.listAllUser();
    }

    /**
     * 填充后续的数据
     * 查询最新的日期，然后startTime为最新日期的后一天，endTime为当天
     * @return 返回填充的数据条数
     */
    //TODO 需要设置定时任务
    public int fillUserStatsRelay() throws KeyManagementException, NoSuchAlgorithmException {
        Date lastDate = userStatsRelayCountryDao.getLastDate();
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
        String url = Const.USERS_RELAY + "?start=" + startDateString + "&end=" + endDateString + "events=off";
        System.out.println(url);
        //下载剩余的csv数据
        List<String> list = DownloadUtil.downloadCSV(url);
        List<UserStatsRelayCountry> usersList = new ArrayList<UserStatsRelayCountry>();
        for (String s : list) {
            String[] fields = s.split(",");
            UserStatsRelayCountry userStatsRelayCountry = new UserStatsRelayCountry();
            userStatsRelayCountry.setDate(DateTimeUtil.strToDate(fields[0]));
            userStatsRelayCountry.setCountry(fields[1]);
            userStatsRelayCountry.setUsers(Integer.parseInt(fields[2]));
            if (fields[3].equals("")){
                fields[3] = "0";
            }
            if (fields[4].equals("")){
                fields[4] = "0";
            }
            userStatsRelayCountry.setLower(Integer.parseInt(fields[3]));
            userStatsRelayCountry.setUpper(Integer.parseInt(fields[4]));
            userStatsRelayCountry.setFrac(Integer.parseInt(fields[5]));
            usersList.add(userStatsRelayCountry);
        }
        System.out.println(usersList.size());
        //填充到数据库中
        int fillNumber = userStatsRelayCountryDao.insertUsers(usersList);
        System.out.println(fillNumber);

        return fillNumber;
    }


}
