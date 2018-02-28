
package com.zuicoding.framework.mybatis.generator.ui.custom;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

/**
 * Created by <a href="mailto:stephen.lin@gmail.com">Stephen.lin</a> on 2018/2/24.
 * <p>
 * <p>
 * </p>
 */
public class BorderTableCellRenderer extends DefaultTableCellRenderer implements TableCellRenderer {



    public BorderTableCellRenderer() {

    }

    @Override
    public Component getTableCellRendererComponent(JTable table,
                                                   Object value,
                                                   boolean isSelected,
                                                   boolean hasFocus,
                                                   int row, int column) {



        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        setBorder(new LineBorder(Color.gray));
        return this;
    }
}
