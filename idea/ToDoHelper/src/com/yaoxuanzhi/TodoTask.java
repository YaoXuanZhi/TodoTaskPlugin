// - 增加一个JavaGui，专门用来设置 TodoList Settings
package com.yaoxuanzhi;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.util.TextRange;
import com.intellij.ui.Gray;
import com.intellij.ui.JBColor;
import com.intellij.ui.popup.PopupFactoryImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class TodoTask {
    private static final HashMap<String, Integer> TASK_ACTIONS = new HashMap<String, Integer>() {
        {
            put("open", 0);
            put("hand", 1);
            put("cancel", 2);
            put("done", 3);
        }
    };
    private static final HashMap<Integer, String> TASK_ICONS = new HashMap<Integer, String>() {
        {
            put(0, "☐");
            put(1, "❑");
            put(2, "✘");
            put(3, "✔");
        }
    };

    public void openTask(Editor editor) {
        insertTag(editor, "open", " ");
    }

    public void cancelTask(Editor editor) {
        taskCommand(editor, "open", "cancel");
    }

    public void handTask(Editor editor) {
        taskCommand(editor, "open", "hand");
    }

    public void doneTask(Editor editor) {
        taskCommand(editor, "open", "done");
    }

    private void insertTag(Editor editor, String tag, String afterTag) {
        String currTag = TASK_ICONS.get(TASK_ACTIONS.get(tag));

//      检查光标所在行的状态
        Document document = editor.getDocument();
        Project project = editor.getProject();
        CaretModel caretModel = editor.getCaretModel();

//      获取光标所在行首插入
        int caretOffset = caretModel.getOffset();
        int lineNum = document.getLineNumber(caretOffset);
        int lineStartOffset = document.getLineStartOffset(lineNum);
        WriteCommandAction.runWriteCommandAction(project, () ->
                document.insertString(lineStartOffset, currTag + afterTag)
        );
    }

    private List<Integer> createArrayList(Integer a, Integer b) {
        List<Integer> temp = new ArrayList<Integer>() {
        };
        temp.add(a);
        temp.add(b);
        return temp;
    }

    private void taskCommand(Editor editor, String oldTag, String newTag) {
        ArrayList<List<Integer>> todoMap = new ArrayList<List<Integer>>() {
        };
        for (Integer i = TASK_ACTIONS.get(oldTag); i < TASK_ACTIONS.get(newTag); i++) {
            todoMap.add(createArrayList(i, TASK_ACTIONS.get(newTag)));
        }
        todoMap.add(createArrayList(TASK_ACTIONS.get(newTag), TASK_ACTIONS.get(oldTag)));

        for (List<Integer> v : todoMap) {
            String oldString = TASK_ICONS.get(v.get(0));
            String newString = TASK_ICONS.get(v.get(1));
            boolean result = replaceStringEx(editor, oldString, newString);
            if (result) {
//                showPopupBalloon(editor, "todo进度更新");
                break;
            }
        }
    }

//  采用正则表达式来替换文本
    private boolean replaceStringEx(Editor editor, String oldString, String newString) {
        Document document = editor.getDocument();
        Project project = editor.getProject();
        CaretModel caretModel = editor.getCaretModel();

//      获取光标所在行的文本
        int caretOffset = caretModel.getOffset();
        int lineNum = document.getLineNumber(caretOffset);
        int lineStartOffset = document.getLineStartOffset(lineNum);
        int lineEndOffset = document.getLineEndOffset(lineNum);
        String lineContent = document.getText(new TextRange(lineStartOffset, lineEndOffset));

        String pattern = String.format("(%s)", oldString);
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(lineContent);
        if (matcher.find()) {
            String newContent = matcher.replaceFirst(newString);

            if (!lineContent.equals(newContent)) {
                Runnable runnable = () -> {
                    document.deleteString(lineStartOffset, lineEndOffset);
                    document.insertString(lineStartOffset, newContent);
                };
                WriteCommandAction.runWriteCommandAction(project, runnable);
                caretModel.moveToOffset(caretOffset);
                return true;
            }
        }
        return false;
    }

//  弹出冒泡文本
//  @refer to [RequestRunnable](https://github.com/a483210/GoogleTranslation/blob/master/src/com/xiuyukeji/plugin/translation/RequestRunnable.java)
//    private void showPopupBalloon(Editor editor, final String result) {
//        ApplicationManager.getApplication().invokeLater(() -> {
//            editor.putUserData(PopupFactoryImpl.ANCHOR_POPUP_POSITION, null);
//            JBPopupFactory factory = JBPopupFactory.getInstance();
//            factory.createHtmlTextBalloonBuilder(result, null, new JBColor(Gray._242, Gray._0), null)
//                    .createBalloon()
//                    .show(factory.guessBestPopupLocation(editor), Balloon.Position.below);
//        });
//   }
}
