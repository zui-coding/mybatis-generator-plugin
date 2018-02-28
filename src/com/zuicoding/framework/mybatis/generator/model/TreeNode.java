
package com.zuicoding.framework.mybatis.generator.model;

import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * Created by <a href="mailto:stephen.lin@gmail.com">Stephen.lin</a> on 2018/2/9.
 * <p>
 * <p>
 * </p>
 */
public class TreeNode {
    private String text;
    private Icon icon;
    private NodeEnum nodeType;
    private DataSourceInfo dataSourceInfo;
    public TreeNode() {
    }

    public TreeNode(String text) {
        this.text = text;
    }

    public TreeNode(String text, Icon icon) {
        this.text = text;
        this.icon = icon;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

   public void setIcon(String iconPath){
        try {
            Toolkit tk = Toolkit.getDefaultToolkit();
            Image image = tk.createImage(this.getClass().getClassLoader().getResource(iconPath));
            image = image.getScaledInstance(32,32,Image.SCALE_DEFAULT);
            icon = new ImageIcon(image);

        }catch (Exception e){
            e.printStackTrace();
        }

   }

    public NodeEnum getNodeType() {
        return nodeType;
    }

    public void setNodeType(NodeEnum nodeType) {
        this.nodeType = nodeType;
    }

    @Override
    public String toString() {
        return this.text;
    }

    public DataSourceInfo getDataSourceInfo() {
        return dataSourceInfo;
    }

    public void setDataSourceInfo(DataSourceInfo dataSourceInfo) {
        this.dataSourceInfo = dataSourceInfo;
    }
}
