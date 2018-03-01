package com.zuicoding.framework.mybatis.generator.configuration;

import java.util.List;

import javax.swing.JComponent;

import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import com.zuicoding.framework.mybatis.generator.model.MybatisGeneratorSetting;
import com.zuicoding.framework.mybatis.generator.ui.MybatisGeneratorSettingsComponent;

/**
 * Created by <a href="mailto:stephen.lin@gmail.com">Stephen.lin</a> on 2018/2/24.
 * <p>
 * <p>
 * </p>
 */
public class MybatisGeneratorConfigurable implements SearchableConfigurable {

    private MybatisGeneratorSettingsComponent component;

    @NotNull
    @Override
    public String getId() {
        return "Mybatis-Generator-Configuarable";
    }

    @Nls
    @Override
    public String getDisplayName() {
        return "Mybatis Generator 配置";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        this.component = MybatisGeneratorSettingsComponent
                .getInstance();
        return component.getContainer();
    }

    @Override
    public boolean isModified() {
        List<MybatisGeneratorSetting> oldSettings = component.getOldSettings();
        MybatisGeneratorSettingsComponent.SettingWrapper wrapper = component.getState();
        if(oldSettings == null && wrapper == null) {
            return false;
        }

        if (oldSettings == null  || wrapper == null || wrapper.getSettings() == null) {
            return true;
        }
        List<MybatisGeneratorSetting> newSettings = wrapper.getSettings();
        boolean result = oldSettings.size() == wrapper.getSettings().size();
        if (result) {
            for (int i = 0,size = oldSettings.size(); i < size; i++) {
                MybatisGeneratorSetting oldSetting = oldSettings.get(i);
                MybatisGeneratorSetting newSetting = newSettings.get(i);
                if (!oldSetting.equals(newSetting)){
                    return true;
                }
            }
        }
        return !result;
    }

    @Override
    public void apply() throws ConfigurationException {
        MybatisGeneratorSettingsComponent.SettingWrapper wrapper = component.getState();
        if (wrapper == null) {
            component.setOldSettings(null);
            return;
        }
        component.setOldSettings(wrapper.getSettings());


    }
}
