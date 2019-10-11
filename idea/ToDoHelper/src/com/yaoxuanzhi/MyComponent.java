package com.yaoxuanzhi;

import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ComponentManager;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.structuralsearch.plugin.ui.Configuration;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class MyComponent implements ProjectComponent, Configurable {
    private DemoSettingsEditor form;
//    public class MyComponent implements ProjectComponent, Configuration {
//    static MyComponent getInstance() {
//        return ComponentManager.getComponent(MyComponent.class);
//    }

    public static MyComponent getInstanceEx() {
        Application application = ApplicationManager.getApplication();
        return application.getComponent(MyComponent.class);
    }

//    public static void getInstance_1() {
//        Application application = ApplicationManager.getApplication();
//        MyComponent obj = application.getComponent(MyComponent.class);
//        obj.sayHello();
//    }

    @Override
    public void initComponent() {

    }

    @Override
    public void disposeComponent() {

    }

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "HelloWorldSetting";
    }

    @NotNull
    @Override
    public String getComponentName() {
        return "HelloWorldComponent";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        if (form == null) {
            form = new DemoSettingsEditor();
        }
        return form.getRootComponent();
    }

    @Override
    public void disposeUIResources() {
        form = null;
    }

    public void sayHello() {
        System.out.println("===========> test ");
    }

    @Override
    public String getHelpTopic() {return null;}

    @Override
    public boolean isModified() {
        return true;
    }

    @Override
    public void apply() throws ConfigurationException {
        if (form != null) {
            System.out.println("=========>");
        }
    }

    @Override
    public void reset() {
        if (form != null) {
            System.out.println("=========>");
            }
        }
}
