
package com.zuicoding.framework.mybatis.generator.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.filechooser.FileSystemView;

import org.jetbrains.annotations.Nullable;

import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.Messages;
import com.zuicoding.framework.mybatis.generator.model.DataSourceInfo;
import com.zuicoding.framework.mybatis.generator.model.MybatisGeneratorSetting;
import com.zuicoding.framework.mybatis.generator.service.FileGenerator;
import com.zuicoding.framework.mybatis.generator.ui.custom.MybaitsGeneratorComboBoxRenderer;
import com.zuicoding.framework.mybatis.generator.ui.custom.MybatisGeneratorComboBoxModel;
import com.zuicoding.framework.mybatis.generator.util.StringTools;

/**
 * Created by <a href="mailto:stephen.lin@gmail.com">Stephen.lin</a> on 2018/2/9.
 * <p>
 * <p>
 * </p>
 */
public class ConnectionDialog extends DialogWrapper {
    private JPanel container;
    private JTextField connecitonNameField;
    private JTextField driverPathField;
    private JTextField hostField;
    private JSpinner portSpinner;
    private JTextField userField;
    private JPasswordField passwordField;
    private JTextField driverClassField;
    private JButton filebrowerButton;
    private JComboBox driverTypeCombobox;
    private DataSourceInfo dataSourceInfo;
    private JFileChooser fileChooser;
    private MybatisGeneratorSettingsComponent settingsComponent;
    private FileGenerator generator;
    public ConnectionDialog(){
        super(false);
        init();
        setTitle("连接");
        setOKButtonText("确定");

        portSpinner.setModel(new SpinnerNumberModel(3306,80,65536,1));
        portSpinner.setEditor(new JSpinner.NumberEditor(portSpinner,"#"));
        bindEvent();
        settingsComponent = MybatisGeneratorSettingsComponent.getInstance();
        driverTypeCombobox.setModel(new MybatisGeneratorComboBoxModel(
                settingsComponent.getOldSettings()));
        driverTypeCombobox.setEditable(false);
        driverTypeCombobox.updateUI();
        driverTypeCombobox.setRenderer(new MybaitsGeneratorComboBoxRenderer());
        driverTypeCombobox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    MybatisGeneratorSetting setting = (MybatisGeneratorSetting) e.getItem();
                    driverPathField.setText(setting.getDriverPath());
                    driverClassField.setText(setting.getDriverClass());

                }
            }
        });
        generator = FileGenerator.getInstance();
    }

    private void bindEvent(){
        filebrowerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fileChooser == null) {
                    fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                }
                int returnValue = fileChooser.showOpenDialog(container);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    driverPathField.setText(selectedFile.getAbsolutePath());
                }else {
                    driverPathField.setText("");
                }
            }
        });


    }

    private void valid(){
        if (StringTools.isBank(connecitonNameField.getText())){
            Messages.showErrorDialog("请填写连接名称","Mybatis Generator");
            return;
        }
        if (StringTools.isBank(driverPathField.getText())){
            Messages.showErrorDialog("请填写数据库驱动文件路径","Mybatis Generator");
            return;
        }
        if (StringTools.isBank(hostField.getText())){
            Messages.showErrorDialog("请填写数据库驱动文件路径","Mybatis Generator");
            return;
        }
        if (StringTools.isBank(hostField.getText())){
            Messages.showErrorDialog("请填写服务器地址","Mybatis Generator");
            return;
        }
        if (StringTools.isBank(driverClassField.getText())){
            Messages.showErrorDialog("请填写数据库驱动类","Mybatis Generator");
            return;
        }
        getOKAction().setEnabled(true);

    }



    @Override
    protected void doOKAction() {
        try {
            valid();
            dataSourceInfo = new DataSourceInfo();
            dataSourceInfo.setConnectionName(connecitonNameField.getText());
            dataSourceInfo.setDriverPath(driverPathField.getText());
            dataSourceInfo.setDriverClass(driverClassField.getText());
            portSpinner.validate();
            dataSourceInfo.setPort((Integer) portSpinner.getValue());
            dataSourceInfo.setUserName(userField.getText());
            dataSourceInfo.setHost(hostField.getText());
            dataSourceInfo.setPassword(new String(passwordField.getPassword()));
            MybatisGeneratorSetting setting = (MybatisGeneratorSetting) driverTypeCombobox.getSelectedItem();
            dataSourceInfo.setUrl(generator.generateDBUrl("",
                    dataSourceInfo.getHost(),
                    dataSourceInfo.getPort(),setting.getUrlTemplate()));
            dataSourceInfo.setUrl(setting.getUrlTemplate());
            dataSourceInfo.setUrlTemplate(setting.getUrlTemplate());
            super.doOKAction();
        }catch (Exception e){
            Messages.showErrorDialog("创建连接失败","Mybatis Generator");
            throw new IllegalArgumentException(e);
        }

    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return container;
    }

    public DataSourceInfo getDataSourceInfo() {

        return dataSourceInfo;
    }

    @Override
    public void show() {
        dataSourceInfo = null;
        super.show();
    }

}
