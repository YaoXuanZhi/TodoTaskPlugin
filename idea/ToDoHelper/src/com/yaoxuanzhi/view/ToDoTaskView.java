package com.yaoxuanzhi.view;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.util.text.StringUtil;
import com.yaoxuanzhi.model.ToDoTaskModel;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToDoTaskView implements Configurable {
    private JButton button1;
    private JTextField textField1;
    private JPanel myRoot;
    private JLabel myLable;
    private boolean isLocked;
    private ToDoTaskModel model = ToDoTaskModel.getInstance();
    private static final Logger LOG = Logger.getInstance(ToDoTaskView.class);

    public ToDoTaskView() {
        isLocked = true;
        LOG.warn("========= ToDoTaskView init");
//        model = ToDoTaskModel.getInstance();
        initControls();
        registerActions();
    }

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "ToDoTask Viewer";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        LOG.warn("========= ToDoTaskView create component");
        return myRoot;
    }

    @Override
    public boolean isModified() {
        return StringUtil.compare(textField1.getText(), model.getMyPath(), false) != 0;
    }

    @Override
    public void apply() throws ConfigurationException {
        LOG.warn("========= ToDoTaskView apply");
        String str = textField1.getText();
        model.setMyPath(str);
    }

    @Override
    public void reset() {
        if (isLocked) {
            isLocked = false;
            return;
        }

        LOG.warn("========= ToDoTaskView reset");
        System.out.println("==========> reset");
        String str = "";
        textField1.setText(str);
        model.setMyPath(str);
    }

    private void initControls() {
        textField1.setText(model.getMyPath());
    }

    private void registerActions() {
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LOG.warn("=========> register");
                textField1.setText("按钮写入的文本");
            }
        });
    }
}
