
package com.zuicoding.framework.mybatis.generator.ui.custom;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.DefaultCellEditor;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileSystemView;

import com.zuicoding.framework.mybatis.generator.util.StringTools;


public class FileChooserCellEditor extends DefaultCellEditor {

    private JTextField field;
    private JFileChooser fileChooser;

    public FileChooserCellEditor() {
        super(new JTextField());

        field = (JTextField) getComponent();
        field.setEditable(false);
        field.setBorder(new LineBorder(Color.BLACK));
        String path = field.getText();
        if (StringTools.isBank(path)) {
            fileChooser = new JFileChooser(FileSystemView.getFileSystemView()
                    .getHomeDirectory());
        }else {
            fileChooser = new JFileChooser(new File(path));
        }

        field.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int returnValue = fileChooser.showOpenDialog(field);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    field.setText(selectedFile.getAbsolutePath());
                } else {
                    field.setText("");
                }
            }
        });

    }

}
