package com.yaoxuanzhi;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.ui.StripeTable;
import com.intellij.ui.IdeBorderFactory;
import com.intellij.ui.JBColor;
import com.intellij.ui.components.JBScrollPane;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.ArrayList;

public class DemoSettingsEditor implements Configurable {
    private JTextField textField1;
    private JPanel myPanel;
    private DemmoSettingState settings;
    private TestService conf;
    @NotNull private final VimSettingsPanel myUIPanel = new VimSettingsPanel();
    private static final Logger LOG = Logger.getInstance(DemoSettingsEditor.class);

    public DemoSettingsEditor() {
        conf = TestService.getInstance();
        settings = DemmoSettingState.getInstance();
//      从持久化对象里面取数据来初始化控件
//        String str = settings.getPath();
        String str = conf.getName();
        LOG.debug("DemoSettingsEditor 构造函数 : " + str);
        textField1.setText(str);
    }

    public JComponent getRootComponent() {
        return myPanel;
    }

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "ToDoSettings";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
//        return myPanel;
        return myUIPanel;
    }

    @Override
    public boolean isModified() {
        return true;
    }

    @Override
    public void apply() throws ConfigurationException {
//      从控件上取文本回写到持久化对象上
        String str = textField1.getText();
        LOG.debug("点击应用之后 : " + str);
//        settings.setPath(str);
        conf.setName(str);
    }

    //  手动创建界面
    private static final class VimSettingsPanel extends JPanel {
        public VimSettingsPanel() {
            setLayout(new BorderLayout());
            final JPanel conflictsPanel = new JPanel(new BorderLayout());
            final String title = String.format("Custom Icons Type for ToDoTask");
            conflictsPanel.setBorder(IdeBorderFactory.createTitledBorder(title, false));
            add(conflictsPanel, BorderLayout.CENTER);
        }
    }

}

