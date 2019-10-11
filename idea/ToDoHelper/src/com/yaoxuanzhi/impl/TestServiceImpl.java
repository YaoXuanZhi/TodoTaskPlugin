package com.yaoxuanzhi.impl;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import com.yaoxuanzhi.TestService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@State(name = "ToDoTaskService", storages = {
        @Storage("emacs.xml")
})
public class TestServiceImpl implements TestService, PersistentStateComponent<TestServiceImpl> {
    private String myName;
    public TestServiceImpl() {
        myName = "faflajf";
    }

    @Nullable
    @Override
    public TestServiceImpl getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull TestServiceImpl testService) {
        XmlSerializerUtil.copyBean(testService, this);
    }

    @Override
    public void setName(String str) {
        myName = str;
    }

    @Override
    public String getName() {
        return myName;
    }

    @Override
    public void print() {
        System.out.println(myName);
    }
}
