
package com.zuicoding.framework.mybatis.generator.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by <a href="mailto:stephen.lin@gmail.com">Stephen.lin</a> on 2018/2/9.
 * <p>
 * <p>
 * </p>
 */
public class StringTools {
    private static Map<String,String> urlMap = new HashMap<>();

    static {
        urlMap.put("com.mysql.jdbc.Driver","jdbc:mysql://%s:%s");
        urlMap.put("com.microsoft.sqlserver.jdbc.SQLServerDriver",
                "jdbc:sqlserver://%s:%s");
        urlMap.put("oracle.jdbc.driver.OralceDriver",
                "jdbc:oracle:thin:@%s:%s");
    }
    private StringTools() {}

    public static boolean isBank(String str){

        return str == null || str.trim().isEmpty();
    }

    public static String getUrl(String driverClass,String address,int port){
       return String.format(urlMap.getOrDefault(driverClass, ""), address,port);
    }


}
