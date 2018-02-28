package com.zuicoding.framework.mybatis.generator;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.DialogWrapper;
import com.zuicoding.framework.mybatis.generator.ui.MybatisGeneratorDialog;

/**
 * Created by <a href="mailto:stephen.linicoding@gmail.com">Stephen.lin</a> on 2018/1/23.
 * <p>
 * <p>
 * </p>
 */
public class MybatisGeneratorToolsAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        DialogWrapper dialog = new MybatisGeneratorDialog();
        dialog.show();
    }
}
