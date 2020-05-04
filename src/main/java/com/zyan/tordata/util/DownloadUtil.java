package com.zyan.tordata.util;


import javax.net.ssl.*;
import java.io.*;
import java.net.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;


public class DownloadUtil{

    public static final String TOR_DATA_URL = "https://metrics.torproject.org/";

    // https://metrics.torproject.org/userstats-bridge-country.csv?start=2020-01-30&end=2020-04-29&country=all

    /**
     * 下载csv,返回csv内容
     * @param urlAddress 部分URL地址，例如 userstats-bridge-country.csv?start=2020-01-30&end=2020-04-29&country=all
     * @return csv内容
     * @throws NoSuchAlgorithmException ssl算法
     * @throws KeyManagementException  证书
     */
    public static List<String> downloadCSV(String urlAddress) throws NoSuchAlgorithmException, KeyManagementException {

        List<String> list = new ArrayList<String>();

        //  直接通过主机认证
        HostnameVerifier hv = new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        };
        //  配置认证管理器
        javax.net.ssl.TrustManager[] trustAllCerts = {new TrustAllTrustManager()};
        SSLContext sc = SSLContext.getInstance("SSL");
        SSLSessionContext sslsc = sc.getServerSessionContext();
        sslsc.setSessionTimeout(0);
        sc.init(null, trustAllCerts, null);
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        //  激活主机认证
        HttpsURLConnection.setDefaultHostnameVerifier(hv);

        String address = TOR_DATA_URL + urlAddress;
        System.out.println(address);
        URL url = null;
        InputStream is = null;
        InputStreamReader isr = null;
        HttpURLConnection conn = null;
        try {
            url = new URL(address);
            String host = "127.0.0.1";
            int port = 1080;
            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(host, port));
            conn = (HttpURLConnection)url.openConnection(proxy);
            // 读取服务器端返回的内容
            is = conn.getInputStream();
            //读取内容
            isr = new InputStreamReader(is,"utf-8");
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            int count = 0;
            while ((line = br.readLine()) != null) {
                count++;
                if (count > 6){
                    list.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (isr != null){
                    isr.close();
                }
                if (is != null){
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }


    public static void main(String[] args) throws KeyManagementException, NoSuchAlgorithmException {
        String filename = "userstats-bridge-country.csv";
        String start = "2018-1-1";
        String end = "2020-04-25";
//        DownloadUtil.downloadCSV("userstats-bridge-country.csv?start=2020-01-30&end=2020-04-29&events=off");
        DownloadUtil.downloadCSV("userstats-relay-country.csv?start=2020-01-30&end=2020-04-29&events=off");
//        String url = "# URL: https://metrics.torproject.org/userstats-relay-country.csv?start=2020-01-30&end=2020-04-29&events=off";
//        String out = DownloadUtil.httpsRequest(url,"","");
//        System.out.println(out);
    }
}
