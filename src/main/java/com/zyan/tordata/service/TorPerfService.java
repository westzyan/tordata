package com.zyan.tordata.service;

import com.zyan.tordata.dao.TorPerfDao;
import com.zyan.tordata.domain.BridgeDBTransport;
import com.zyan.tordata.domain.TorPerf;
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
public class TorPerfService {
    @Autowired
    private TorPerfDao torPerfDao;

    public List<TorPerf> listTorPerfByCondition(int filesize, String server, String start, String end){
        return torPerfDao.listAllUserByCondition(filesize, server, start, end);
    }

    //TODO 需要设置定时任务
    public int fillTorPerf() throws KeyManagementException, NoSuchAlgorithmException {
        Date lastDate = torPerfDao.getLastDate();
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
        String url = Const.TORPERF + "?start=" + startDateString + "&end=" + endDateString;
        System.out.println(url);
        //下载剩余的csv数据
        List<String> list = DownloadUtil.downloadCSV(url);
        List<TorPerf> usersList = new ArrayList<TorPerf>();
        for (String s : list) {
            String[] fields = s.split(",", 7);  //这里为了处理类似于"2017-07-12,51200,op-us,onion,,,"
            TorPerf torPerf = new TorPerf();
            torPerf.setDate(DateTimeUtil.strToDate(fields[0]));
            torPerf.setFilesize(Integer.parseInt(fields[1]));
            torPerf.setSource(fields[2]);
            torPerf.setServer(fields[3]);
            if (fields[4].equals("")){
                fields[4] = "0";
            }
            if (fields[5].equals("")){
                fields[5] = "0";
            }
            if (fields[6].equals("")){
                fields[6] = "0";
            }
            torPerf.setQ1(Double.parseDouble(fields[4]));
            torPerf.setMd(Double.parseDouble(fields[5]));
            torPerf.setQ3(Double.parseDouble(fields[6]));
            usersList.add(torPerf);
        }
        System.out.println(usersList.size());
        //填充到数据库中, 为了加快写入速度，也为了避免堆溢出，每400条写入一次
        int rows = 0;
        for (int i = 0; i < usersList.size() / 400 + 1; i++) {
            int end = (i + 1) * 400;
            if (end >= list.size()) {
                end = list.size();
            }
            List<TorPerf> sublist = usersList.subList(i * 400, end);
            if (sublist.size() == 0){
                break;
            }
            rows = rows + torPerfDao.insertPerf(sublist);
        }
        log.info("写入了{}条数据", rows);

        return rows;
    }

    public static void main(String[] args) {
        String s = "2017-07-12,51200,op-us,onion,,,";
        String[] strings = s.split(",",7);
        System.out.println(strings.length);
        for (String string : strings) {
            System.out.println(string);
        }
    }
}
