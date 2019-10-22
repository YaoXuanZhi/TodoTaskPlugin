package com.yaoxuanzhi.extend;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

public class RunLua extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent e) {
        Globals globals = JsePlatform.standardGlobals();
        LuaValue chunk = globals.load("print 'hello, world'");
        chunk.call();
    }
}
