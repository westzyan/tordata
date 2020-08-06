package com.zyan.tordata.util;

import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {

    private static Properties props;

    static {
        try {
            InputStream in = DBUtil.class.getClassLoader().getResourceAsStream("application.properties");
            props = new Properties();
            props.load(in);
            in.close();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    public static String getHttpProxy(){
        return props.getProperty("http_proxy");
    }
    public static int getHttpProxyPort(){
        return Integer.parseInt(props.getProperty("http_proxy_port"));
    }

}
