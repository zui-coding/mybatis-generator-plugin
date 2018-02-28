
package com.zuicoding.framework.mybatis.generator.ui.custom;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import com.zuicoding.framework.mybatis.generator.model.TreeNode;

/**
 * Created by <a href="mailto:stephen.lin@gmail.com">Stephen.lin</a> on 2018/2/9.
 * <p>
 * <p>
 * </p>
 */
public class CustomTreeCellRenderer extends DefaultTreeCellRenderer {

    private Color selectionBorderColor,
            selectionForeground,
            selectionBackground,
            textForeground,
            textBackground;

    public CustomTreeCellRenderer() {
        selectionBorderColor = UIManager.getColor("Tree.selectionBorderColor");
        selectionForeground = UIManager.getColor("Tree.selectionForeground");
        selectionBackground = UIManager.getColor("Tree.selectionBackground");
        textForeground = UIManager.getColor("Tree.textForeground");
        textBackground = UIManager.getColor("Tree.textBackground");
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value,
                                                  boolean sel, boolean expanded,
                                                  boolean leaf,
                                                  int row, boolean hasFocus) {


        DefaultMutableTreeNode dmtn = (DefaultMutableTreeNode)value;
        Object o = dmtn.getUserObject();
        if (o instanceof TreeNode){
            TreeNode node = (TreeNode)o;
            this.setIcon(node.getIcon());
            this.setText(node.getText());
//            JCheckBox box = new JCheckBox(node.getText());
//            //box.setIcon(node.getIcon());
//            box.setSelected(selected);
//            box.setEnabled(tree.isEnabled());
//
//            if (selected){
//                box.setForeground(selectionForeground);
//                box.setBackground(selectionBackground);
//            }else {
//                box.setForeground(textForeground);
//                box.setBackground(textBackground);
//            }
//            return box;
        }


        return super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
    }
}
