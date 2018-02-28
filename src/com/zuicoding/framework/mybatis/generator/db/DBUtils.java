
package com.zuicoding.framework.mybatis.generator.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.zuicoding.framework.mybatis.generator.model.DataSourceInfo;
import com.zuicoding.framework.mybatis.generator.model.NodeEnum;
import com.zuicoding.framework.mybatis.generator.model.TreeNode;

/**
 * Created by <a href="mailto:Stephen.lin@gmail.com">Stephen.lin</a> on 2018/2/9.
 * <p>
 * <p>
 * </p>
 */
public class DBUtils {
    private static DBUtils INSTANCE;
    private DynamicDBDriver dbDriver;
    private static final String DATABASE_SQL = "SHOW DATABASES";
    private static final String TABLE_SQL = "SHOW TABLES";
    private Connection connection;
    private DataSourceInfo dataSourceInfo;

    private DBUtils(DynamicDBDriver dbDriver) {
        this.dbDriver = dbDriver;
        this.dataSourceInfo = dbDriver.getDataSourceInfo();

    }

    private void open() throws Exception {
        if (connection == null || connection.isClosed()){
            this.connection = dbDriver.getConnection();
        }
    }
    private void close() throws Exception {
        if (this.connection != null && !this.connection.isClosed()){
            this.connection.close();
        }
    }
    public List<TreeNode> getDatabases() {
        try {

            this.open();

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(DATABASE_SQL);
            List<TreeNode> databases = new ArrayList<>();
            TreeNode node = null;
            while (resultSet.next()){
                String dbname = resultSet.getString(1);
                node = new TreeNode(dbname);
                node.setIcon("images/database.png");
                node.setNodeType(NodeEnum.DATABASE);
                node.setDataSourceInfo(dataSourceInfo);
                databases.add(node);
            }
            return databases;
        }catch (Exception e){
            throw new RuntimeException(e);
        }


    }

    public List<TreeNode> getTables(String dbname){
        try {
            this.open();
            connection.setCatalog(dbname);
            Statement statement = connection.createStatement();
            //connection.setSchema(dbname);
            ResultSet resultSet = statement.executeQuery(TABLE_SQL);
            List<TreeNode> tables = new ArrayList<>();
            TreeNode table = null;
            while (resultSet.next()){
                String tableName = resultSet.getString(1);
                table = new TreeNode(tableName);
                table.setIcon("images/table.png");
                table.setNodeType(NodeEnum.TABLE);
                table.setDataSourceInfo(dataSourceInfo);
                tables.add(table);
            }
            return tables;
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }
    public static DBUtils getInstance(DynamicDBDriver dbDriver) {
        if (INSTANCE == null) {
            synchronized(DBUtils.class) {

                if (INSTANCE == null) {

                    INSTANCE = new DBUtils(dbDriver) ;
                }
            }
        }
        return INSTANCE;
    }
}
