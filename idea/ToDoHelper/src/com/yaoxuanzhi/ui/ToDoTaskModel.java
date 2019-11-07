package com.yaoxuanzhi.ui;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@State(name = "ToDoTaskModel", storages = {@Storage("$APP_CONFIG$/todo_model.xml")})
public class ToDoTaskModel implements PersistentStateComponent<Element> {
    private static String ROOT_TAG = "todo_model";
    private static Element rootElement = new Element(ROOT_TAG);
    @Nullable
    @Override
    public Element getState() {
        Element element = new Element("TableDemoPlugin");
        element.addContent(rootElement);
        return element;
    }

    @Override
    public void loadState(@NotNull Element element) {
        rootElement = element.getChild(ROOT_TAG);
    }

    static class PluginSettings {
        boolean enabled = false;
        List<RewriteRule> rewriteRules = new ArrayList<>();
    }

    public static ToDoTaskModel getInstance() {
        return ServiceManager.getService(ToDoTaskModel.class);
    }

    static class RewriteRule {
        boolean active;
        String pattern;
        String replacementRule;
        String path;

        public RewriteRule() {

        }

        public RewriteRule(boolean active0, String pattern0, String replacementRule0, String path0) {
            this.active = active0;
            this.pattern = pattern0;
            this.replacementRule = replacementRule0;
            this.path = path0;
        }

        public RewriteRule copy() {
            RewriteRule rewriteRule = new RewriteRule();
            rewriteRule.active = this.active;
            rewriteRule.pattern = this.pattern;
            rewriteRule.path = this.path;
            rewriteRule.replacementRule = this.replacementRule;
            return rewriteRule;
        }

        public void updateWith(RewriteRule rewriteRule) {
            this.active = rewriteRule.active;
            this.pattern = rewriteRule.pattern;
            this.path = rewriteRule.path;
            this.replacementRule = rewriteRule.replacementRule;
        }

        public String rewrite(String contentRoot, String filepath) {
            return  contentRoot + filepath;
        }
    }
}
