package com.yaoxuanzhi;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.*;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jdom.Element;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@State(name = "ToDoTaskSettings", storages = {
        @Storage("todo_task_conf.xml")
})
// 注意，使用了BaseComponent，那么就将它当作主体啦，并且这个实例只会创建一次
public class DemmoSettingState implements BaseComponent, PersistentStateComponent<Element>, Disposable {
//public class DemmoSettingState implements PersistentStateComponent<DemmoSettingState> {
    private String myPath;
    private static final Logger LOG = Logger.getInstance(DemmoSettingState.class);
    private static final String TEST_COMPONENT_NAME = "TestPlugin";

    @NotNull
    private final HashMap<String, String> globalMarks = new HashMap<>();

    public DemmoSettingState() {
        LOG.debug(" 构造 ：DemmoSettingState");
        myPath = "";
    }

    public boolean get() {
        return !StringUtil.isEmpty(myPath);
    }

    public String getPath() {
        return myPath;
    }

    public void setPath(String str) {
        myPath = str;
    }

    public void print() {
        LOG.debug("打印当前路径 ： " + myPath);
    }

//    由于转换成了Component之后，只会创建一个实例，因此不需要静态变量了
//    public static DemmoSettingState getInstance() {
//        return ServiceManager.getService(DemmoSettingState.class);
//    }

    @NotNull
    @Override
    public String getComponentName() {
        return TEST_COMPONENT_NAME;
    }

    @NotNull
    public static DemmoSettingState getInstance() {
        return ApplicationManager.getApplication().getComponent(DemmoSettingState.class);
//        return (DemmoSettingState)ApplicationManager.getApplication().getComponent(TEST_COMPONENT_NAME);
    }

    @Nullable
    @Override
    public Element getState() {
        LOG.debug("Saving state");
        final Element element = new Element("ToDoTask-Local");
        saveData(element);

        return element;
    }

    @Override
    public void loadState(@NotNull Element element) {
        readData(element);
    }

    //  定义一个序列化类，如何解析一个列表的数据呢
    void readData(@NotNull Element element) {
        Element marksElem = element.getChild("globalmarks");
        if (marksElem != null) {
            List markList = marksElem.getChildren("mark");
            for (Object aMarkList : markList) {
                Element markElem = (Element) aMarkList;
//              从dom上解析标签，生成指定对象
                String key = markElem.getAttributeValue("key");
                String value = markElem.getAttributeValue("line");
                globalMarks.put(key, value);
            }
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("globalMarks=" + globalMarks);
        }
    }

    void saveData(@NotNull Element element) {
        Element marksElem = new Element("globalmarks");
        for (Map.Entry<String, String> entry : globalMarks.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            LOG.warn("------ " + key + " ==> " + value);

            Element markElem = new Element("mark");
            markElem.setAttribute("key", key);
            markElem.setAttribute("line", value);
            marksElem.addContent(markElem);
        }
        element.addContent(marksElem);
    }

    public void addItem(String key, String value) {
        globalMarks.put(key, value);
    }

    @Override
    public void initComponent() {
        LOG.warn("initComponent");

//        if (isEnabled()) initializePlugin();

        LOG.debug("done");
    }

    @Override
    public void disposeComponent() {
        LOG.warn("disposeComponent");
    }

    @Override
    public void dispose() {
        LOG.warn("------- dispose");
    }
}
