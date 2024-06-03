package com.example.springstudy.dynamicproperties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestBeanController {
    @Autowired
    private TestConfig.TestBean testBean;

    @RequestMapping("getTestBean")
    public TestConfig.TestBean getTestBean() {
        return testBean;
    }

    @RequestMapping("updateBean")
    public TestConfig.TestBean getTestBean(String name) {
        testBean.setName(name);
        return testBean;
    }
}
