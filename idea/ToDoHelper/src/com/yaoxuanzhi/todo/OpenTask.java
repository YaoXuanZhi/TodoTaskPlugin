package com.yaoxuanzhi.todo;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.yaoxuanzhi.model.ToDoTaskModel;

public class OpenTask extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent e) {
        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        if (editor == null) {
            return;
        }
        TodoTask obj = new TodoTask();
        obj.openTask(editor);
        ToDoTaskModel model = ToDoTaskModel.getInstance();
        model.resetComponent();
    }
}
