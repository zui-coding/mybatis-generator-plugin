
package com.zuicoding.framework.mybatis.generator.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.TreePath;

import org.jetbrains.annotations.Nullable;

import com.intellij.openapi.ui.DialogWrapper;
import com.zuicoding.framework.mybatis.generator.db.DBUtils;
import com.zuicoding.framework.mybatis.generator.db.DynamicDBDriver;
import com.zuicoding.framework.mybatis.generator.model.DataSourceInfo;
import com.zuicoding.framework.mybatis.generator.model.MybatisGeneratorConfig;
import com.zuicoding.framework.mybatis.generator.model.NodeEnum;
import com.zuicoding.framework.mybatis.generator.model.TreeNode;
import com.zuicoding.framework.mybatis.generator.service.FileGenerator;
import com.zuicoding.framework.mybatis.generator.ui.custom.CustomTreeCellRenderer;

/**
 * Created by <a href="mailto:stephen.lin@gmail.com">Stephen.lin</a> on 2018/2/9.
 * <p>
 * <p>
 * </p>
 */
public class MybatisGeneratorDialog extends DialogWrapper {

    private JPanel container;
    private JTree databaseTree;
    private JButton createConButton;
    private JComboBox defaultModelTypeCombobox;
    private JComboBox targetRuntimeCombobox;
    private JTextField fileEncodingField;
    private JTextField javaModelPackagePathField;
    private JTextField javaModelProjectPathField;
    private JComboBox constructorBasedCombobox;
    private JComboBox trimStringsCombobox;
    private JTextField sqlMapPackagePathField;
    private JTextField sqlMapProjectPathField;

    private JButton sqlMapProjectButton;

    private JButton javaModelProjectButton;
    private JTextField javaClientPackagePathField;
    private JComboBox javaClientTypeCombobox;
    private JTextField javaClientProjectPathField;
    private JButton javaClientProjectButton;
    private JComboBox enableCountCombobox;
    private JComboBox enableUpdateCombobox;
    private JComboBox enableDelCombobox;
    private JComboBox enableSelectCombobox;
    private JComboBox queryIdCombobox;
    private JComboBox useActColumnCombobox;
    private JPanel sqlMapGeneratorPanel;
    private JButton generatorButton;
    private DynamicDBDriver dynamicDBDriver;

    private ConnectionDialog connectionDialog;
    private JFileChooser fileChooser;

    private String[] contextModelType = {"flat", "conditional", "hierarchical" };
    private String[] contextTargetRuntime = {"MyBatis3" , "MyBatis3Simple" };

    private Boolean[] boolValues = {true, false };

    private String[] javaClientTypes = {"XMLMAPPER", "ANNOTATEDMAPPER", "MIXEDMAPPER"};

    public MybatisGeneratorDialog(){
        super(false);
        //init();
        setTitle("mybatis 生成器");
        setOKButtonText("生成");
        dynamicDBDriver = new DynamicDBDriver();
        bindOpenDB();
        databaseTree.setModel(null);
        databaseTree.setCellRenderer(new CustomTreeCellRenderer());
        databaseTree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                int count = e.getClickCount();
                if (count == 2) {


                    TreePath selectionPath = databaseTree.getSelectionPath();

                    loadNode((DefaultMutableTreeNode) selectionPath.getLastPathComponent());

                    databaseTree.expandPath(selectionPath);
                    databaseTree.updateUI();
                }
            }
        });
        databaseTree.addTreeWillExpandListener(new TreeWillExpandListener() {
            @Override
            public void treeWillExpand(TreeExpansionEvent event) throws ExpandVetoException {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode)event.getPath().getLastPathComponent();
                int count = node.getChildCount();
                if (count == 0) {
                    loadNode(node);
                }

            }

            @Override
            public void treeWillCollapse(TreeExpansionEvent event) throws ExpandVetoException {

            }
        });

        initData();

    }

    private void initComboboxData(JComboBox comboBox,Object[] data,int selIndex) {
        comboBox.setEditable(false);
        for (Object o : data) {
            comboBox.addItem(o);
        }
        comboBox.setSelectedIndex(selIndex);
    }

    private void bindButtonEvent(JButton button,JTextField clearText) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileBrowser();
                int returnValue = fileChooser.showOpenDialog(container);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    clearText.setText(selectedFile.getAbsolutePath());
                }else {
                    clearText.setText("");
                }
            }
        });
    }
    private void initData() {

        initComboboxData(defaultModelTypeCombobox,contextModelType,0);

        initComboboxData(targetRuntimeCombobox,contextTargetRuntime,0);

        initComboboxData(constructorBasedCombobox,boolValues,1);
        initComboboxData(trimStringsCombobox,boolValues,0);
        initComboboxData(javaClientTypeCombobox,javaClientTypes,0);

        initComboboxData(enableCountCombobox,boolValues,1);
        initComboboxData(enableUpdateCombobox,boolValues,1);
        initComboboxData(enableDelCombobox,boolValues,1);
        initComboboxData(enableSelectCombobox,boolValues,1);
        initComboboxData(queryIdCombobox,boolValues,1);
        initComboboxData(useActColumnCombobox,boolValues,1);

        bindButtonEvent(javaModelProjectButton,javaModelProjectPathField);

        bindButtonEvent(sqlMapProjectButton,sqlMapProjectPathField);

        bindButtonEvent(javaClientProjectButton, javaClientProjectPathField);

        generatorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    generate();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    private void fileBrowser(){
        if (fileChooser == null) {
            fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        }
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    }


    private void loadNode(DefaultMutableTreeNode node) {
        if (node == null) {
            return;
        }
        node.removeAllChildren();
        TreeNode currentNode = (TreeNode) node.getUserObject();
        switch (currentNode.getNodeType()) {
            case DATABASE:
                List<TreeNode> tables = DBUtils.getInstance(dynamicDBDriver).getTables(currentNode.getText());
                for (TreeNode treeNode : tables) {
                    node.add(new DefaultMutableTreeNode(treeNode,false));
                }
                break;
            case CONNECTION:
                List<TreeNode> list = DBUtils.getInstance(dynamicDBDriver).getDatabases();
                for (TreeNode treeNode : list) {
                    node.add(new DefaultMutableTreeNode(treeNode,true));
                }
                break;
        }

    }


    private void bindOpenDB(){
        createConButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (connectionDialog == null){
                    connectionDialog = new ConnectionDialog();
                }
                connectionDialog.show();
                DataSourceInfo dataSourceInfo = connectionDialog.getDataSourceInfo();
                if(dataSourceInfo == null) {
                    return;
                }
                try {
                    dynamicDBDriver.registe(dataSourceInfo);
                } catch (Exception e1) {
                    throw new IllegalArgumentException(e1);
                }
                TreeNode collectNode = new TreeNode(dataSourceInfo.getConnectionName());
                collectNode.setIcon("images/connection.png");
                collectNode.setNodeType(NodeEnum.CONNECTION);
                DefaultMutableTreeNode parent = new DefaultMutableTreeNode(collectNode);
                databaseTree.setModel(new DefaultTreeModel(parent,true));
            }
        });
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return container;
    }

    private void generate() throws Exception {
        Map<String,MybatisGeneratorConfig> dbMap = new HashMap<>();
        FileGenerator generator = FileGenerator.getInstance();
        for (TreePath treePath : databaseTree.getSelectionPaths()) {

            DefaultMutableTreeNode node = (DefaultMutableTreeNode) treePath.getLastPathComponent();
            TreeNode treeNode = (TreeNode) node.getUserObject();
            if (treeNode.getNodeType() == NodeEnum.TABLE ) {
                DefaultMutableTreeNode dataBaseNode = (DefaultMutableTreeNode) node.getParent();
                TreeNode parent = (TreeNode) dataBaseNode.getUserObject();
                MybatisGeneratorConfig config = null;
                if (dbMap.containsKey(parent.getText())) {

                    config = dbMap.get(parent.getText());

                    config.addTable(treeNode.getText());
                    dbMap.put(parent.getText(),config);
                    continue;
                }
                config = new MybatisGeneratorConfig();
                DataSourceInfo dataSourceInfo = treeNode.getDataSourceInfo();
                config.setJdbcUrl(generator.generateDBUrl(parent.getText(),
                        dataSourceInfo.getHost(),
                        dataSourceInfo.getPort(),
                        dataSourceInfo.getUrlTemplate()));

                fillConfig(config,dataSourceInfo);

                config.addTable(treeNode.getText());

                dbMap.put(parent.getText(),config);

            }
        }


        Map<String,Object> params = new HashMap<>(1);
        for (Map.Entry<String, MybatisGeneratorConfig> entry : dbMap.entrySet()) {
            params.put("config",entry.getValue());
            File homeDirectory = FileSystemView.getFileSystemView().getHomeDirectory();
            String path =  homeDirectory.getAbsolutePath() + File.separator + String.format("mybatis-generator-config-%s.xml",
                    entry.getKey());
            File file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter writer = new FileWriter(file);

            generator.generateConfig(params,writer);
            writer.close();

            generator.generate(file);

            file.deleteOnExit();
            params.clear();
        }




    }

    private void fillConfig(MybatisGeneratorConfig config,DataSourceInfo dataSourceInfo) {
        config.setConstructorBased((Boolean) constructorBasedCombobox.getSelectedItem());
        config.setDataSourceInfo(dataSourceInfo);
        config.setDefaultModelType(defaultModelTypeCombobox.getSelectedItem().toString());
        config.setDriverJarPath(dataSourceInfo.getDriverPath());
        config.setEnableCount((Boolean) enableCountCombobox.getSelectedItem());
        config.setEnableDelete((Boolean) enableDelCombobox.getSelectedItem());
        config.setEnableSelect((Boolean) enableSelectCombobox.getSelectedItem());
        config.setEnableUpdate((Boolean) enableUpdateCombobox.getSelectedItem());
        config.setJavaClientTargetPackage(javaClientPackagePathField.getText());
        config.setJavaClientTargetProjectPath(javaClientProjectPathField.getText());
        config.setJavaClientType(javaClientTypeCombobox.getSelectedItem().toString());
        config.setJavaModelTargetPackage(javaModelPackagePathField.getText());
        config.setJavaModelTargetProjectPath(javaModelProjectPathField.getText());
        config.setJavaFileEncoding(fileEncodingField.getText());
        config.setSelectQueryId((Boolean) queryIdCombobox.getSelectedItem());
        config.setSqlMapTargetPackage(sqlMapPackagePathField.getText());
        config.setSqlMapTargetProjectPath(sqlMapProjectPathField.getText());
        config.setTargetRuntime(targetRuntimeCombobox.getSelectedItem().toString());
        config.setTrimStrings((Boolean) trimStringsCombobox.getSelectedItem());
        config.setUseActualColumnNames((Boolean) useActColumnCombobox.getSelectedItem());
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("MybatisGeneratorDialog");
        frame.setContentPane(new MybatisGeneratorDialog().container);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


}
