package com.zyan.tordata.service;

import com.zyan.tordata.dao.TorPerfDao;
import com.zyan.tordata.domain.BridgeDBTransport;
import com.zyan.tordata.domain.TorPerf;
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
public class TorPerfService {
    @Autowired
    private TorPerfDao torPerfDao;

    public List<TorPerf> listTorPerfByCondition(int filesize, String server, String start, String end){
        return torPerfDao.listAllUserByCondition(filesize, server, start, end);
    }


    public List<List<String>> listTorperf(int filesize, String server, String start, String end){
        List<List<String>> result = new ArrayList<List<String>>();
        String preDate;
        List<TorPerf> list = torPerfDao.listAllUserByCondition(filesize, server, start, end);
        if (list.size() > 0) {
            preDate = DateTimeUtil.dateToStr(list.get(0).getDate());
        } else {
            return result;
        }
        List<String> dateList = new ArrayList<String>();
        List<String> moriaList = new ArrayList<String>();
        List<String> torperfList = new ArrayList<String>();
        List<String> sivList = new ArrayList<String>();
        List<String> op_hkList = new ArrayList<String>();
        List<String> op_nlList = new ArrayList<String>();
        List<String> op_usList = new ArrayList<String>();

        String moria = "0";
        String torperf = "0";
        String siv = "0";
        String op_hk = "0";
        String op_nl = "0";
        String op_us = "0";

        for (TorPerf torPerf1 : list) {
            Date curDate = torPerf1.getDate();
            String dateStr = DateTimeUtil.dateToStr(curDate);
            if (!preDate.equals(dateStr)){
                dateList.add(preDate);
                moriaList.add(moria);
                torperfList.add(torperf);
                sivList.add(siv);
                op_hkList.add(op_hk);
                op_nlList.add(op_nl);
                op_usList.add(op_us);

                moria = "0";
                torperf = "0";
                siv = "0";
                op_hk = "0";
                op_nl = "0";
                op_us = "0";

                preDate = dateStr;
            }

            switch (torPerf1.getSource()){
                case "moria":
                    moria = String.valueOf(torPerf1.getMd());
                    break;
                case "torperf":
                    torperf = String.valueOf(torPerf1.getMd());
                    break;
                case "siv":
                    siv = String.valueOf(torPerf1.getMd());
                    break;
                case "op-hk":
                    op_hk = String.valueOf(torPerf1.getMd());
                    break;
                case "op-nl":
                    op_nl = String.valueOf(torPerf1.getMd());
                    break;
                case "op-us":
                    op_us = String.valueOf(torPerf1.getMd());
                    break;
            }
        }
        dateList.add(preDate);
        moriaList.add(moria);
        torperfList.add(torperf);
        sivList.add(siv);
        op_hkList.add(op_hk);
        op_nlList.add(op_nl);
        op_usList.add(op_us);

        result.add(dateList);
        result.add(moriaList);
        result.add(torperfList);
        result.add(sivList);
        result.add(op_hkList);
        result.add(op_nlList);
        result.add(op_usList);

        return result;
    }


//    @Async("executor")
//    @Scheduled(cron = "0 0/2 * * * ? ")
    public void fillTorPerf() throws KeyManagementException, NoSuchAlgorithmException {
        Date lastDate = torPerfDao.getLastDate();
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
            List<TorPerf> sublist = usersList.subList(i * 400, end);
            if (sublist.size() == 0){
                break;
            }
            rows = rows + torPerfDao.insertPerf(sublist);
        }
        if (rows < 0){
            log.error("本次写入失败");
        }else {
            log.info("写入了{}条数据", rows);
        }
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
