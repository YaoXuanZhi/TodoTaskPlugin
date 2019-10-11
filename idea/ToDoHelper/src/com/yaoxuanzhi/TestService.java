package com.yaoxuanzhi;

import com.intellij.openapi.components.ServiceManager;
import com.yaoxuanzhi.impl.TestServiceImpl;

public interface TestService {
    static TestService getInstance() {
        return ServiceManager.getService(TestServiceImpl.class);
    }

    void setName(String str);
    String getName();
    void print();
}
