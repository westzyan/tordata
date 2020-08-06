package com.zyan.tordata.service;

import com.zyan.tordata.dao.TotalConsensusWeightsAcrossBwAuDao;
import com.zyan.tordata.domain.RelaysByRelayFlag;
import com.zyan.tordata.domain.TotalConsensusWeightsAcrossBwAu;
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
public class TotalConsensusWeightsAcrossBwAuService {
    @Autowired
    TotalConsensusWeightsAcrossBwAuDao totalConsensusWeightsAcrossBwAuDao;

    public List<TotalConsensusWeightsAcrossBwAu> listTotalConW(String start, String end) {
        return totalConsensusWeightsAcrossBwAuDao.listTotalConW(start, end);
    }

    public List<List<String>> listTotalConWDefault(String start, String end) {
        List<List<String>> result = new ArrayList<List<String>>();
        String preDate;
        List<TotalConsensusWeightsAcrossBwAu> list = totalConsensusWeightsAcrossBwAuDao.listTotalConW(start, end);
        if (list.size() > 0) {
            preDate = DateTimeUtil.dateToStr(list.get(0).getDate());
        } else {
            return result;
        }

        List<String> dateList = new ArrayList<String>();
        List<String> bastetList = new ArrayList<String>();
        List<String> consensusList = new ArrayList<String>();
        List<String> dizumList = new ArrayList<String>();
        List<String> faravaharList = new ArrayList<String>();
        List<String> gabelmooList = new ArrayList<String>();
        List<String> longclawList = new ArrayList<String>();
        List<String> maatuskaList = new ArrayList<String>();
        List<String> moria1List = new ArrayList<String>();


        String bastet = "0";
        String consensus = "0";
        String dizum = "0";
        String faravahar = "0";
        String gabelmoo = "0";
        String longclaw = "0";
        String maatuska = "0";
        String moria1 = "0";

        for (TotalConsensusWeightsAcrossBwAu totalConsensusWeightsAcrossBwAu : list) {
            Date curDate = totalConsensusWeightsAcrossBwAu.getDate();
            String dateStr = DateTimeUtil.dateToStr(curDate);
            if (preDate.equals(dateStr)) {
                switch (totalConsensusWeightsAcrossBwAu.getNickname()) {
                    case "bastet":
                        bastet = String.valueOf(totalConsensusWeightsAcrossBwAu.getTotalcw());
                        break;
                    case "consensus":
                        consensus = String.valueOf(totalConsensusWeightsAcrossBwAu.getTotalcw());
                        break;
                    case "dizum":
                        dizum = String.valueOf(totalConsensusWeightsAcrossBwAu.getTotalcw());
                        break;
                    case "faravahar":
                        faravahar = String.valueOf(totalConsensusWeightsAcrossBwAu.getTotalcw());
                        break;
                    case "gabelmoo":
                        gabelmoo = String.valueOf(totalConsensusWeightsAcrossBwAu.getTotalcw());
                        break;
                    case "longclaw":
                        longclaw = String.valueOf(totalConsensusWeightsAcrossBwAu.getTotalcw());
                        break;
                    case "maatuska":
                        maatuska = String.valueOf(totalConsensusWeightsAcrossBwAu.getTotalcw());
                        break;
                    case "moria1":
                        moria1 = String.valueOf(totalConsensusWeightsAcrossBwAu.getTotalcw());
                        break;
                }
            } else {
                dateList.add(preDate);
                bastetList.add(bastet);
                consensusList.add(consensus);
                dizumList.add(dizum);
                faravaharList.add(faravahar);
                gabelmooList.add(gabelmoo);
                longclawList.add(longclaw);
                maatuskaList.add(maatuska);
                moria1List.add(moria1);

                bastet = "0";
                consensus = "0";
                dizum = "0";
                faravahar = "0";
                gabelmoo = "0";
                longclaw = "0";
                maatuska = "0";
                moria1 = "0";

                preDate = dateStr;

                switch (totalConsensusWeightsAcrossBwAu.getNickname()) {
                    case "bastet":
                        bastet = String.valueOf(totalConsensusWeightsAcrossBwAu.getTotalcw());
                        break;
                    case "consensus":
                        consensus = String.valueOf(totalConsensusWeightsAcrossBwAu.getTotalcw());
                        break;
                    case "dizum":
                        dizum = String.valueOf(totalConsensusWeightsAcrossBwAu.getTotalcw());
                        break;
                    case "faravahar":
                        faravahar = String.valueOf(totalConsensusWeightsAcrossBwAu.getTotalcw());
                        break;
                    case "gabelmoo":
                        gabelmoo = String.valueOf(totalConsensusWeightsAcrossBwAu.getTotalcw());
                        break;
                    case "longclaw":
                        longclaw = String.valueOf(totalConsensusWeightsAcrossBwAu.getTotalcw());
                        break;
                    case "maatuska":
                        maatuska = String.valueOf(totalConsensusWeightsAcrossBwAu.getTotalcw());
                        break;
                    case "moria1":
                        moria1 = String.valueOf(totalConsensusWeightsAcrossBwAu.getTotalcw());
                        break;
                }

            }
        }

        dateList.add(preDate);
        bastetList.add(bastet);
        consensusList.add(consensus);
        dizumList.add(dizum);
        faravaharList.add(faravahar);
        gabelmooList.add(gabelmoo);
        longclawList.add(longclaw);
        maatuskaList.add(maatuska);
        moria1List.add(moria1);

        result.add(dateList);
        result.add(bastetList);
        result.add(consensusList);
        result.add(dizumList);
        result.add(faravaharList);
        result.add(gabelmooList);
        result.add(longclawList);
        result.add(maatuskaList);
        result.add(moria1List);

        return result;
    }

    /**
     * 填充后续的数据
     * 查询最新的日期，然后startTime为最新日期的后一天，endTime为当天
     *
     * @return 返回填充的数据条数
     */
//    @Async("executor")
//    @Scheduled(cron = "0 0/2 * * * ? ")
    public void fillTotalCW() throws KeyManagementException, NoSuchAlgorithmException {
        Date lastDate = totalConsensusWeightsAcrossBwAuDao.getLastDate();
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
        //https://metrics.torproject.org/totalcw.csv?start=2020-02-02&end=2020-05-02
        String url = Const.TOTAL_CONSENSUS_WEIGHT + "?start=" + startDateString + "&end=" + endDateString;
        System.out.println(url);
        //下载剩余的csv数据
        List<String> list = DownloadUtil.downloadCSV(url);
        List<TotalConsensusWeightsAcrossBwAu> totalConsensusWeightsAcrossBwAuList = new ArrayList<TotalConsensusWeightsAcrossBwAu>();
        for (String s : list) {
            String[] fields = s.split(",");
            TotalConsensusWeightsAcrossBwAu totalConsensusWeightsAcrossBwAu = new TotalConsensusWeightsAcrossBwAu();
            totalConsensusWeightsAcrossBwAu.setDate(DateTimeUtil.strToDate(fields[0]));
            totalConsensusWeightsAcrossBwAu.setNickname(fields[1]);
            totalConsensusWeightsAcrossBwAu.setTotalcw(Integer.parseInt(fields[2]));

            totalConsensusWeightsAcrossBwAuList.add(totalConsensusWeightsAcrossBwAu);
        }
        if (list.size() == 0){
            log.info("未下载到数据");
            return;
        }
        //填充到数据库中
        int rows = 0;
        for (int i = 0; i < totalConsensusWeightsAcrossBwAuList.size() / 400 + 1; i++) {
            int end = (i + 1) * 400;
            if (end >= list.size()) {
                end = list.size();
            }
            List<TotalConsensusWeightsAcrossBwAu> sublist = totalConsensusWeightsAcrossBwAuList.subList(i * 400, end);
            if (sublist.size() == 0) {
                break;
            }
            rows = rows + totalConsensusWeightsAcrossBwAuDao.insertTotalCW(sublist);
        }
        if (rows < 0){
            log.error("本次写入失败");
        }else {
            log.info("写入了{}条数据", rows);
        }
    }


}
