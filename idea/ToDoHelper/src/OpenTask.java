import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.yaoxuanzhi.DemmoSettingState;
import com.yaoxuanzhi.MyComponent;
import com.yaoxuanzhi.TodoTask;

public class OpenTask extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent e) {
        Editor editor = e.getData(PlatformDataKeys.EDITOR);
        if (editor == null) {
            return;
        }
        TodoTask obj = new TodoTask();
        obj.openTask(editor);

        DemmoSettingState settings = DemmoSettingState.getInstance();
        settings.print();
        settings.addItem("1233", "======>");
    }

    public void test() {
        MyComponent obj = MyComponent.getInstanceEx();
        obj.sayHello();
    }
}
