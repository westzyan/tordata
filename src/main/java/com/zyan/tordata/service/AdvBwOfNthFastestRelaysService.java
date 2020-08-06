package com.zyan.tordata.service;

import com.zyan.tordata.dao.AdvBwOfNthFastestRelaysDao;
import com.zyan.tordata.domain.AdvBwByIpVersion;
import com.zyan.tordata.domain.AdvBwDistribution;
import com.zyan.tordata.domain.AdvBwOfNthFastestRelays;
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
public class AdvBwOfNthFastestRelaysService {
    @Autowired
    AdvBwOfNthFastestRelaysDao advBwOfNthFastestRelaysDao;

    public List<AdvBwOfNthFastestRelays> listAdvBwOfNth(String start, String end) {
        return advBwOfNthFastestRelaysDao.listAdvBwOfNth(start,end);
    }

    /**
     * 填充后续的数据
     * 查询最新的日期，然后startTime为最新日期的后一天，endTime为当天
     */
//    @Async("executor")
//    @Scheduled(cron = "0 0/2 * * * ? ")
    public void fillAdvBwOfNth() throws KeyManagementException, NoSuchAlgorithmException {
        Date lastDate = advBwOfNthFastestRelaysDao.getLastDate();
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
        //https://metrics.torproject.org/advbwdist-relay.csv?start=2020-02-05&end=2020-05-05
        String url = Const.ADV_BW_OF_N_TH_FASTEST_RELAYS + "?start=" + startDateString + "&end=" + endDateString;
        System.out.println(url);
        //下载剩余的csv数据
        List<String> list = DownloadUtil.downloadCSV(url);
        List<AdvBwOfNthFastestRelays> advBwOfNthFastestRelaysList = new ArrayList<AdvBwOfNthFastestRelays>();
        for (String s : list) {
            String[] fields = s.split(",",4);
            AdvBwOfNthFastestRelays advBwOfNthFastestRelays = new AdvBwOfNthFastestRelays();
            advBwOfNthFastestRelays.setDate(DateTimeUtil.strToDate(fields[0]));
            advBwOfNthFastestRelays.setN(Integer.parseInt(fields[1]));
            //数据中存在XXXeXXX
            if (fields[2].equals("")){
                advBwOfNthFastestRelays.setAll("");
            }
            else{
                BigDecimal bd = new BigDecimal(fields[2]);
                String str = bd.toPlainString();
                advBwOfNthFastestRelays.setAll(str);
            }

            if (fields[3].equals("")){
                advBwOfNthFastestRelays.setExits("");
            }else{
                BigDecimal bd1 = new BigDecimal(fields[3]);
                String str1 = bd1.toPlainString();
                advBwOfNthFastestRelays.setExits(str1);
            }


            advBwOfNthFastestRelaysList.add(advBwOfNthFastestRelays);
        }
        if (list.size() == 0){
            log.info("未下载到数据");
            return;
        }
        //填充到数据库中
        int rows = 0;
        for (int i = 0; i < advBwOfNthFastestRelaysList.size() / 400 + 1; i++) {
            int end = (i + 1) * 400;
            if (end >= list.size()) {
                end = list.size();
            }
            List<AdvBwOfNthFastestRelays> sublist = advBwOfNthFastestRelaysList.subList(i * 400, end);
            if (sublist.size() == 0){
                break;
            }
            rows = rows + advBwOfNthFastestRelaysDao.insertAdvBwOfNth(sublist);
        }
        if (rows < 0){
            log.error("本次写入失败");
        }else {
            log.info("写入了{}条数据", rows);
        }
    }

    public List<List<List<String>>> listAdvBwOfNthDefault(String start, String end) {
        List<List<List<String>>> result = new ArrayList<>();
        String preDate;
        List<AdvBwOfNthFastestRelays> list = advBwOfNthFastestRelaysDao.listAdvBwOfNth(start, end);
        if (list.size() > 0) {
            preDate = DateTimeUtil.dateToStr(list.get(0).getDate());
        } else {
            return result;
        }
        List<List<String>> dateAllList = new ArrayList<List<String>>();
        List<List<String>> allDateList1 = new ArrayList<List<String>>();//1
        List<List<String>> allDateList2 = new ArrayList<List<String>>();//2
        List<List<String>> allDateList3 = new ArrayList<List<String>>();//3
        List<List<String>> allDateList4 = new ArrayList<List<String>>();//5
        List<List<String>> allDateList5 = new ArrayList<List<String>>();//10
        List<List<String>> allDateList6 = new ArrayList<List<String>>();//20
        List<List<String>> allDateList7 = new ArrayList<List<String>>();//30
        List<List<String>> allDateList8 = new ArrayList<List<String>>();//50
        List<List<String>> allDateList9 = new ArrayList<List<String>>();//100
        List<List<String>> allDateList10 = new ArrayList<List<String>>();//200
        List<List<String>> allDateList11 = new ArrayList<List<String>>();//300
        List<List<String>> allDateList12 = new ArrayList<List<String>>();//500
        List<List<String>> allDateList13 = new ArrayList<List<String>>();//1000
        List<List<String>> allDateList14 = new ArrayList<List<String>>();//2000
        List<List<String>> allDateList15 = new ArrayList<List<String>>();//3000
        List<List<String>> allDateList16 = new ArrayList<List<String>>();//5000


        List<String> dateList = new ArrayList<String>();
        List<String> allList1 = new ArrayList<String>();//100
        List<String> allList2 = new ArrayList<String>();//99
        List<String> allList3 = new ArrayList<String>();//98
        List<String> allList4 = new ArrayList<String>();//97
        List<String> allList5 = new ArrayList<String>();//95
        List<String> allList6 = new ArrayList<String>();//91
        List<String> allList7 = new ArrayList<String>();//90
        List<String> allList8 = new ArrayList<String>();//80
        List<String> allList9 = new ArrayList<String>();//75
        List<String> allList10 = new ArrayList<String>();//70
        List<String> allList11 = new ArrayList<String>();//60
        List<String> allList12 = new ArrayList<String>();//50
        List<String> allList13 = new ArrayList<String>();//40
        List<String> allList14 = new ArrayList<String>();//30
        List<String> allList15 = new ArrayList<String>();//25
        List<String> allList16 = new ArrayList<String>();//20


        List<String> exitList1 = new ArrayList<String>();//100
        List<String> exitList2 = new ArrayList<String>();//99
        List<String> exitList3 = new ArrayList<String>();//98
        List<String> exitList4 = new ArrayList<String>();//97
        List<String> exitList5 = new ArrayList<String>();//95
        List<String> exitList6 = new ArrayList<String>();//91
        List<String> exitList7 = new ArrayList<String>();//90
        List<String> exitList8 = new ArrayList<String>();//80
        List<String> exitList9 = new ArrayList<String>();//75
        List<String> exitList10 = new ArrayList<String>();//70
        List<String> exitList11 = new ArrayList<String>();//60
        List<String> exitList12 = new ArrayList<String>();//50
        List<String> exitList13 = new ArrayList<String>();//40
        List<String> exitList14 = new ArrayList<String>();//30
        List<String> exitList15 = new ArrayList<String>();//25
        List<String> exitList16 = new ArrayList<String>();//20


        String all1 = "0";
        String all2 = "0";
        String all3 = "0";
        String all4 = "0";
        String all5 = "0";
        String all6 = "0";
        String all7 = "0";
        String all8 = "0";
        String all9 = "0";
        String all10 = "0";
        String all11 = "0";
        String all12 = "0";
        String all13 = "0";
        String all14 = "0";
        String all15 = "0";
        String all16 = "0";

        String exit1 = "0";
        String exit2 = "0";
        String exit3 = "0";
        String exit4 = "0";
        String exit5 = "0";
        String exit6 = "0";
        String exit7 = "0";
        String exit8 = "0";
        String exit9 = "0";
        String exit10 = "0";
        String exit11 = "0";
        String exit12 = "0";
        String exit13 = "0";
        String exit14 = "0";
        String exit15 = "0";
        String exit16 = "0";


        for (AdvBwOfNthFastestRelays advBwOfNthFastestRelays : list) {
            Date curDate = advBwOfNthFastestRelays.getDate();
            String dateStr = DateTimeUtil.dateToStr(curDate);
            if (preDate.equals(dateStr)) {
                switch (advBwOfNthFastestRelays.getN()) {
                    case 1:
                        all1 = String.valueOf(advBwOfNthFastestRelays.getAll());
                        exit1 = String.valueOf(advBwOfNthFastestRelays.getExits());
                        break;
                    case 2:
                        all2 = String.valueOf(advBwOfNthFastestRelays.getAll());
                        exit2 = String.valueOf(advBwOfNthFastestRelays.getExits());
                        break;
                    case 3:
                        all3 = String.valueOf(advBwOfNthFastestRelays.getAll());
                        exit3 = String.valueOf(advBwOfNthFastestRelays.getExits());
                        break;
                    case 5:
                        all4 = String.valueOf(advBwOfNthFastestRelays.getAll());
                        exit4 = String.valueOf(advBwOfNthFastestRelays.getExits());
                        break;
                    case 10:
                        all5 = String.valueOf(advBwOfNthFastestRelays.getAll());
                        exit5 = String.valueOf(advBwOfNthFastestRelays.getExits());
                        break;
                    case 20:
                        all6 = String.valueOf(advBwOfNthFastestRelays.getAll());
                        exit6 = String.valueOf(advBwOfNthFastestRelays.getExits());
                        break;
                    case 30:
                        all7 = String.valueOf(advBwOfNthFastestRelays.getAll());
                        exit7 = String.valueOf(advBwOfNthFastestRelays.getExits());
                        break;
                    case 50:
                        all8 = String.valueOf(advBwOfNthFastestRelays.getAll());
                        exit8 = String.valueOf(advBwOfNthFastestRelays.getExits());
                        break;
                    case 100:
                        all9 = String.valueOf(advBwOfNthFastestRelays.getAll());
                        exit9 = String.valueOf(advBwOfNthFastestRelays.getExits());
                        break;
                    case 200:
                        all10 = String.valueOf(advBwOfNthFastestRelays.getAll());
                        exit10 = String.valueOf(advBwOfNthFastestRelays.getExits());
                        break;
                    case 300:
                        all11 = String.valueOf(advBwOfNthFastestRelays.getAll());
                        exit11 = String.valueOf(advBwOfNthFastestRelays.getExits());
                        break;
                    case 500:
                        all12 = String.valueOf(advBwOfNthFastestRelays.getAll());
                        exit12 = String.valueOf(advBwOfNthFastestRelays.getExits());
                        break;
                    case 1000:
                        all13 = String.valueOf(advBwOfNthFastestRelays.getAll());
                        exit13 = String.valueOf(advBwOfNthFastestRelays.getExits());
                        break;
                    case 2000:
                        all14 = String.valueOf(advBwOfNthFastestRelays.getAll());
                        exit14 = String.valueOf(advBwOfNthFastestRelays.getExits());
                        break;
                    case 3000:
                        all15 = String.valueOf(advBwOfNthFastestRelays.getAll());
                        exit15 = String.valueOf(advBwOfNthFastestRelays.getExits());
                        break;
                    case 5000:
                        all16 = String.valueOf(advBwOfNthFastestRelays.getAll());
                        exit16 = String.valueOf(advBwOfNthFastestRelays.getExits());
                        break;

                }
            } else {
                dateList.add(preDate);
                allList1.add(all1);
                allList2.add(all2);
                allList3.add(all3);
                allList3.add(all3);
                allList4.add(all4);
                allList5.add(all5);
                allList6.add(all6);
                allList7.add(all7);
                allList8.add(all8);
                allList9.add(all9);
                allList10.add(all10);
                allList11.add(all11);
                allList12.add(all12);
                allList13.add(all13);
                allList14.add(all14);
                allList15.add(all15);
                allList16.add(all16);


                exitList1.add(exit1);
                exitList2.add(exit2);
                exitList3.add(exit3);
                exitList4.add(exit4);
                exitList5.add(exit5);
                exitList6.add(exit6);
                exitList7.add(exit7);
                exitList8.add(exit8);
                exitList9.add(exit9);
                exitList10.add(exit10);
                exitList11.add(exit11);
                exitList12.add(exit12);
                exitList13.add(exit13);
                exitList14.add(exit14);
                exitList15.add(exit15);
                exitList16.add(exit16);


                all1 = "0";
                all2 = "0";
                all3 = "0";
                all4 = "0";
                all5 = "0";
                all6 = "0";
                all7 = "0";
                all8 = "0";
                all9 = "0";
                all10 = "0";
                all11 = "0";
                all12 = "0";
                all13 = "0";
                all14 = "0";
                all15 = "0";
                all16 = "0";


                exit1 = "0";
                exit2 = "0";
                exit3 = "0";
                exit4 = "0";
                exit5 = "0";
                exit6 = "0";
                exit7 = "0";
                exit8 = "0";
                exit9 = "0";
                exit10 = "0";
                exit11 = "0";
                exit12 = "0";
                exit13 = "0";
                exit14 = "0";
                exit15 = "0";
                exit16 = "0";


                preDate = dateStr;

                switch (advBwOfNthFastestRelays.getN()) {
                    case 1:
                        all1 = String.valueOf(advBwOfNthFastestRelays.getAll());
                        exit1 = String.valueOf(advBwOfNthFastestRelays.getExits());
                        break;
                    case 2:
                        all2 = String.valueOf(advBwOfNthFastestRelays.getAll());
                        exit2 = String.valueOf(advBwOfNthFastestRelays.getExits());
                        break;
                    case 3:
                        all3 = String.valueOf(advBwOfNthFastestRelays.getAll());
                        exit3 = String.valueOf(advBwOfNthFastestRelays.getExits());
                        break;
                    case 5:
                        all4 = String.valueOf(advBwOfNthFastestRelays.getAll());
                        exit4 = String.valueOf(advBwOfNthFastestRelays.getExits());
                        break;
                    case 10:
                        all5 = String.valueOf(advBwOfNthFastestRelays.getAll());
                        exit5 = String.valueOf(advBwOfNthFastestRelays.getExits());
                        break;
                    case 20:
                        all6 = String.valueOf(advBwOfNthFastestRelays.getAll());
                        exit6 = String.valueOf(advBwOfNthFastestRelays.getExits());
                        break;
                    case 30:
                        all7 = String.valueOf(advBwOfNthFastestRelays.getAll());
                        exit7 = String.valueOf(advBwOfNthFastestRelays.getExits());
                        break;
                    case 50:
                        all8 = String.valueOf(advBwOfNthFastestRelays.getAll());
                        exit8 = String.valueOf(advBwOfNthFastestRelays.getExits());
                        break;
                    case 100:
                        all9 = String.valueOf(advBwOfNthFastestRelays.getAll());
                        exit9 = String.valueOf(advBwOfNthFastestRelays.getExits());
                        break;
                    case 200:
                        all10 = String.valueOf(advBwOfNthFastestRelays.getAll());
                        exit10 = String.valueOf(advBwOfNthFastestRelays.getExits());
                        break;
                    case 300:
                        all11 = String.valueOf(advBwOfNthFastestRelays.getAll());
                        exit11 = String.valueOf(advBwOfNthFastestRelays.getExits());
                        break;
                    case 500:
                        all12 = String.valueOf(advBwOfNthFastestRelays.getAll());
                        exit12 = String.valueOf(advBwOfNthFastestRelays.getExits());
                        break;
                    case 1000:
                        all13 = String.valueOf(advBwOfNthFastestRelays.getAll());
                        exit13 = String.valueOf(advBwOfNthFastestRelays.getExits());
                        break;
                    case 2000:
                        all14 = String.valueOf(advBwOfNthFastestRelays.getAll());
                        exit14 = String.valueOf(advBwOfNthFastestRelays.getExits());
                        break;
                    case 3000:
                        all15 = String.valueOf(advBwOfNthFastestRelays.getAll());
                        exit15 = String.valueOf(advBwOfNthFastestRelays.getExits());
                        break;
                    case 5000:
                        all16 = String.valueOf(advBwOfNthFastestRelays.getAll());
                        exit16 = String.valueOf(advBwOfNthFastestRelays.getExits());
                        break;

                }
            }
        }
        dateList.add(preDate);
        allList1.add(all1);
        allList2.add(all2);
        allList3.add(all3);
        allList3.add(all3);
        allList4.add(all4);
        allList5.add(all5);
        allList6.add(all6);
        allList7.add(all7);
        allList8.add(all8);
        allList9.add(all9);
        allList10.add(all10);
        allList11.add(all11);
        allList12.add(all12);
        allList13.add(all13);
        allList14.add(all14);
        allList15.add(all15);
        allList16.add(all16);

        exitList1.add(exit1);
        exitList2.add(exit2);
        exitList3.add(exit3);
        exitList4.add(exit4);
        exitList5.add(exit5);
        exitList6.add(exit6);
        exitList7.add(exit7);
        exitList8.add(exit8);
        exitList9.add(exit9);
        exitList10.add(exit10);
        exitList11.add(exit11);
        exitList12.add(exit12);
        exitList13.add(exit13);
        exitList14.add(exit14);
        exitList15.add(exit15);
        exitList16.add(exit16);


        dateAllList.add(dateList);
        result.add(dateAllList);

        allDateList1.add(allList1);
        allDateList1.add(exitList1);
        result.add(allDateList1);

        allDateList2.add(allList2);
        allDateList2.add(exitList2);
        result.add(allDateList2);

        allDateList3.add(allList3);
        allDateList3.add(exitList3);
        result.add(allDateList3);

        allDateList4.add(allList4);
        allDateList4.add(exitList4);
        result.add(allDateList4);

        allDateList5.add(allList5);
        allDateList5.add(exitList5);
        result.add(allDateList5);

        allDateList6.add(allList6);
        allDateList6.add(exitList6);
        result.add(allDateList6);

        allDateList7.add(allList7);
        allDateList7.add(exitList7);
        result.add(allDateList7);

        allDateList8.add(allList8);
        allDateList8.add(exitList8);
        result.add(allDateList8);

        allDateList9.add(allList9);
        allDateList9.add(exitList9);
        result.add(allDateList9);

        allDateList10.add(allList10);
        allDateList10.add(exitList10);
        result.add(allDateList10);

        allDateList11.add(allList11);
        allDateList11.add(exitList11);
        result.add(allDateList11);

        allDateList12.add(allList12);
        allDateList12.add(exitList12);
        result.add(allDateList12);

        allDateList13.add(allList13);
        allDateList13.add(exitList13);
        result.add(allDateList13);

        allDateList14.add(allList14);
        allDateList14.add(exitList14);
        result.add(allDateList14);

        allDateList15.add(allList15);
        allDateList15.add(exitList15);
        result.add(allDateList15);

        allDateList16.add(allList16);
        allDateList16.add(exitList16);
        result.add(allDateList16);



        return result;
    }
}
