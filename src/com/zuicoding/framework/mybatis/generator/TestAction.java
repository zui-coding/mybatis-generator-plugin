
package com.zuicoding.framework.mybatis.generator;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;

/**
 * Created by <a href="mailto:stephen.lin@gmail.com">Stephen.lin</a> on 2018/2/26.
 * <p>
 * <p>
 * </p>
 */
public class TestAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Messages.showErrorDialog("请填写服务器地址","Mybatis Generator");
    }
}
