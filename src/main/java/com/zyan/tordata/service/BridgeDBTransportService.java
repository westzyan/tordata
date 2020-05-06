package com.zyan.tordata.service;

import com.zyan.tordata.dao.BridgeDBTransportDao;
import com.zyan.tordata.dao.UserStatsBridgeVersionDao;
import com.zyan.tordata.domain.BridgeDBTransport;
import com.zyan.tordata.domain.UserStatsBridgeTransport;
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
     * 将制定时间段内的所有数据进行统计，例如2018-03-02到2019-03-02日期中每一种混淆插件的个数
     * 返回 不同类型pt的个数,这里只有fte,obfs2,obfs3,obfs4,scramblesuit,vanilla
     * @param start 开始
     * @param end 结束
     * @return list
     */
    public List<List<String>> listBridgeDBPTByDate(String start, String end) {
        List<List<String>> result = new ArrayList<List<String>>();
        String preDate;
        List<BridgeDBTransport> list = bridgeDBTransportDao.listUserByStartAndEnd(start, end);
        if (list.size() > 0) {
            preDate = DateTimeUtil.dateToStr(list.get(0).getDate());
        } else {
            return result;
        }

        List<String> dateList = new ArrayList<String>();
        List<String> obfs2List = new ArrayList<String>();
        List<String> obfs3List = new ArrayList<String>();
        List<String> obfs4List = new ArrayList<String>();
        List<String> fteList = new ArrayList<String>();
        List<String> scramblesuitList = new ArrayList<String>();
        List<String> vanillaList = new ArrayList<String>();
        String obfs2 = "0";
        String obfs3 = "0";
        String obfs4 = "0";
        String fte = "0";
        String scramblesuit = "0";
        String vanilla = "0";
        for (BridgeDBTransport bridgeDBTransport : list) {
            Date curDate = bridgeDBTransport.getDate();
            String dateStr = DateTimeUtil.dateToStr(curDate);
            if (preDate.equals(dateStr)) {
                switch (bridgeDBTransport.getTransport()) {
                    case "vanilla":
                        vanilla = String.valueOf(bridgeDBTransport.getRequests());
                        break;
                    case "obfs2":
                        obfs2 = String.valueOf(bridgeDBTransport.getRequests());
                        break;
                    case "obfs3":
                        obfs3 = String.valueOf(bridgeDBTransport.getRequests());
                        break;
                    case "obfs4":
                        obfs4 = String.valueOf(bridgeDBTransport.getRequests());
                        break;
                    case "fte":
                        fte = String.valueOf(bridgeDBTransport.getRequests());
                        break;
                    case "scramblesuit":
                        scramblesuit = String.valueOf(bridgeDBTransport.getRequests());
                        break;
                }
            } else {
                dateList.add(preDate);
                vanillaList.add(vanilla);
                obfs2List.add(obfs2);
                obfs3List.add(obfs3);
                obfs4List.add(obfs4);
                fteList.add(fte);
                scramblesuitList.add(scramblesuit);

                obfs2 = "0";
                obfs3 = "0";
                obfs4 = "0";
                fte = "0";
                scramblesuit = "0";
                vanilla = "0";

                preDate = dateStr;

                switch (bridgeDBTransport.getTransport()) {
                    case "vanilla":
                        vanilla = String.valueOf(bridgeDBTransport.getRequests());
                        break;
                    case "obfs2":
                        obfs2 = String.valueOf(bridgeDBTransport.getRequests());
                        break;
                    case "obfs3":
                        obfs3 = String.valueOf(bridgeDBTransport.getRequests());
                        break;
                    case "obfs4":
                        obfs4 = String.valueOf(bridgeDBTransport.getRequests());
                        break;
                    case "fte":
                        fte = String.valueOf(bridgeDBTransport.getRequests());
                        break;
                    case "scramblesuit":
                        scramblesuit = String.valueOf(bridgeDBTransport.getRequests());
                        break;
                }
            }
        }
        dateList.add(preDate);
        obfs2List.add(obfs2);
        obfs3List.add(obfs3);
        obfs4List.add(obfs4);
        fteList.add(fte);
        scramblesuitList.add(scramblesuit);
        vanillaList.add(vanilla);


        result.add(dateList);
        result.add(fteList);
        result.add(obfs2List);
        result.add(obfs3List);
        result.add(obfs4List);
        result.add(scramblesuitList);
        result.add(vanillaList);

        return result;
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
