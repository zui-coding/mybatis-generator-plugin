package com.zuicoding.framework.mybatis.generator.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by <a href="mailto:stephen.lin@gmail.com">Stephen.lin</a> on 2018/2/24.
 * <p>
 * <p>
 * </p>
 */
public class MybatisGeneratorSetting implements Serializable{
    
    private String name;
    private String driverClass;
    private String driverPath;

    public MybatisGeneratorSetting() {
    }

    public MybatisGeneratorSetting(String name, String driverClass, String driverPath) {
        this.name = name;
        this.driverClass = driverClass;
        this.driverPath = driverPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getDriverPath() {
        return driverPath;
    }

    public void setDriverPath(String driverPath) {
        this.driverPath = driverPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MybatisGeneratorSetting setting = (MybatisGeneratorSetting) o;
        return Objects.equals(name, setting.name) &&
                Objects.equals(driverClass, setting.driverClass) &&
                Objects.equals(driverPath, setting.driverPath);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, driverClass, driverPath);
    }
}
