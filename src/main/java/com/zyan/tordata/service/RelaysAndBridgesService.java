package com.zyan.tordata.service;

import com.zyan.tordata.dao.RelaysAndBridgesDao;
import com.zyan.tordata.domain.RelaysAndBridges;
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
public class RelaysAndBridgesService {
    @Autowired
    RelaysAndBridgesDao relaysAndBridgesDao;

    public List<RelaysAndBridges> listRelaysAndBridges(String start, String end) {
        return relaysAndBridgesDao.listRelaysAndBridges(start, end);
    }

    /**
     * 填充后续的数据
     * 查询最新的日期，然后startTime为最新日期的后一天，endTime为当天
     *
     * @return 返回填充的数据条数
     */
    //TODO 需要设置定时任务
    public int fillRelaysAndBridges() throws KeyManagementException, NoSuchAlgorithmException {
        Date lastDate = relaysAndBridgesDao.getLastDate();
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
        //https://metrics.torproject.org/networksize.csv?start=2020-02-02&end=2020-05-02
        String url = Const.RELAYS_AND_BRIDGES + "?start=" + startDateString + "&end=" + endDateString;
        System.out.println(url);
        //下载剩余的csv数据
        List<String> list = DownloadUtil.downloadCSV(url);
        List<RelaysAndBridges> relaysAndBridgesList = new ArrayList<RelaysAndBridges>();
        for (String s : list) {
            String[] fields = s.split(",");
            RelaysAndBridges relaysAndBridges = new RelaysAndBridges();
            relaysAndBridges.setDate(DateTimeUtil.strToDate(fields[0]));
            relaysAndBridges.setRelays(Integer.parseInt(fields[1]));
            if (fields[2].equals("")) {
                fields[2] = "0";
            }
            relaysAndBridgesList.add(relaysAndBridges);
        }
        log.info(String.valueOf(relaysAndBridgesList.size()));
        //填充到数据库中
        int rows = 0;
        for (int i = 0; i < relaysAndBridgesList.size() / 400 + 1; i++) {
            int end = (i + 1) * 400;
            if (end >= list.size()) {
                end = list.size();
            }
            List<RelaysAndBridges> sublist = relaysAndBridgesList.subList(i * 400, end);
            if (sublist.size() == 0) {
                break;
            }
            rows = rows + relaysAndBridgesDao.insertRelaysAndBridges(sublist);
        }
        log.info("写入了{}条数据", rows);

        return rows;
    }

    public List<RelaysAndBridges> listRelaysAndBridgesDefault(String start, String end) {
        return relaysAndBridgesDao.listRelaysAndBridges(start, end);
    }
}
