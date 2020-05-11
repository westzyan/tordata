package com.zyan.tordata.service;

import com.zyan.tordata.dao.AdvBwDistributionDao;
import com.zyan.tordata.domain.AdvBwDistribution;
import com.zyan.tordata.result.Const;
import com.zyan.tordata.util.DateTimeUtil;
import com.zyan.tordata.util.DownloadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Service
@Slf4j
public class AdvBwDistributionService {
    @Autowired
    AdvBwDistributionDao advBwDistributionDao;

    public List<AdvBwDistribution> listAdvBwDis(String start, String end) {
        return advBwDistributionDao.listAdvBwDis(start, end);
    }

    public List<List<List<String>>> listAdvBwDisDefault(String start, String end) {
        List<List<List<String>>> result = new ArrayList<>();
        String preDate;
        List<AdvBwDistribution> list = advBwDistributionDao.listAdvBwDis(start, end);
        if (list.size() > 0) {
            preDate = DateTimeUtil.dateToStr(list.get(0).getDate());
        } else {
            return result;
        }
        List<List<String>> dateAllList = new ArrayList<List<String>>();
        List<List<String>> allDateList1 = new ArrayList<List<String>>();//100
        List<List<String>> allDateList2 = new ArrayList<List<String>>();//99
        List<List<String>> allDateList3 = new ArrayList<List<String>>();//98
        List<List<String>> allDateList4 = new ArrayList<List<String>>();//97
        List<List<String>> allDateList5 = new ArrayList<List<String>>();//95
        List<List<String>> allDateList6 = new ArrayList<List<String>>();//91
        List<List<String>> allDateList7 = new ArrayList<List<String>>();//90
        List<List<String>> allDateList8 = new ArrayList<List<String>>();//80
        List<List<String>> allDateList9 = new ArrayList<List<String>>();//75
        List<List<String>> allDateList10 = new ArrayList<List<String>>();//70
        List<List<String>> allDateList11 = new ArrayList<List<String>>();//60
        List<List<String>> allDateList12 = new ArrayList<List<String>>();//50
        List<List<String>> allDateList13 = new ArrayList<List<String>>();//40
        List<List<String>> allDateList14 = new ArrayList<List<String>>();//30
        List<List<String>> allDateList15 = new ArrayList<List<String>>();//25
        List<List<String>> allDateList16 = new ArrayList<List<String>>();//20
        List<List<String>> allDateList17 = new ArrayList<List<String>>();//10
        List<List<String>> allDateList18 = new ArrayList<List<String>>();//9
        List<List<String>> allDateList19 = new ArrayList<List<String>>();//5
        List<List<String>> allDateList20 = new ArrayList<List<String>>();//3
        List<List<String>> allDateList21 = new ArrayList<List<String>>();//2
        List<List<String>> allDateList22 = new ArrayList<List<String>>();//1
        List<List<String>> allDateList23 = new ArrayList<List<String>>();//0

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
        List<String> allList17 = new ArrayList<String>();//10
        List<String> allList18 = new ArrayList<String>();//9
        List<String> allList19 = new ArrayList<String>();//5
        List<String> allList20 = new ArrayList<String>();//3
        List<String> allList21 = new ArrayList<String>();//2
        List<String> allList22 = new ArrayList<String>();//1
        List<String> allList23 = new ArrayList<String>();//0

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
        List<String> exitList17 = new ArrayList<String>();//10
        List<String> exitList18 = new ArrayList<String>();//9
        List<String> exitList19 = new ArrayList<String>();//5
        List<String> exitList20 = new ArrayList<String>();//3
        List<String> exitList21 = new ArrayList<String>();//2
        List<String> exitList22 = new ArrayList<String>();//1
        List<String> exitList23 = new ArrayList<String>();//0

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
        String all17 = "0";
        String all18 = "0";
        String all19 = "0";
        String all20 = "0";
        String all21 = "0";
        String all22 = "0";
        String all23 = "0";

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
        String exit17 = "0";
        String exit18 = "0";
        String exit19 = "0";
        String exit20 = "0";
        String exit21 = "0";
        String exit22 = "0";
        String exit23 = "0";

        for (AdvBwDistribution advBwDistribution : list) {
            Date curDate = advBwDistribution.getDate();
            String dateStr = DateTimeUtil.dateToStr(curDate);
            if (preDate.equals(dateStr)) {
                switch (advBwDistribution.getP()) {
                    case 100:
                        all1 = String.valueOf(advBwDistribution.getAll());
                        exit1 = String.valueOf(advBwDistribution.getExits());
                        break;
                    case 99:
                        all2 = String.valueOf(advBwDistribution.getAll());
                        exit2 = String.valueOf(advBwDistribution.getExits());
                        break;
                    case 98:
                        all3 = String.valueOf(advBwDistribution.getAll());
                        exit3 = String.valueOf(advBwDistribution.getExits());
                        break;
                    case 97:
                        all4 = String.valueOf(advBwDistribution.getAll());
                        exit4 = String.valueOf(advBwDistribution.getExits());
                        break;
                    case 95:
                        all5 = String.valueOf(advBwDistribution.getAll());
                        exit5 = String.valueOf(advBwDistribution.getExits());
                        break;
                    case 91:
                        all6 = String.valueOf(advBwDistribution.getAll());
                        exit6 = String.valueOf(advBwDistribution.getExits());
                        break;
                    case 90:
                        all7 = String.valueOf(advBwDistribution.getAll());
                        exit7 = String.valueOf(advBwDistribution.getExits());
                        break;
                    case 80:
                        all8 = String.valueOf(advBwDistribution.getAll());
                        exit8 = String.valueOf(advBwDistribution.getExits());
                        break;
                    case 75:
                        all9 = String.valueOf(advBwDistribution.getAll());
                        exit9 = String.valueOf(advBwDistribution.getExits());
                        break;
                    case 70:
                        all10 = String.valueOf(advBwDistribution.getAll());
                        exit10 = String.valueOf(advBwDistribution.getExits());
                        break;
                    case 60:
                        all11 = String.valueOf(advBwDistribution.getAll());
                        exit11 = String.valueOf(advBwDistribution.getExits());
                        break;
                    case 50:
                        all12 = String.valueOf(advBwDistribution.getAll());
                        exit12 = String.valueOf(advBwDistribution.getExits());
                        break;
                    case 40:
                        all13 = String.valueOf(advBwDistribution.getAll());
                        exit13 = String.valueOf(advBwDistribution.getExits());
                        break;
                    case 30:
                        all14 = String.valueOf(advBwDistribution.getAll());
                        exit14 = String.valueOf(advBwDistribution.getExits());
                        break;
                    case 25:
                        all15 = String.valueOf(advBwDistribution.getAll());
                        exit15 = String.valueOf(advBwDistribution.getExits());
                        break;
                    case 20:
                        all16 = String.valueOf(advBwDistribution.getAll());
                        exit16 = String.valueOf(advBwDistribution.getExits());
                        break;
                    case 10:
                        all17 = String.valueOf(advBwDistribution.getAll());
                        exit17 = String.valueOf(advBwDistribution.getExits());
                        break;
                    case 9:
                        all18 = String.valueOf(advBwDistribution.getAll());
                        exit18 = String.valueOf(advBwDistribution.getExits());
                        break;
                    case 5:
                        all19 = String.valueOf(advBwDistribution.getAll());
                        exit19 = String.valueOf(advBwDistribution.getExits());
                        break;
                    case 3:
                        all20 = String.valueOf(advBwDistribution.getAll());
                        exit20 = String.valueOf(advBwDistribution.getExits());
                        break;
                    case 2:
                        all21 = String.valueOf(advBwDistribution.getAll());
                        exit21 = String.valueOf(advBwDistribution.getExits());
                        break;
                    case 1:
                        all22 = String.valueOf(advBwDistribution.getAll());
                        exit22 = String.valueOf(advBwDistribution.getExits());
                        break;
                    case 0:
                        all23 = String.valueOf(advBwDistribution.getAll());
                        exit23 = String.valueOf(advBwDistribution.getExits());
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
                allList17.add(all17);
                allList18.add(all18);
                allList19.add(all19);
                allList20.add(all20);
                allList21.add(all21);
                allList22.add(all22);
                allList23.add(all23);

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
                exitList17.add(exit17);
                exitList18.add(exit18);
                exitList19.add(exit19);
                exitList20.add(exit20);
                exitList21.add(exit21);
                exitList22.add(exit22);
                exitList23.add(exit23);

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
                all17 = "0";
                all18 = "0";
                all19 = "0";
                all20 = "0";
                all21 = "0";
                all22 = "0";
                all23 = "0";

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
                exit17 = "0";
                exit18 = "0";
                exit19 = "0";
                exit20 = "0";
                exit21 = "0";
                exit22 = "0";
                exit23 = "0";

                preDate = dateStr;

                switch (advBwDistribution.getP()) {
                    case 100:
                        all1 = String.valueOf(advBwDistribution.getAll());
                        exit1 = String.valueOf(advBwDistribution.getExits());
                        break;
                    case 99:
                        all2 = String.valueOf(advBwDistribution.getAll());
                        exit2 = String.valueOf(advBwDistribution.getExits());
                        break;
                    case 98:
                        all3 = String.valueOf(advBwDistribution.getAll());
                        exit3 = String.valueOf(advBwDistribution.getExits());
                        break;
                    case 97:
                        all4 = String.valueOf(advBwDistribution.getAll());
                        exit4 = String.valueOf(advBwDistribution.getExits());
                        break;
                    case 95:
                        all5 = String.valueOf(advBwDistribution.getAll());
                        exit5 = String.valueOf(advBwDistribution.getExits());
                        break;
                    case 91:
                        all6 = String.valueOf(advBwDistribution.getAll());
                        exit6 = String.valueOf(advBwDistribution.getExits());
                        break;
                    case 90:
                        all7 = String.valueOf(advBwDistribution.getAll());
                        exit7 = String.valueOf(advBwDistribution.getExits());
                        break;
                    case 80:
                        all8 = String.valueOf(advBwDistribution.getAll());
                        exit8 = String.valueOf(advBwDistribution.getExits());
                        break;
                    case 75:
                        all9 = String.valueOf(advBwDistribution.getAll());
                        exit9 = String.valueOf(advBwDistribution.getExits());
                        break;
                    case 70:
                        all10 = String.valueOf(advBwDistribution.getAll());
                        exit10 = String.valueOf(advBwDistribution.getExits());
                        break;
                    case 60:
                        all11 = String.valueOf(advBwDistribution.getAll());
                        exit11 = String.valueOf(advBwDistribution.getExits());
                        break;
                    case 50:
                        all12 = String.valueOf(advBwDistribution.getAll());
                        exit12 = String.valueOf(advBwDistribution.getExits());
                        break;
                    case 40:
                        all13 = String.valueOf(advBwDistribution.getAll());
                        exit13 = String.valueOf(advBwDistribution.getExits());
                        break;
                    case 30:
                        all14 = String.valueOf(advBwDistribution.getAll());
                        exit14 = String.valueOf(advBwDistribution.getExits());
                        break;
                    case 25:
                        all15 = String.valueOf(advBwDistribution.getAll());
                        exit15 = String.valueOf(advBwDistribution.getExits());
                        break;
                    case 20:
                        all16 = String.valueOf(advBwDistribution.getAll());
                        exit16 = String.valueOf(advBwDistribution.getExits());
                        break;
                    case 10:
                        all17 = String.valueOf(advBwDistribution.getAll());
                        exit17 = String.valueOf(advBwDistribution.getExits());
                        break;
                    case 9:
                        all18 = String.valueOf(advBwDistribution.getAll());
                        exit18 = String.valueOf(advBwDistribution.getExits());
                        break;
                    case 5:
                        all19 = String.valueOf(advBwDistribution.getAll());
                        exit19 = String.valueOf(advBwDistribution.getExits());
                        break;
                    case 3:
                        all20 = String.valueOf(advBwDistribution.getAll());
                        exit20 = String.valueOf(advBwDistribution.getExits());
                        break;
                    case 2:
                        all21 = String.valueOf(advBwDistribution.getAll());
                        exit21 = String.valueOf(advBwDistribution.getExits());
                        break;
                    case 1:
                        all22 = String.valueOf(advBwDistribution.getAll());
                        exit22 = String.valueOf(advBwDistribution.getExits());
                        break;
                    case 0:
                        all23 = String.valueOf(advBwDistribution.getAll());
                        exit23 = String.valueOf(advBwDistribution.getExits());
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
        allList17.add(all17);
        allList18.add(all18);
        allList19.add(all19);
        allList20.add(all20);
        allList21.add(all21);
        allList22.add(all22);
        allList23.add(all23);

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
        exitList17.add(exit17);
        exitList18.add(exit18);
        exitList19.add(exit19);
        exitList20.add(exit20);
        exitList21.add(exit21);
        exitList22.add(exit22);
        exitList23.add(exit23);

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

        allDateList17.add(allList17);
        allDateList17.add(exitList17);
        result.add(allDateList17);

        allDateList18.add(allList18);
        allDateList18.add(exitList18);
        result.add(allDateList18);

        allDateList19.add(allList19);
        allDateList19.add(exitList19);
        result.add(allDateList19);

        allDateList20.add(allList20);
        allDateList20.add(exitList20);
        result.add(allDateList20);

        allDateList21.add(allList21);
        allDateList21.add(exitList21);
        result.add(allDateList21);

        allDateList22.add(allList22);
        allDateList22.add(exitList22);
        result.add(allDateList22);

        allDateList23.add(allList23);
        allDateList23.add(exitList23);
        result.add(allDateList23);

        return result;
    }

    /**
     * 填充后续的数据
     * 查询最新的日期，然后startTime为最新日期的后一天，endTime为当天
     *
     * @return 返回填充的数据条数
     */
    //TODO 需要设置定时任务
    public int fillAdvBwDis() throws KeyManagementException, NoSuchAlgorithmException {
        Date lastDate = advBwDistributionDao.getLastDate();
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
        //https://metrics.torproject.org/bandwidth.csv?start=2020-02-05&end=2020-05-05
        String url = Const.ADV_BW_DISTRIBUTION + "?start=" + startDateString + "&end=" + endDateString;
        System.out.println(url);
        //下载剩余的csv数据
        List<String> list = DownloadUtil.downloadCSV(url);
        List<AdvBwDistribution> advBwDistributionList = new ArrayList<AdvBwDistribution>();
        for (String s : list) {
            String[] fields = s.split(",");
            AdvBwDistribution advBwDistribution = new AdvBwDistribution();
            advBwDistribution.setDate(DateTimeUtil.strToDate(fields[0]));
            advBwDistribution.setP(Integer.parseInt(fields[1]));
            //数据中存在XXXeXXX
            BigDecimal bd = new BigDecimal(fields[2]);
            String str = bd.toPlainString();
            advBwDistribution.setAll(str);
//            advBwDistribution.setAll(fields[2]);

            BigDecimal bd1 = new BigDecimal(fields[3]);
            String str1 = bd1.toPlainString();
            advBwDistribution.setExits(str1);
//            advBwDistribution.setExits(fields[3]);


            advBwDistributionList.add(advBwDistribution);
        }
        log.info(String.valueOf(advBwDistributionList.size()));
        //填充到数据库中
        int rows = 0;
        for (int i = 0; i < advBwDistributionList.size() / 400 + 1; i++) {
            int end = (i + 1) * 400;
            if (end >= list.size()) {
                end = list.size();
            }
            List<AdvBwDistribution> sublist = advBwDistributionList.subList(i * 400, end);
            if (sublist.size() == 0) {
                break;
            }
            rows = rows + advBwDistributionDao.insertAdvBwDis(sublist);
        }
        log.info("写入了{}条数据", rows);

        return rows;
    }


}
