package com.zyan.tordata.service;

import com.zyan.tordata.dao.OnionServiceTrafficDao;
import com.zyan.tordata.dao.OnionUniqueAddressDao;
import com.zyan.tordata.domain.OnionServiceTraffic;
import com.zyan.tordata.domain.OnionUniqueAddress;
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

@Slf4j
@Service
public class OnionUniqueAddressService {

    @Autowired
    private OnionUniqueAddressDao onionUniqueAddressDao;
    @Autowired
    private OnionServiceTrafficService onionServiceTrafficService;

    public List<OnionUniqueAddress> listOnionUniqueAddress(String start, String end){
        return onionUniqueAddressDao.listOnionAddress(start, end);
    }

    public List<List<Object>> listOnionServiceData(String start, String end){
        List<List<Object>> lists = new ArrayList<>();
        List<OnionUniqueAddress> list1 = onionUniqueAddressDao.listOnionAddress(start, end);
        List<OnionServiceTraffic> list2 = onionServiceTrafficService.listOnionServiceTraffic(start, end);
        if (list1.size() == 0 || list2.size() == 0){
            return lists;
        }
        lists.add(Collections.singletonList(list1));
        lists.add(Collections.singletonList(list2));
        return lists;
    }

//    @Async("executor")
//    @Scheduled(cron = "0 0/2 * * * ? ")
    public void fillOnionUniqueAddress() throws KeyManagementException, NoSuchAlgorithmException {
        Date lastDate = onionUniqueAddressDao.getLastDate();
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
        String url = Const.ONION_UNIQUE_ADDRESS + "?start=" + startDateString + "&end=" + endDateString;
        System.out.println(url);
        //下载剩余的csv数据
        List<String> list = DownloadUtil.downloadCSV(url);
        List<OnionUniqueAddress> usersList = new ArrayList<OnionUniqueAddress>();
        for (String s : list) {
            String[] fields = s.split(",");
            OnionUniqueAddress onionUniqueAddress = new OnionUniqueAddress();
            onionUniqueAddress.setDate(DateTimeUtil.strToDate(fields[0]));
            if (fields[1].equals("")){
                fields[1] = "0";
            }
            if (fields[2].equals("")){
                fields[2] = "0";
            }
            onionUniqueAddress.setOnions(Integer.parseInt(fields[1]));
            onionUniqueAddress.setFrac(Double.parseDouble(fields[2]));
            usersList.add(onionUniqueAddress);
        }
        if (list.size() == 0){
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
            List<OnionUniqueAddress> sublist = usersList.subList(i * 400, end);
            if (sublist.size() == 0){
                break;
            }
            rows = rows + onionUniqueAddressDao.insertOnionAddress(sublist);
        }
        if (rows < 0){
            log.error("本次写入失败");
        }else {
            log.info("写入了{}条数据", rows);
        }
    }
}
