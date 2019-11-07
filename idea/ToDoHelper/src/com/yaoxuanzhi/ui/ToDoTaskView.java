package com.yaoxuanzhi.ui;

import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.ui.IdeBorderFactory;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.util.Function;
import com.intellij.util.PathUtil;
import com.intellij.util.ui.ColumnInfo;
import com.intellij.util.ui.LocalPathCellEditor;
import com.intellij.util.ui.table.TableModelEditor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.apache.commons.lang.StringUtils;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ToDoTaskView extends JPanel {
    private JComponent rewriteRulesTable;
    private TableModelEditor<ToDoTaskModel.RewriteRule> editor;

    ToDoTaskView() {
        createUIComponents();
    }

    List<ToDoTaskModel.RewriteRule> getUserData() {
        return this.editor.getModel().getItems();
    }

    void setUserData(List<ToDoTaskModel.RewriteRule> items) {
        this.editor.getModel().setItems(items);
    }

    void resetUserData() {
        System.out.println("==========> ");
    }

    private static final FileChooserDescriptor APP_FILE_CHOOSER_DESCRIPTOR = FileChooserDescriptorFactory.createSingleFileOrExecutableAppDescriptor();
    private static final TableModelEditor.EditableColumnInfo<ToDoTaskModel.RewriteRule, String> PATH_COLUMN_INFO =
            new TableModelEditor.EditableColumnInfo<ToDoTaskModel.RewriteRule, String>("Path") {
                @Override
                public String valueOf(ToDoTaskModel.RewriteRule item) {
                    return PathUtil.toSystemDependentName(item.path);
                }

                @Override
                public void setValue(ToDoTaskModel.RewriteRule item, String value) {
                    item.path = value;
                }

                @Nullable
                @Override
                public TableCellEditor getEditor(ToDoTaskModel.RewriteRule item) {
                    return new LocalPathCellEditor().fileChooserDescriptor(APP_FILE_CHOOSER_DESCRIPTOR).normalizePath(true);
                }
            };

    private final TableModelEditor.EditableColumnInfo<ToDoTaskModel.RewriteRule, Boolean> ACTIVE_COLUMN = new TableModelEditor.EditableColumnInfo<ToDoTaskModel.RewriteRule, Boolean>() {

        @Override
        public Class getColumnClass() {
            return Boolean.class;
        }

        @Override
        public Boolean valueOf(ToDoTaskModel.RewriteRule item) {
            return item.active;
        }

        @Override
        public void setValue(ToDoTaskModel.RewriteRule item, Boolean value) {
            item.active = value;
        }

    };

    private final TableModelEditor.EditableColumnInfo<ToDoTaskModel.RewriteRule, String> PATTERN_COLUMN = new TableModelEditor.EditableColumnInfo<ToDoTaskModel.RewriteRule, String>("Pattern") {
        @Override
        public String valueOf(ToDoTaskModel.RewriteRule item) {
            return item.pattern;
        }

        @Override
        public void setValue(ToDoTaskModel.RewriteRule item, String value) {
            if (StringUtils.isNotBlank(value)) {
                item.pattern = value;
            }
        }
    };

    private final TableModelEditor.EditableColumnInfo<ToDoTaskModel.RewriteRule, String> REPLACEMENT_COLUMN = new TableModelEditor.EditableColumnInfo<ToDoTaskModel.RewriteRule, String>("Replacement") {

        @Override
        public String valueOf(ToDoTaskModel.RewriteRule item) {
            return item.replacementRule;
        }

        @Override
        public void setValue(ToDoTaskModel.RewriteRule item, String value) {
            if (StringUtils.isEmpty(value)) {
                return;
            }

            item.replacementRule = value;
        }

    };

    private final ColumnInfo[] COLUMNS = {
            ACTIVE_COLUMN,
            PATTERN_COLUMN,
            REPLACEMENT_COLUMN,
            PATH_COLUMN_INFO
    };

    void initUI() {
        ArrayList<ToDoTaskModel.RewriteRule> items = new ArrayList<ToDoTaskModel.RewriteRule>() {
            {
                add(new ToDoTaskModel.RewriteRule(true, "1111111", "22222222", "aaaaaa"));
                add(new ToDoTaskModel.RewriteRule(true, "2222222", "22222222", "bbbbb"));
                add(new ToDoTaskModel.RewriteRule(true, "3333333", "22222222", "ccccccc"));
            }
        };
        setUserData(items);
    }

    private void createUIComponents() {
//      create ui header
        setLayout(new BorderLayout());
        final JPanel conflictsPanel = new JPanel(new BorderLayout());
        final String title = String.format("请在下面设置ToDo优先级:");
        conflictsPanel.setBorder(IdeBorderFactory.createTitledBorder(title, false));

//      create TableEditor control
        TableModelEditor.DialogItemEditor<ToDoTaskModel.RewriteRule> itemEditor = new TableModelEditor.DialogItemEditor<ToDoTaskModel.RewriteRule>() {

            @NotNull
            @Override
            public Class<? extends ToDoTaskModel.RewriteRule> getItemClass() {
                return ToDoTaskModel.RewriteRule.class;
            }

            @Override
            public ToDoTaskModel.RewriteRule clone(@NotNull ToDoTaskModel.RewriteRule rewriteRule, boolean forInPlaceEditing) {
                return rewriteRule.copy();
            }

            @Override
            public void edit(@NotNull ToDoTaskModel.RewriteRule rewriteRule, @NotNull Function<ToDoTaskModel.RewriteRule, ToDoTaskModel.RewriteRule> mutator, boolean isAdd) {
                ToDoTaskModel.RewriteRule copy = rewriteRule.copy();
                mutator.fun(rewriteRule).updateWith(copy);
            }

            @Override
            public void applyEdited(@NotNull ToDoTaskModel.RewriteRule oldItem, @NotNull ToDoTaskModel.RewriteRule newItem) {
                oldItem.updateWith(newItem);
            }

            @Override
            public boolean isEditable(@NotNull ToDoTaskModel.RewriteRule item) {
                return false;
//                return true;
            }
        };

        editor = new TableModelEditor<>(COLUMNS, itemEditor, "No rules configured");
        rewriteRulesTable = editor.createComponent();
//        add(rewriteRulesTable, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
//        add(rewriteRulesTable, BorderLayout.CENTER);
//        conflictsPanel.add(rewriteRulesTable, BorderLayout.AFTER_LAST_LINE);
//        conflictsPanel.add(rewriteRulesTable, BorderLayout.AFTER_LINE_ENDS);
//        conflictsPanel.add(rewriteRulesTable, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        conflictsPanel.add(rewriteRulesTable, BorderLayout.PAGE_END);
        add(conflictsPanel, BorderLayout.CENTER);
    }
}
