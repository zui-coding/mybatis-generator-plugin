
package com.zuicoding.framework.mybatis.generator.ui.custom;

import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;

import com.zuicoding.framework.mybatis.generator.model.MybatisGeneratorSetting;

/**
 * Created by <a href="mailto:stephen.lin@gmail.com">Stephen.lin</a> on 2018/2/24.
 * <p>
 * <p>
 * </p>
 */
public class MybatisGeneratorComboBoxModel implements ComboBoxModel<MybatisGeneratorSetting> {

    private List<MybatisGeneratorSetting> settings;
    private MybatisGeneratorSetting selectedSetting;
    public MybatisGeneratorComboBoxModel(
            List<MybatisGeneratorSetting> settings) {
        this.settings = settings;

    }

    @Override
    public void setSelectedItem(Object item) {
        this.selectedSetting = (MybatisGeneratorSetting) item;
    }

    @Override
    public Object getSelectedItem() {
        return selectedSetting;
    }

    @Override
    public int getSize() {
        return settings == null ? 0 : settings.size();
    }

    @Override
    public MybatisGeneratorSetting getElementAt(int index) {
        return settings.get(index);
    }

    @Override
    public void addListDataListener(ListDataListener l) {

    }

    @Override
    public void removeListDataListener(ListDataListener l) {

    }
}
