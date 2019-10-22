// Copyright 2000-2019 JetBrains s.r.o. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.

package com.yaoxuanzhi.todo_ui;

import com.intellij.psi.search.TodoPattern;
import com.intellij.ui.JBColor;
import com.intellij.util.ui.UIUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.List;

final class TodoPatternTableCellRendererPlus extends DefaultTableCellRenderer {
  private final List<? extends TodoPattern> myPatterns;

  TodoPatternTableCellRendererPlus(List<? extends TodoPattern> patterns) {
    myPatterns = patterns;
  }

  @Override
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
    super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    TodoPattern pattern = myPatterns.get(row);
    if (isSelected) {
      setForeground(UIUtil.getTableSelectionForeground(true));
    }
    else {
      setForeground(pattern.getPattern() != null ? UIUtil.getTableForeground() : JBColor.RED);
    }
    return this;
  }
}