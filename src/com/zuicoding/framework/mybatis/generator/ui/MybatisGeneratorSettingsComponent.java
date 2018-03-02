package com.zuicoding.framework.mybatis.generator.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

import org.jetbrains.annotations.Nullable;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.zuicoding.framework.mybatis.generator.model.MybatisGeneratorSetting;
import com.zuicoding.framework.mybatis.generator.ui.custom.FileChooserCellEditor;
import com.zuicoding.framework.mybatis.generator.util.StringTools;

/**
 * Created by <a href="mailto:stephen.lin@gmail.com">Stephen.lin</a> on 2018/2/24.
 * <p>
 * <p>
 * </p>
 */
@State(name = "mybatisGeneratorSettings", storages = @Storage(id = "other", file =
        "$APP_CONFIG$/mybatis-generator-settings.xml"))
public class MybatisGeneratorSettingsComponent implements PersistentStateComponent<MybatisGeneratorSettingsComponent.SettingWrapper> {
    private JPanel container;
    private JTable driverTable;
    private JButton addButton;
    private JButton delButton;
    private JScrollPane scrollPane;

    private String[] header = new String[] {"name", "driverClass", "driverPath","url"};
    private DefaultTableModel tableModel;


    private List<MybatisGeneratorSetting> oldSettings;
    private TableCellEditor cellEditor;
    public static class SettingWrapper {

        private List<MybatisGeneratorSetting> settings;

        public List<MybatisGeneratorSetting> getSettings() {
            return settings;
        }

        public void setSettings(List<MybatisGeneratorSetting> settings) {
            this.settings = settings;
        }
    }
    public MybatisGeneratorSettingsComponent() {

        tableModel = new DefaultTableModel(null, header);

        driverTable.setModel(tableModel);

        driverTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.addRow(new Object[4]);
                driverTable.getColumnModel().getColumn(2).setCellEditor(cellEditor);

            }
        });

        delButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = driverTable.getSelectedRow();
                if (index >= 0 ) {
                    tableModel.removeRow(index);
                }

            }
        });


        //driverTable.setCellEditor(new FileChooserCellEditor(driverTable,false));
        scrollPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                TableCellEditor editor = driverTable.getCellEditor();
                if (editor == null) {
                    return;
                }

                if (!driverTable.isEditing()) {
                    return;
                }
                if (editor.getCellEditorValue() != null) {

                    editor.stopCellEditing();
                    tableModel.fireTableDataChanged();
                    return;
                }
                editor.cancelCellEditing();
                tableModel.fireTableDataChanged();
            }
        });
        cellEditor = new FileChooserCellEditor();


    }

    @Nullable
    @Override
    public SettingWrapper getState() {
        int count = driverTable.getRowCount();
        if(count <= 0) {
            return null;
        }
        SettingWrapper wrapper = new SettingWrapper();
        List<MybatisGeneratorSetting> settings = new ArrayList<>(count);
        MybatisGeneratorSetting setting = null;
        for (int i = 0; i < count; i++) {

            String name = (String) driverTable.getValueAt(i, 0);
            String driverClass = (String) driverTable.getValueAt(i, 1);
            String driverPath = (String) driverTable.getValueAt(i,2);
            String urlTemplate = (String) driverTable.getValueAt(i,3);

            if (StringTools.isBank(name) ||
                    StringTools.isBank(driverClass) ||
                    StringTools.isBank(driverPath) ||
                    StringTools.isBank(urlTemplate)) {
                continue;
            }
            setting = new MybatisGeneratorSetting(name, driverClass, driverPath,urlTemplate);
            settings.add(setting);
        }
        wrapper.setSettings(settings);
        return wrapper;
    }

    @Override
    public void loadState(SettingWrapper wrapper) {
        if (wrapper == null || wrapper.settings == null) {
            return;
        }
        tableModel = new DefaultTableModel(null, header);
        driverTable.setModel(tableModel);

        int size = wrapper.settings.size();
        Object[] data = null;
        for (int i = 0; i < size; i++) {

            MybatisGeneratorSetting setting = wrapper.settings.get(i);
            data = new Object[] {setting.getName(),
                    setting.getDriverClass(),
                    setting.getDriverPath(),
                    setting.getUrlTemplate()
            };

            tableModel.addRow(data);
        }
        driverTable.setModel(tableModel);
        this.oldSettings = wrapper.settings;
    }

    public JPanel getContainer() {
        return container;
    }

    public static MybatisGeneratorSettingsComponent getInstance(){
        MybatisGeneratorSettingsComponent component = ServiceManager
                .getService(MybatisGeneratorSettingsComponent.class);

        return component;
    }

    public List<MybatisGeneratorSetting> getOldSettings() {
        return oldSettings;
    }

    public void setOldSettings(
            List<MybatisGeneratorSetting> oldSettings) {
        this.oldSettings = oldSettings;
    }
}
