package com.example.factorybean;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

@Component
public class CustomerFactoryBean implements FactoryBean<MyBean> {
    @Override
    public MyBean getObject() throws Exception {
        //当注入MyBean，会调用该方法
        MyBean myBean = new MyBean();
        myBean.setName("aaaa");
        return myBean;
    }

    @Override
    public Class<?> getObjectType() {
        return MyBean.class;
    }
}
