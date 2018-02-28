package com.zuicoding.framework.mybatis.generator.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by <a href="mailto:stephen.lin@gmail.com">Stephen.lin</a> on 2018/2/23.
 * <p>
 * <p>
 * </p>
 */
public class MybatisGeneratorConfig {

    private String driverJarPath;
    private String defaultModelType;
    private String targetRuntime;
    private String javaFileEncoding = "UTF-8";
    private DataSourceInfo dataSourceInfo;
    private String javaModelTargetPackage;
    private String javaModelTargetProjectPath;
    private boolean constructorBased;
    private boolean trimStrings;
    private String sqlMapTargetPackage;
    private String sqlMapTargetProjectPath;
    private String javaClientTargetPackage;
    private String javaClientType;
    private String javaClientTargetProjectPath;
    private List<String> tables = new ArrayList<>();
    private boolean enableCount;
    private boolean enableUpdate;
    private boolean enableDelete;
    private boolean enableSelect;
    private boolean selectQueryId;
    private  boolean useActualColumnNames;

    public String getDriverJarPath() {
        return driverJarPath;
    }

    public void setDriverJarPath(String driverJarPath) {
        this.driverJarPath = driverJarPath;
    }

    public String getDefaultModelType() {
        return defaultModelType;
    }

    public void setDefaultModelType(String defaultModelType) {
        this.defaultModelType = defaultModelType;
    }

    public String getTargetRuntime() {
        return targetRuntime;
    }

    public void setTargetRuntime(String targetRuntime) {
        this.targetRuntime = targetRuntime;
    }

    public String getJavaFileEncoding() {
        return javaFileEncoding;
    }

    public void setJavaFileEncoding(String javaFileEncoding) {
        this.javaFileEncoding = javaFileEncoding;
    }

    public DataSourceInfo getDataSourceInfo() {
        return dataSourceInfo;
    }

    public void setDataSourceInfo(DataSourceInfo dataSourceInfo) {
        this.dataSourceInfo = dataSourceInfo;
    }

    public String getJavaModelTargetPackage() {
        return javaModelTargetPackage;
    }

    public void setJavaModelTargetPackage(String javaModelTargetPackage) {
        this.javaModelTargetPackage = javaModelTargetPackage;
    }

    public String getJavaModelTargetProjectPath() {
        return javaModelTargetProjectPath;
    }

    public void setJavaModelTargetProjectPath(String javaModelTargetProjectPath) {
        this.javaModelTargetProjectPath = javaModelTargetProjectPath;
    }

    public boolean isConstructorBased() {
        return constructorBased;
    }

    public void setConstructorBased(boolean constructorBased) {
        this.constructorBased = constructorBased;
    }

    public boolean isTrimStrings() {
        return trimStrings;
    }

    public void setTrimStrings(boolean trimStrings) {
        this.trimStrings = trimStrings;
    }

    public String getSqlMapTargetPackage() {
        return sqlMapTargetPackage;
    }

    public void setSqlMapTargetPackage(String sqlMapTargetPackage) {
        this.sqlMapTargetPackage = sqlMapTargetPackage;
    }

    public String getSqlMapTargetProjectPath() {
        return sqlMapTargetProjectPath;
    }

    public void setSqlMapTargetProjectPath(String sqlMapTargetProjectPath) {
        this.sqlMapTargetProjectPath = sqlMapTargetProjectPath;
    }

    public String getJavaClientTargetPackage() {
        return javaClientTargetPackage;
    }

    public void setJavaClientTargetPackage(String javaClientTargetPackage) {
        this.javaClientTargetPackage = javaClientTargetPackage;
    }

    public String getJavaClientType() {
        return javaClientType;
    }

    public void setJavaClientType(String javaClientType) {
        this.javaClientType = javaClientType;
    }

    public String getJavaClientTargetProjectPath() {
        return javaClientTargetProjectPath;
    }

    public void setJavaClientTargetProjectPath(String javaClientTargetProjectPath) {
        this.javaClientTargetProjectPath = javaClientTargetProjectPath;
    }

    public List<String> getTables() {
        return tables;
    }

    public void addTable(String table) {
        if (tables == null) {
            tables = new ArrayList<>();
        }
        tables.add(table);
    }

    public void setTables(List<String> tables) {
        this.tables = tables;
    }

    public boolean isEnableCount() {
        return enableCount;
    }

    public void setEnableCount(boolean enableCount) {
        this.enableCount = enableCount;
    }

    public boolean isEnableUpdate() {
        return enableUpdate;
    }

    public void setEnableUpdate(boolean enableUpdate) {
        this.enableUpdate = enableUpdate;
    }

    public boolean isEnableDelete() {
        return enableDelete;
    }

    public void setEnableDelete(boolean enableDelete) {
        this.enableDelete = enableDelete;
    }

    public boolean isEnableSelect() {
        return enableSelect;
    }

    public void setEnableSelect(boolean enableSelect) {
        this.enableSelect = enableSelect;
    }

    public boolean isSelectQueryId() {
        return selectQueryId;
    }

    public void setSelectQueryId(boolean selectQueryId) {
        this.selectQueryId = selectQueryId;
    }

    public boolean isUseActualColumnNames() {
        return useActualColumnNames;
    }

    public void setUseActualColumnNames(boolean useActualColumnNames) {
        this.useActualColumnNames = useActualColumnNames;
    }
}
