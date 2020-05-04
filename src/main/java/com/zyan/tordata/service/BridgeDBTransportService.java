package com.zyan.tordata.service;

import com.zyan.tordata.dao.BridgeDBTransportDao;
import com.zyan.tordata.dao.UserStatsBridgeVersionDao;
import com.zyan.tordata.domain.BridgeDBTransport;
import com.zyan.tordata.domain.UserStatsBridgeVersion;
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
public class BridgeDBTransportService {
    @Autowired
    BridgeDBTransportDao bridgeDBTransportDao;

    public List<BridgeDBTransport> listUserByStartAndEnd(String start, String end) {
        return bridgeDBTransportDao.listUserByStartAndEnd(start, end);
    }

    /**
     * 填充后续的数据
     * 查询最新的日期，然后startTime为最新日期的后一天，endTime为当天
     *
     * @return 返回填充的数据条数
     */
    //TODO 需要设置定时任务
    public int fillUserStatsBridgeVersion() throws KeyManagementException, NoSuchAlgorithmException {
        Date lastDate = bridgeDBTransportDao.getLastDate();
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
        String url = Const.BRIDGEDB_TRANSPORT + "?start=" + startDateString + "&end=" + endDateString;
        System.out.println(url);
        //下载剩余的csv数据
        List<String> list = DownloadUtil.downloadCSV(url);
        List<BridgeDBTransport> usersList = new ArrayList<BridgeDBTransport>();
        for (String s : list) {
            String[] fields = s.split(",");
            BridgeDBTransport bridgeDBTransport = new BridgeDBTransport();
            bridgeDBTransport.setDate(DateTimeUtil.strToDate(fields[0]));
            bridgeDBTransport.setTransport(fields[1]);
            bridgeDBTransport.setRequests(Integer.parseInt(fields[2]));
            usersList.add(bridgeDBTransport);
        }
        System.out.println(usersList.size());
        //填充到数据库中, 为了加快写入速度，也为了避免堆溢出，每400条写入一次
        int rows = 0;
        for (int i = 0; i < usersList.size() / 400 + 1; i++) {
            int end = (i + 1) * 400;
            if (end >= list.size()) {
                end = list.size();
            }
            List<BridgeDBTransport> sublist = usersList.subList(i * 400, end);
            if (sublist.size() == 0){
                break;
            }
            rows = rows + bridgeDBTransportDao.insertUsers(sublist);
        }
        log.info("写入了{}条数据", rows);

        return rows;
    }


}
