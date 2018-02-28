package com.zuicoding.framework.mybatis.generator.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;

import org.jetbrains.annotations.Nullable;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.zuicoding.framework.mybatis.generator.model.MybatisGeneratorSetting;
import com.zuicoding.framework.mybatis.generator.ui.custom.BorderTableCellRenderer;
import com.zuicoding.framework.mybatis.generator.util.StringTools;

/**
 * Created by <a href="mailto:stephen.lin@gmail.com">Stephen.lin</a> on 2018/2/24.
 * <p>
 * <p>
 * </p>
 */
@State(name = "mybatisGeneratorSettings", storages = @Storage(id = "other", file =
        "$APP_CONFIG$/mybatis-generator-settings.xml"))
public class MybatisGeneratorSettingsComponent implements PersistentStateComponent<List<MybatisGeneratorSetting>> {
    private JPanel container;
    private JTable driverTable;
    private JButton addButton;
    private JButton delButton;

    private String[] header = new String[] {"name", "driverClass", "driverPath"};
    private DefaultTableModel tableModel;
    private JTextField driverPathField;
    private JFileChooser fileChooser;

    private List<MybatisGeneratorSetting> oldSettings;

    public MybatisGeneratorSettingsComponent() {

        tableModel = new DefaultTableModel(null, header);

        driverTable.setModel(tableModel);

        driverTable.setDefaultRenderer(Object.class, new BorderTableCellRenderer());
        driverPathField = new JTextField();
        driverPathField.setEditable(false);

        driverPathField.addMouseListener(
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {

                        if (fileChooser == null) {
                            fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                        }
                        int returnValue = fileChooser.showOpenDialog(container);
                        if (returnValue == JFileChooser.APPROVE_OPTION) {
                            File selectedFile = fileChooser.getSelectedFile();
                            driverPathField.setText(selectedFile.getAbsolutePath());
                        } else {
                            driverPathField.setText("");
                        }

                    }
                });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.addRow(new Object[3]);
                driverTable.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(driverPathField));
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
    }

    @Nullable
    @Override
    public List<MybatisGeneratorSetting> getState() {
        int count = driverTable.getRowCount();
        if(count <= 0) return null;
        List<MybatisGeneratorSetting> settings = new ArrayList<>(count);
        MybatisGeneratorSetting setting = null;
        for (int i = 0; i < count; i++) {

            String name = (String) driverTable.getValueAt(i, 0);
            String driverClass = (String) driverTable.getValueAt(i, 1);
            String driverPath = (String) driverTable.getValueAt(i,2);
            if (StringTools.isBank(name) ||
                    StringTools.isBank(driverClass) ||
                    StringTools.isBank(driverPath)) {
                continue;
            }
            setting = new MybatisGeneratorSetting(name, driverClass, driverPath);
            settings.add(setting);
        }

        return settings;
    }

    @Override
    public void loadState(List<MybatisGeneratorSetting> mybatisGeneratorSettings) {
        if (mybatisGeneratorSettings == null || mybatisGeneratorSettings.size() == 0) {
            return;
        }
        tableModel = new DefaultTableModel(null, header);
        driverTable.setModel(tableModel);

        int size = mybatisGeneratorSettings.size();
        Object[] data = null;
        for (int i = 0; i < size; i++) {

            MybatisGeneratorSetting setting = mybatisGeneratorSettings.get(i);
            data = new Object[] {setting.getName(),
                    setting.getDriverClass(),
                    setting.getDriverPath()};

            tableModel.addRow(data);
        }
        driverTable.setModel(tableModel);
        this.oldSettings = mybatisGeneratorSettings;
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
