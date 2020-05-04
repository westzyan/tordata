package com.zyan.tordata.service;

import com.zyan.tordata.dao.OnionServiceTrafficDao;
import com.zyan.tordata.domain.OnionServiceTraffic;
import com.zyan.tordata.result.Const;
import com.zyan.tordata.util.DateTimeUtil;
import com.zyan.tordata.util.DownloadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Slf4j
@Service
public class OnionServiceTrafficService {

    @Autowired
    private OnionServiceTrafficDao onionServiceTrafficDao;

    public List<OnionServiceTraffic> listOnionServiceTraffic(String start, String end){
        return onionServiceTrafficDao.listOnionServiceTraffic(start, end);
    }

    //TODO 需要设置定时任务
    public int fillOnionServiceTraffic() throws KeyManagementException, NoSuchAlgorithmException {
        Date lastDate = onionServiceTrafficDao.getLastDate();
        System.out.println(DateTimeUtil.dateToStr(lastDate));
        if (lastDate.equals(new Date())) {
            return 0;
        }
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(lastDate);
        calendar.add(Calendar.DATE, 1); //把日期往后增加一天,整数  往后推,负数往前移动
        Date startDate = calendar.getTime(); //这个时间就是日期往后推一天的结果
        String startDateString = DateTimeUtil.dateToStr(startDate);
        String endDateString = DateTimeUtil.dateToStr(new Date());
        String url = Const.ONION_SERVICE_TRAFFIC + "?start=" + startDateString + "&end=" + endDateString;
        System.out.println(url);
        //下载剩余的csv数据
        List<String> list = DownloadUtil.downloadCSV(url);
        List<OnionServiceTraffic> usersList = new ArrayList<OnionServiceTraffic>();
        for (String s : list) {
            String[] fields = s.split(",");
            OnionServiceTraffic onionServiceTraffic = new OnionServiceTraffic();
            onionServiceTraffic.setDate(DateTimeUtil.strToDate(fields[0]));
            if (fields[1].equals("")){
                fields[1] = "0";
            }
            if (fields[2].equals("")){
                fields[2] = "0";
            }
            onionServiceTraffic.setRelayed(Double.parseDouble(fields[1]));
            onionServiceTraffic.setFrac(Double.parseDouble(fields[2]));
            usersList.add(onionServiceTraffic);
        }
        System.out.println(usersList.size());
        //填充到数据库中, 为了加快写入速度，也为了避免堆溢出，每400条写入一次
        int rows = 0;
        for (int i = 0; i < usersList.size() / 400 + 1; i++) {
            int end = (i + 1) * 400;
            if (end >= list.size()) {
                end = list.size();
            }
            List<OnionServiceTraffic> sublist = usersList.subList(i * 400, end);
            if (sublist.size() == 0){
                break;
            }
            rows = rows + onionServiceTrafficDao.insertOnionServiceTraffic(sublist);
        }
        log.info("写入了{}条数据", rows);

        return rows;
    }
}
