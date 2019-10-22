package com.yaoxuanzhi.todo;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.CaretModel;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.yaoxuanzhi.model.ToDoTaskModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class TodoTask {
    private static final ToDoTaskModel MODEL = ToDoTaskModel.getInstance();
    private static final HashMap<String, Integer> TASK_ACTIONS = new HashMap<String, Integer>() {
        {
            put("open", 0);
            put("hand", 1);
            put("cancel", 2);
            put("done", 3);
        }
    };

    void openTask(Editor editor) {
        insertTag(editor, "open", " ");
    }

    void cancelTask(Editor editor) {
        taskCommand(editor, "open", "cancel");
    }

    void handTask(Editor editor) {
        taskCommand(editor, "open", "hand");
    }

    void doneTask(Editor editor) {
        taskCommand(editor, "open", "done");
    }

    private void insertTag(Editor editor, String tag, String afterTag) {
        int index = TASK_ACTIONS.get(tag);
        String currTag = MODEL.getTaskTag(index);

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

    private void addTodoMap(ArrayList<List<Integer>> todoMap, Integer a, Integer b) {
        todoMap.add(new ArrayList<Integer>() {{add(a);add(b);}});
    }

    private void taskCommand(Editor editor, String oldTag, String newTag) {
        ArrayList<List<Integer>> todoMap = new ArrayList<>();
        for (Integer i = TASK_ACTIONS.get(oldTag); i < TASK_ACTIONS.get(newTag); i++) {
            addTodoMap(todoMap, i, TASK_ACTIONS.get(newTag));
        }
        addTodoMap(todoMap, TASK_ACTIONS.get(newTag), TASK_ACTIONS.get(oldTag));

        for (List<Integer> v : todoMap) {
            String oldString = MODEL.getTaskTag(v.get(0));
            String newString = MODEL.getTaskTag(v.get(1));
            boolean result = replaceStringEx(editor, oldString, newString);
            if (result) {
//                showPopupBalloon(editor, "todo进度更新");
                break;
            }
        }
    }

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

//      查找被替换的字符串
        int startOffset = lineContent.indexOf(oldString);
        if (startOffset < 0) {
            return false;
        }

//      计算被编辑器上被替换文本的偏移值
        int realStartOffset = lineStartOffset + startOffset;
        int realEndOffset = realStartOffset + oldString.length();

//      调用Api来替换编辑器上的文本
        Runnable runnable = () -> document.replaceString(realStartOffset, realEndOffset, newString);
        WriteCommandAction.runWriteCommandAction(project, runnable);
        return true;
    }

//    private void replcaceSelectedString(Editor editor, String newString) {
//        Document document = editor.getDocument();
//        Project project = editor.getProject();
//        Caret primaryCaret = editor.getCaretModel().getPrimaryCaret();
//        int start = primaryCaret.getSelectionStart();
//        int end = primaryCaret.getSelectionEnd();
//        // Replace the selection with a fixed string.
//        // Must do this document change in a write action context.
//        WriteCommandAction.runWriteCommandAction(project, () ->
//                document.replaceString(start, end, newString)
//        );
//        primaryCaret.removeSelection();
//    }

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
