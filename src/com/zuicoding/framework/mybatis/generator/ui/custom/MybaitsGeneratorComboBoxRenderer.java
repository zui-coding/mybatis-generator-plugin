
package com.zuicoding.framework.mybatis.generator.ui.custom;

import java.awt.Component;

import javax.swing.JList;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import com.zuicoding.framework.mybatis.generator.model.MybatisGeneratorSetting;

/**
 * Created by <a href="mailto:stephen.lin@gmail.com">Stephen.lin</a> on 2018/2/26.
 * <p>
 * <p>
 * </p>
 */
public class MybaitsGeneratorComboBoxRenderer extends BasicComboBoxRenderer {

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
                                                  boolean cellHasFocus) {

        if (value != null){
            MybatisGeneratorSetting setting = (MybatisGeneratorSetting) value;
            setText(setting.getName());
        }
        return this;
    }
}
