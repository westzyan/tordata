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
     *
     * @return 返回填充的数据条数
     */
    public int fillUserStatsRelay() throws KeyManagementException, NoSuchAlgorithmException {
        Date lastDate = userStatsRelayCountryDao.getLastDate();
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
        //下载剩余的csv数据
        String csvData = DownloadUtil.downloadCSV(url);
        //处理为list
        List<String> list = null;
        List<UserStatsRelayCountry> usersList = new ArrayList<UserStatsRelayCountry>();
        for (String s : list) {
            String[] fields = s.split(",");
            UserStatsRelayCountry userStatsRelayCountry = new UserStatsRelayCountry();
            userStatsRelayCountry.setDate(DateTimeUtil.strToDate(fields[0]));
            userStatsRelayCountry.setCountry(fields[1]);
            userStatsRelayCountry.setUsers(Integer.parseInt(fields[2]));
            userStatsRelayCountry.setLower(Integer.parseInt(fields[3]));
            userStatsRelayCountry.setUpper(Integer.parseInt(fields[4]));
            userStatsRelayCountry.setFrac(Integer.parseInt(fields[5]));
            usersList.add(userStatsRelayCountry);
        }
        //填充到数据库中
        int fillNumber = userStatsRelayCountryDao.insertUsers(usersList);

//        return userStatsRelayCountryDao.listAllUser();
        return 0;
    }
}
