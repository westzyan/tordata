package com.zyan.tordata.service;

import com.zyan.tordata.dao.BridgesByIpVersionDao;
import com.zyan.tordata.domain.AdvBwByIpVersion;
import com.zyan.tordata.domain.BridgesByIpVersion;
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
public class BridgesByIpVersionService {
    @Autowired
    BridgesByIpVersionDao bridgesByIpVersionDao;

    public List<BridgesByIpVersion> listBridgesIpVersion(String start, String end) {
        return bridgesByIpVersionDao.listBridgesIpVersion(start,end);
    }

    /**
     * 填充后续的数据
     * 查询最新的日期，然后startTime为最新日期的后一天，endTime为当天
     *
     * @return 返回填充的数据条数
     */
    //TODO 需要设置定时任务
    public int fillBridgesIpVersion() throws KeyManagementException, NoSuchAlgorithmException {
        Date lastDate = bridgesByIpVersionDao.getLastDate();
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
        //https://metrics.torproject.org/bridges-ipv6.csv?start=2020-02-02&end=2020-05-02
        String url = Const.BRIDGES_BY_IP_VERSION + "?start=" + startDateString + "&end=" + endDateString;
        System.out.println(url);
        //下载剩余的csv数据
        List<String> list = DownloadUtil.downloadCSV(url);
        List<BridgesByIpVersion> bridgesByIpVersionList = new ArrayList<BridgesByIpVersion>();
        for (String s : list) {
            String[] fields = s.split(",");
            BridgesByIpVersion bridgesByIpVersion = new BridgesByIpVersion();
            bridgesByIpVersion.setDate(DateTimeUtil.strToDate(fields[0]));
            bridgesByIpVersion.setTotal(Integer.parseInt(fields[1]));
            bridgesByIpVersion.setAnnounced(Integer.parseInt(fields[2]));

            bridgesByIpVersionList.add(bridgesByIpVersion);
        }
        log.info(String.valueOf(bridgesByIpVersionList.size()));
        //填充到数据库中
        int rows = 0;
        for (int i = 0; i < bridgesByIpVersionList.size() / 400 + 1; i++) {
            int end = (i + 1) * 400;
            if (end >= list.size()) {
                end = list.size();
            }
            List<BridgesByIpVersion> sublist = bridgesByIpVersionList.subList(i * 400, end);
            if (sublist.size() == 0){
                break;
            }
            rows = rows + bridgesByIpVersionDao.insertBridgesIpVersion(sublist);
        }
        log.info("写入了{}条数据", rows);

        return rows;
    }
}
