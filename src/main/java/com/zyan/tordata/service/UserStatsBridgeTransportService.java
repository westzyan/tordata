package com.zyan.tordata.service;

import com.zyan.tordata.dao.UserStatsBridgeTransportDao;
import com.zyan.tordata.domain.UserStatsBridgeTransport;
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
public class UserStatsBridgeTransportService {
    @Autowired
    UserStatsBridgeTransportDao userStatsBridgeTransportDao;

    public List<UserStatsBridgeTransport> listAllUser() {
        return userStatsBridgeTransportDao.listAllUser();
    }

    /**
     * 将制定时间段内的所有数据进行统计，例如2018-03-02到2019-03-02日期中每一种混淆插件的个数
     * 这里为了将日期也一并放里面，所以使用了<List<String>，本来应该为Integer的，但是由于前端不懂，所以直接后端处理
     * @param start
     * @param end
     * @return
     */
    public List<List<String>> listTransportUser(String start, String end) {
        List<List<String>> result = new ArrayList<List<String>>();
        String preDate;
        List<UserStatsBridgeTransport> list = userStatsBridgeTransportDao.listBridgeTransportByDate(start, end);
        if (list.size() > 0) {
            preDate = DateTimeUtil.dateToStr(list.get(0).getDate());
        } else {
            return result;
        }
        List<String> dateList = new ArrayList<String>();
        List<String> allPtList = new ArrayList<String>();
        List<String> obfs2List = new ArrayList<String>();
        List<String> obfs3List = new ArrayList<String>();
        List<String> obfs4List = new ArrayList<String>();
        List<String> websocketList = new ArrayList<String>();
        List<String> fteList = new ArrayList<String>();
        List<String> meekList = new ArrayList<String>();
        List<String> scramblesuitList = new ArrayList<String>();
        List<String> snowflakeList = new ArrayList<String>();
        List<String> defaultOPList = new ArrayList<String>();
        String allPt = "0";
        String obfs2 = "0";
        String obfs3 = "0";
        String obfs4 = "0";
        String websocket = "0";
        String fte = "0";
        String meek = "0";
        String scramblesuit = "0";
        String snowflake = "0";
        String defaultOP = "0";
        for (UserStatsBridgeTransport userStatsBridgeTransport : list) {
            Date curDate = userStatsBridgeTransport.getDate();
            String dateStr = DateTimeUtil.dateToStr(curDate);
            if (preDate.equals(dateStr)) {
                switch (userStatsBridgeTransport.getTransport()) {
                    case "!<OR>":
                        allPt = String.valueOf(userStatsBridgeTransport.getUsers());
                        break;
                    case "<OR>":
                        defaultOP = String.valueOf(userStatsBridgeTransport.getUsers());
                        break;
                    case "obfs2":
                        obfs2 = String.valueOf(userStatsBridgeTransport.getUsers());
                        break;
                    case "obfs3":
                        obfs3 = String.valueOf(userStatsBridgeTransport.getUsers());
                        break;
                    case "obfs4":
                        obfs4 = String.valueOf(userStatsBridgeTransport.getUsers());
                        break;
                    case "websocket":
                        websocket = String.valueOf(userStatsBridgeTransport.getUsers());
                        break;
                    case "fte":
                        fte = String.valueOf(userStatsBridgeTransport.getUsers());
                        break;
                    case "meek":
                        meek = String.valueOf(userStatsBridgeTransport.getUsers());
                        break;
                    case "scramblesuit":
                        scramblesuit = String.valueOf(userStatsBridgeTransport.getUsers());
                        break;
                    case "snowflake":
                        snowflake = String.valueOf(userStatsBridgeTransport.getUsers());
                        break;
                }
            } else {
                dateList.add(preDate);
                allPtList.add(allPt);
                defaultOPList.add(defaultOP);
                obfs2List.add(obfs2);
                obfs3List.add(obfs3);
                obfs4List.add(obfs4);
                websocketList.add(websocket);
                fteList.add(fte);
                meekList.add(meek);
                scramblesuitList.add(scramblesuit);
                snowflakeList.add(snowflake);

                allPt = "0";
                obfs2 = "0";
                obfs3 = "0";
                obfs4 = "0";
                websocket = "0";
                fte = "0";
                meek = "0";
                scramblesuit = "0";
                snowflake = "0";
                defaultOP = "0";

                preDate = dateStr;

                switch (userStatsBridgeTransport.getTransport()) {
                    case "!<OR>":
                        allPt = String.valueOf(userStatsBridgeTransport.getUsers());
                        break;
                    case "<OR>":
                        defaultOP = String.valueOf(userStatsBridgeTransport.getUsers());
                        break;
                    case "obfs2":
                        obfs2 = String.valueOf(userStatsBridgeTransport.getUsers());
                        break;
                    case "obfs3":
                        obfs3 = String.valueOf(userStatsBridgeTransport.getUsers());
                        break;
                    case "obfs4":
                        obfs4 = String.valueOf(userStatsBridgeTransport.getUsers());
                        break;
                    case "websocket":
                        websocket = String.valueOf(userStatsBridgeTransport.getUsers());
                        break;
                    case "fte":
                        fte = String.valueOf(userStatsBridgeTransport.getUsers());
                        break;
                    case "meek":
                        meek = String.valueOf(userStatsBridgeTransport.getUsers());
                        break;
                    case "scramblesuit":
                        scramblesuit = String.valueOf(userStatsBridgeTransport.getUsers());
                        break;
                    case "snowflake":
                        snowflake = String.valueOf(userStatsBridgeTransport.getUsers());
                        break;
                }

            }
        }
        dateList.add(preDate);
        allPtList.add(allPt);
        obfs2List.add(obfs2);
        obfs3List.add(obfs3);
        obfs4List.add(obfs4);
        websocketList.add(websocket);
        fteList.add(fte);
        meekList.add(meek);
        scramblesuitList.add(scramblesuit);
        snowflakeList.add(snowflake);
        defaultOPList.add(defaultOP);

        result.add(dateList);
        result.add(allPtList);
        result.add(obfs2List);
        result.add(obfs3List);
        result.add(obfs4List);
        result.add(websocketList);
        result.add(fteList);
        result.add(meekList);
        result.add(scramblesuitList);
        result.add(snowflakeList);
        result.add(defaultOPList);

        return result;
    }



    /**
     * 填充后续的数据
     * 查询最新的日期，然后startTime为最新日期的后一天，endTime为当天
     * @return 返回填充的数据条数
     */
    //TODO 需要设置定时任务
    public int fillUserStatsBridgeTransport() throws KeyManagementException, NoSuchAlgorithmException {
        Date lastDate = userStatsBridgeTransportDao.getLastDate();
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
        String url = Const.USERS_BRIDGE_TRANSPORT + "?start=" + startDateString + "&end=" + endDateString;
        System.out.println(url);
        //下载剩余的csv数据
        List<String> list = DownloadUtil.downloadCSV(url);
        List<UserStatsBridgeTransport> usersList = new ArrayList<UserStatsBridgeTransport>();
        for (String s : list) {
            String[] fields = s.split(",");
            UserStatsBridgeTransport userStatsBridgeTransport = new UserStatsBridgeTransport();
            userStatsBridgeTransport.setDate(DateTimeUtil.strToDate(fields[0]));
            userStatsBridgeTransport.setTransport(fields[1]);
            userStatsBridgeTransport.setUsers(Integer.parseInt(fields[2]));
            userStatsBridgeTransport.setFrac(Integer.parseInt(fields[3]));
            usersList.add(userStatsBridgeTransport);
        }
        System.out.println(usersList.size());
        //填充到数据库中
        int fillNumber = userStatsBridgeTransportDao.insertUsers(usersList);
        log.info("写入了{}条数据",fillNumber);

        return fillNumber;
    }


}
