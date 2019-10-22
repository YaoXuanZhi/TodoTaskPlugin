package com.yaoxuanzhi.model;

import com.intellij.openapi.components.*;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@State(name = "ToDoTaskModel", storages = {@Storage("$APP_CONFIG$/todo_task.xml")})
//public class ToDoTaskModel implements PersistentStateComponent<ToDoTaskModel> {
//    private String myPath;
//    private String[] TASK_ICONS;
//
//    public ToDoTaskModel() {
//        myPath = "";
////        TASK_ICONS = new String[]{"☐", "❑", "✘", "✔"};
//        TASK_ICONS = new String[]{"+", "=", "x", "y"};
//    }
//
//    public static ToDoTaskModel getInstance() {
//        return ServiceManager.getService(ToDoTaskModel.class);
//    }
//
//    @Nullable
//    @Override
//    public ToDoTaskModel getState() {
//        return this;
//    }
//
//    @Override
//    public void loadState(@NotNull ToDoTaskModel state) {
//        XmlSerializerUtil.copyBean(state, this);
//    }
//
//    public void setMyPath(String str) {
//        myPath = str;
//    }
//
//    public String getMyPath() {
//        return myPath;
//    }
//
//    public String[] getTaskIcons() {
//        return TASK_ICONS;
//    }
//}

@State(name = "ToDoTaskModel", storages = {@Storage("$APP_CONFIG$/todo_task.xml")})
public class ToDoTaskModel implements BaseComponent, PersistentStateComponent<Element> {
    private String myPath;
    private static Element rootElement = new Element("task_icons");

    public ToDoTaskModel() {
        myPath = "";
    }

    public static ToDoTaskModel getInstance() {
        return ServiceManager.getService(ToDoTaskModel.class);
    }

    public void resetComponent() {
        rootElement.removeContent();
        HashMap<String, String> todoTaskMap = new HashMap<>();
        todoTaskMap.put("open", "☐");
        todoTaskMap.put("hand", "❑");
        todoTaskMap.put("cancel", "✘");
        todoTaskMap.put("done", "✔");

        Element optionsElement = new Element("task_icons");

        int i = 0;
        for (Map.Entry<String, String> entry : todoTaskMap.entrySet()) {
            i++;
            String desc = entry.getKey();
            String icon = entry.getValue();

            Element elem = new Element("option");
            elem.setText(desc);
            elem.setAttribute("index", Integer.toString(i));
            elem.setAttribute("icon", icon);
            optionsElement.addContent(elem);
        }
        rootElement.addContent(optionsElement);
    }

    @Override
    public void initComponent() {

    }

    @Override
    public void disposeComponent() {

    }

    @Nullable
    @Override
    public Element getState() {
        Element element = new Element("ToDoTaskPlugin");
        element.addContent(rootElement);
        return element;
    }

    @Override
    public void loadState(@NotNull Element element) {
        rootElement = element.getChild("task_icons");
    }

    public void setMyPath(String str) {
        myPath = str;
    }

    public String getMyPath() {
        return myPath;
    }

    public String getTaskTag(Integer matchIndex) {
        List<Element> optionElemList = rootElement.getChildren("option");
        for (Element optionElem : optionElemList) {
            String index = optionElem.getAttributeValue("index");
            String icon = optionElem.getAttributeValue("icon");
            if (Integer.parseInt(index) == matchIndex) {
                return icon;
            }
        }
        return "";
    }
}
