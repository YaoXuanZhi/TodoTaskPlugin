// 引入可编辑的工具和行为吧，支持自定义设置这些功能
// 并且引入正则表达式来说明它们的作用
package com.yaoxuanzhi.ui;

import com.intellij.openapi.components.BaseComponent;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;

import javax.swing.*;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ToDoTaskController implements Configurable {

    @NotNull
    @Nls
    @Override
    public String getDisplayName() {
        return "ToDoTask Settings";
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return null;
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        return myRoot;
    }

    @Override
    public boolean isModified() {
        return false;
    }

    @Override
    public void apply() throws ConfigurationException {
        List<ToDoTaskModel.RewriteRule> items = myRoot.getUserData();
        System.out.printf("=========> %d\n", items.size());
    }

    @Override
    public void reset() {
//        myRoot.resetUserData();
        myRoot.initUI();
    }

    @Override
    public void disposeUIResources() {
        System.out.println("ui dispose\n");
    }

//    @Override
//    public void initComponent() {
//        myRoot.initUI();
//    }

    @NotNull private final ToDoTaskView myRoot = new ToDoTaskView();
}
