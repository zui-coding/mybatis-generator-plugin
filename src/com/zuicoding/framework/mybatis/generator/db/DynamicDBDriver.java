
package com.zuicoding.framework.mybatis.generator.db;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.zuicoding.framework.mybatis.generator.model.DataSourceInfo;
import com.zuicoding.framework.mybatis.generator.util.StringTools;

/**
 * Created by <a href="mailto:stephen.lin@gmail.com">Stephen.lin</a> on 2018/2/9.
 * <p>
 * <p>
 * 动态加载数据库
 * </p>
 */
public class DynamicDBDriver {

    private Map<String,Driver> map = new HashMap<>();

    /**
     * 数据库驱动jar包路径
     */
    private URLClassLoader driverClassLoader;
    private DataSourceInfo dataSourceInfo;
    private Driver driver;


    public void registe(DataSourceInfo dataSourceInfo) throws Exception {
        if (map.containsKey(dataSourceInfo.getDriverClass())){
            this.driver = map.get(dataSourceInfo.getDriverClass());
        }else {
            driverClassLoader = new URLClassLoader(new URL[]{new File(dataSourceInfo.getDriverPath())
                    .toURI().toURL()});
            Class driverClass = driverClassLoader.loadClass(dataSourceInfo.getDriverClass());
            this.driver = (Driver) driverClass.newInstance();
            DriverManager.registerDriver(driver);
            //Class.forName(dataSourceInfo.getDriverClass());
        }

        this.dataSourceInfo = dataSourceInfo;

    }

    public DataSourceInfo getDataSourceInfo() {
        return this.dataSourceInfo;
    }

    public Connection getConnection() throws Exception {
        Properties pro = new Properties();
        pro.setProperty("user",this.dataSourceInfo.getUserName());
        pro.setProperty("password",this.dataSourceInfo.getPassword());
       return this.driver.connect(this.dataSourceInfo.getUrl(),pro);
    }

    public void unregiste() throws SQLException {
        if (this.driver != null){
            DriverManager.deregisterDriver(this.driver);
        }

    }

    public static void main(String[] args) throws Exception {
        DataSourceInfo sourceInfo = new DataSourceInfo();
        sourceInfo.setHost("localhost");
        sourceInfo.setDriverClass("com.mysql.jdbc.Driver");
        sourceInfo.setPassword("root");
        sourceInfo.setUserName("root");
        sourceInfo.setPort(3306);
        sourceInfo.setDriverPath("/Users/XXX/tools/repository/mysql/mysql-connector-java/5.1.36/mysql-connector-java"
                + "-5.1.36.jar");
        sourceInfo.setUrl(StringTools.getUrl(sourceInfo.getDriverClass(),
                sourceInfo.getHost(),
                sourceInfo.getPort()));
        DynamicDBDriver dynamicDBDriver = new DynamicDBDriver();
        dynamicDBDriver.registe(sourceInfo);
        dynamicDBDriver.getConnection();
    }

}
