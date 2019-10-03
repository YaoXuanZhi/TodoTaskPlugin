package com.yaoxuanzhi.ui;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.ui.IdeBorderFactory;

import javax.swing.*;
import java.awt.*;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ToDoTaskSettings implements Configurable {
    @NotNull
    @Nls
    @Override
    public String getDisplayName() {
        return "ToDoTask Settings";
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return null;
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        return myPanel;
    }

    @Override
    public boolean isModified() {
        return false;
    }

    @Override
    public void apply() throws ConfigurationException {
    }

    @Override
    public void reset() {
    }

    @Override
    public void disposeUIResources() {
    }

    @NotNull private final TodoTaskSettingsPanel myPanel = new TodoTaskSettingsPanel();
    private static final class TodoTaskSettingsPanel extends JPanel {
        public TodoTaskSettingsPanel() {
            setLayout(new BorderLayout());
            final JPanel conflictsPanel = new JPanel(new BorderLayout());
            final String title = String.format("Please set the ToDo-Icon style below:");
            conflictsPanel.setBorder(IdeBorderFactory.createTitledBorder(title, false));
            add(conflictsPanel, BorderLayout.CENTER);
        }
    }
}
