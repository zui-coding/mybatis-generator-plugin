
package com.zuicoding.framework.mybatis.generator.ui.custom;

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 * Created by <a href="mailto:stephen.lin@gmail.com">Stephen.lin</a> on 2018/2/24.
 * <p>
 * <p>
 * </p>
 */
public class CustomTreeCellEditor extends DefaultTreeCellEditor {
    private DefaultTreeCellRenderer renderer;
    private JTree tree;
    public CustomTreeCellEditor(JTree tree, DefaultTreeCellRenderer renderer) {
        super(tree, renderer);
        this.renderer = renderer;
        this.tree = tree;
    }

    @Override
    public Component getTreeCellEditorComponent(JTree tree, Object value, boolean isSelected, boolean expanded,
                                                boolean leaf, int row) {
        return super.getTreeCellEditorComponent(tree, value, isSelected, expanded, leaf, row);
    }

    @Override
    public Object getCellEditorValue() {

        return super.getCellEditorValue();
    }
}
